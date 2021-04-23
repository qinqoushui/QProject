/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import net.syzc.modules.pm.entity.PmModule;
import net.syzc.modules.pm.dao.PmModuleDao;

/**
 * 功能模块Service
 * @author 张文相
 * @version 2021-04-23
 */
@Service
@Transactional(readOnly=true)
public class PmModuleService extends CrudService<PmModuleDao, PmModule> {
	
	/**
	 * 获取单条数据
	 * @param pmModule
	 * @return
	 */
	@Override
	public PmModule get(PmModule pmModule) {
		return super.get(pmModule);
	}
	
	/**
	 * 查询分页数据
	 * @param pmModule 查询条件
	 * @param pmModule.page 分页对象
	 * @return
	 */
	@Override
	public Page<PmModule> findPage(PmModule pmModule) {
		return super.findPage(pmModule);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param pmModule
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PmModule pmModule) {
		super.save(pmModule);
	}
	
	/**
	 * 更新状态
	 * @param pmModule
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PmModule pmModule) {
		super.updateStatus(pmModule);
	}
	
	/**
	 * 删除数据
	 * @param pmModule
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PmModule pmModule) {
		super.delete(pmModule);
	}
	
}