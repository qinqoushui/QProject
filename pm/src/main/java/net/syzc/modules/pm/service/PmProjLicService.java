/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import net.syzc.modules.pm.entity.PmProjLic;
import net.syzc.modules.pm.dao.PmProjLicDao;
import net.syzc.modules.pm.entity.PmProjLicModule;
import net.syzc.modules.pm.dao.PmProjLicModuleDao;

/**
 * 项目授权信息Service
 * @author 张文相
 * @version 2021-04-23
 */
@Service
@Transactional(readOnly=true)
public class PmProjLicService extends CrudService<PmProjLicDao, PmProjLic> {
	
	@Autowired
	private PmProjLicModuleDao pmProjLicModuleDao;
	
	/**
	 * 获取单条数据
	 * @param pmProjLic
	 * @return
	 */
	@Override
	public PmProjLic get(PmProjLic pmProjLic) {
		PmProjLic entity = super.get(pmProjLic);
		if (entity != null){
			PmProjLicModule pmProjLicModule = new PmProjLicModule(entity);
			pmProjLicModule.setStatus(PmProjLicModule.STATUS_NORMAL);
			entity.setPmProjLicModuleList(pmProjLicModuleDao.findList(pmProjLicModule));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param pmProjLic 查询条件
	 * @param pmProjLic.page 分页对象
	 * @return
	 */
	@Override
	public Page<PmProjLic> findPage(PmProjLic pmProjLic) {
		return super.findPage(pmProjLic);
	}
	
	/**
	 * 查询子表分页数据
	 * @param pmProjLicModule
	 * @param pmProjLicModule.page 分页对象
	 * @return
	 */
	public Page<PmProjLicModule> findSubPage(PmProjLicModule pmProjLicModule) {
		Page<PmProjLicModule> page = pmProjLicModule.getPage();
		page.setList(pmProjLicModuleDao.findList(pmProjLicModule));
		return page;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param pmProjLic
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PmProjLic pmProjLic) {
		super.save(pmProjLic);
		// 保存 PmProjLic子表
		for (PmProjLicModule pmProjLicModule : pmProjLic.getPmProjLicModuleList()){
			if (!PmProjLicModule.STATUS_DELETE.equals(pmProjLicModule.getStatus())){
				pmProjLicModule.setLicId(pmProjLic);
				if (pmProjLicModule.getIsNewRecord()){
					pmProjLicModuleDao.insert(pmProjLicModule);
				}else{
					pmProjLicModuleDao.update(pmProjLicModule);
				}
			}else{
				pmProjLicModuleDao.delete(pmProjLicModule);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param pmProjLic
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PmProjLic pmProjLic) {
		super.updateStatus(pmProjLic);
	}
	
	/**
	 * 删除数据
	 * @param pmProjLic
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PmProjLic pmProjLic) {
		super.delete(pmProjLic);
		PmProjLicModule pmProjLicModule = new PmProjLicModule();
		pmProjLicModule.setLicId(pmProjLic);
		pmProjLicModuleDao.deleteByEntity(pmProjLicModule);
	}
	
}