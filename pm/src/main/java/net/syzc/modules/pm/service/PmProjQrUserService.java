/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import net.syzc.modules.pm.entity.PmProjQrUser;
import net.syzc.modules.pm.dao.PmProjQrUserDao;

/**
 * 项目二维码用户码参数Service
 * @author 张文相
 * @version 2021-04-19
 */
@Service
@Transactional(readOnly=true)
public class PmProjQrUserService extends CrudService<PmProjQrUserDao, PmProjQrUser> {
	
	/**
	 * 获取单条数据
	 * @param pmProjQrUser
	 * @return
	 */
	@Override
	public PmProjQrUser get(PmProjQrUser pmProjQrUser) {
		return super.get(pmProjQrUser);
	}
	
	/**
	 * 查询分页数据
	 * @param pmProjQrUser 查询条件
	 * @param pmProjQrUser.page 分页对象
	 * @return
	 */
	@Override
	public Page<PmProjQrUser> findPage(PmProjQrUser pmProjQrUser) {
		return super.findPage(pmProjQrUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param pmProjQrUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PmProjQrUser pmProjQrUser) {
		super.save(pmProjQrUser);
	}
	
	/**
	 * 更新状态
	 * @param pmProjQrUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PmProjQrUser pmProjQrUser) {
		super.updateStatus(pmProjQrUser);
	}
	
	/**
	 * 删除数据
	 * @param pmProjQrUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PmProjQrUser pmProjQrUser) {
		super.delete(pmProjQrUser);
	}
	
}