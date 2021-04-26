/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.callback.MethodCallback;
import com.jeesite.common.collect.ListUtils;
import net.syzc.modules.pm.dao.PmModuleDao;
import net.syzc.modules.pm.entity.PmModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import net.syzc.modules.pm.entity.PmProjLic;
import net.syzc.modules.pm.dao.PmProjLicDao;
import net.syzc.modules.pm.entity.PmProjLicModule;
import net.syzc.modules.pm.dao.PmProjLicModuleDao;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 项目授权信息Service
 *
 * @author 张文相
 * @version 2021-04-23
 */
@Service
@Transactional(readOnly = true)
public class PmProjLicService extends CrudService<PmProjLicDao, PmProjLic> {

    @Autowired
    private PmProjLicModuleDao pmProjLicModuleDao;

    @Autowired
    private PmModuleDao pmModuleDao;

    /**
     * 获取单条数据
     *
     * @param pmProjLic
     * @return
     */
    @Override
    public PmProjLic get(PmProjLic pmProjLic) {
        PmProjLic entity = super.get(pmProjLic);
        if (entity != null) {
            PmProjLicModule pmProjLicModule = new PmProjLicModule(entity);
            pmProjLicModule.setStatus(PmProjLicModule.STATUS_NORMAL);
            entity.setPmProjLicModuleList(pmProjLicModuleDao.findList(pmProjLicModule));
        }
        return entity;
    }

    /**
     * 查询分页数据
     *
     * @param pmProjLic 查询条件
     * @return
     */
    @Override
    public Page<PmProjLic> findPage(PmProjLic pmProjLic) {
        return super.findPage(pmProjLic);
    }

    /**
     * 查询子表分页数据
     *
     * @param pmProjLicModule
     * @return
     */
    public Page<PmProjLicModule> findSubPage(PmProjLicModule pmProjLicModule) {
        Page<PmProjLicModule> page = pmProjLicModule.getPage();
        page.setList(pmProjLicModuleDao.findList(pmProjLicModule));
        return page;
    }

    public List<PmProjLicModule> findSubModuleList(PmProjLicModule  pmProjLicModule ) {
        return pmProjLicModuleDao.findListByLic(pmProjLicModule  );
    }


    /**
     * 保存数据（插入或更新）
     *
     * @param pmProjLic
     */
    @Override
    @Transactional(readOnly = false)
    public void save(PmProjLic pmProjLic) {
        super.save(pmProjLic);
        // 保存 PmProjLic子表
        for (PmProjLicModule pmProjLicModule : pmProjLic.getPmProjLicModuleList()) {
            if (!PmProjLicModule.STATUS_DELETE.equals(pmProjLicModule.getStatus())) {
                pmProjLicModule.setLicId(pmProjLic);
                if (pmProjLicModule.getIsNewRecord()) {
                    pmProjLicModuleDao.insert(pmProjLicModule);
                } else {
                    pmProjLicModuleDao.update(pmProjLicModule);
                }
            } else {
                pmProjLicModuleDao.delete(pmProjLicModule);
            }
        }
    }

    @Transactional(readOnly = false)
    public void saveSubModuleList(PmProjLic lic) {
        //删除所有的数据
        PmProjLicModule deleteWhere = new PmProjLicModule();
        deleteWhere.setLicId(lic);
        pmProjLicModuleDao.deleteByParent(deleteWhere);
        List<PmProjLicModule> needSave = ListUtils.newArrayList();
        List<PmProjLicModule> licModules = JSON.parseArray(lic.getPmProjLicModuleIdList(), PmProjLicModule.class);
        List<PmModule> pmModuleList = pmModuleDao.findList(new PmModule());
//在新产生的licModule中查找系统配置的项
        licModules.stream().iterator().forEachRemaining(module -> {
            String moduleId = module.getId();
            Optional<PmModule> first = StreamSupport.stream(pmModuleList.spliterator(), false)
                    .map(it -> it).filter(it -> moduleId.equals(it.getId()))
                    .findFirst();
            if (first.isPresent()) {
                PmProjLicModule subModule = new PmProjLicModule();
                subModule.setModuleName(first.get().getModuleName());
                subModule.setModuleTag(first.get().getModuleTag());
                subModule.setModuleUrl(first.get().getModuleUrl());
                subModule.setModuleLimit(module.getModuleLimit());
                subModule.setLicId(lic);
                subModule.setIsNewRecord(true);
                subModule.preInsert();
                needSave.add(subModule);
            }
        });
        //分批提交
        ListUtils.pageList(needSave, 100, new MethodCallback() {
            @SuppressWarnings("unchecked")
            public Object execute(Object... objs) {
                return pmProjLicModuleDao.insertBatch((List<PmProjLicModule>) objs[0]);
            }
        });
        logger.info("save licModules  " + needSave.size());
    }

    /**
     * 更新状态
     *
     * @param pmProjLic
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(PmProjLic pmProjLic) {
        super.updateStatus(pmProjLic);
    }

    /**
     * 删除数据
     *
     * @param pmProjLic
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(PmProjLic pmProjLic) {
        super.delete(pmProjLic);
        PmProjLicModule pmProjLicModule = new PmProjLicModule();
        pmProjLicModule.setLicId(pmProjLic);
        pmProjLicModuleDao.deleteByEntity(pmProjLicModule);
    }

}