/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import net.syzc.modules.pm.entity.PmProjBase;
import net.syzc.modules.pm.dao.PmProjBaseDao;

/**
 * 项目项目信息Service
 * @author 张文相
 * @version 2021-04-19
 */
@Service
@Transactional(readOnly=true)
public class PmProjBaseService extends CrudService<PmProjBaseDao, PmProjBase> {
	
	/**
	 * 获取单条数据
	 * @param pmProjBase
	 * @return
	 */
	@Override
	public PmProjBase get(PmProjBase pmProjBase) {
		return super.get(pmProjBase);
	}
	
	/**
	 * 查询分页数据
	 * @param pmProjBase 查询条件
	 * @param pmProjBase.page 分页对象
	 * @return
	 */
	@Override
	public Page<PmProjBase> findPage(PmProjBase pmProjBase) {
		return super.findPage(pmProjBase);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param pmProjBase
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PmProjBase pmProjBase) {
		super.save(pmProjBase);
	}
	
	/**
	 * 更新状态
	 * @param pmProjBase
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PmProjBase pmProjBase) {
		super.updateStatus(pmProjBase);
	}
	
	/**
	 * 删除数据
	 * @param pmProjBase
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PmProjBase pmProjBase) {
		super.delete(pmProjBase);
	}
	
}