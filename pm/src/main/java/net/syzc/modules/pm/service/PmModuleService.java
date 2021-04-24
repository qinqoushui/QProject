/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jeesite.common.callback.MethodCallback;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.mybatis.mapper.provider.UpdateSqlProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.beetl.ext.fn.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.service.TreeService;
import net.syzc.modules.pm.entity.PmModule;
import net.syzc.modules.pm.dao.PmModuleDao;

/**
 * 功能模块Service
 *
 * @author 张文相
 * @version 2021-04-23
 */
@Service
@Transactional(readOnly = true)
public class PmModuleService extends TreeService<PmModuleDao, PmModule> {


    @Autowired
    private PmModuleDao pmModuleDao;

    /**
     * 获取单条数据
     *
     * @param pmModule
     * @return
     */
    @Override
    public PmModule get(PmModule pmModule) {
        return super.get(pmModule);
    }

    /**
     * 查询列表数据
     *
     * @param pmModule
     * @return
     */
    @Override
    public List<PmModule> findList(PmModule pmModule) {
        return super.findList(pmModule);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param pmModule
     */
    @Override
    @Transactional(readOnly = false)
    public void save(PmModule pmModule) {
        super.save(pmModule);
    }

    /**
     * 更新状态
     *
     * @param pmModule
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(PmModule pmModule) {
        super.updateStatus(pmModule);
    }

    /**
     * 删除数据
     *
     * @param pmModule
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(PmModule pmModule) {
        super.delete(pmModule);
    }

    @Transactional(readOnly = false)
    public String parseJson(String json) {
        //删除所有的数据
        Long deleteCount = pmModuleDao.deleteAll(new PmModule());
        logger.info("delete count {}", deleteCount);
        List<PmModule> pmList = ListUtils.newArrayList();
        JSONObject jsonObject = JSON.parseObject(json);
        //获取内部的items
        JSONArray list = jsonObject.getJSONArray("Modules");
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            JSONObject moduleJson = (JSONObject) iterator.next();
            //获取Module各项属性
            PmModule module = new PmModule();
            module.setModuleName(moduleJson.getString("ModelName"));
            module.setModuleTag(moduleJson.getString("ModelTag"));
            module.setIsNewRecord(true);
            pmList.add(module);
            save(module);//还未参透如何补全各种业务数据，先调用标准方法
            logger.info("save " + module.getModuleName());
            //处理subModules
            JSONArray subList = moduleJson.getJSONArray("SubModels");
            subList.stream().iterator().forEachRemaining(sub -> {
                JSONObject subModuleJson = (JSONObject) sub;
                PmModule subModule = new PmModule();
                subModule.setModuleName(subModuleJson.getString("SubModelName"));
                subModule.setModuleTag(subModuleJson.getString("SubModelTag"));
                subModule.setModuleUrl(subModuleJson.getString("SubModelUrl"));
                subModule.setParent(module);
                subModule.setIsNewRecord(true);
                pmList.add(subModule);
                save(subModule);
                logger.info("find " + subModule.getModuleName());
            });
        }
//            ListUtils.pageList(pmList, 100, new MethodCallback() {
//                @SuppressWarnings("unchecked")
//                public Object execute(Object... objs) {
//                    return pmModuleDao.insertBatch((List<PmModule>) objs[0]);
//                }
//            });
        return "parse " + list.size();
    }
}