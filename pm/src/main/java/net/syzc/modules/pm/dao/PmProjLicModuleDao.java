/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import net.syzc.modules.pm.entity.PmProjLicModule;

/**
 * 项目授权信息DAO接口
 * @author 张文相
 * @version 2021-04-23
 */
@MyBatisDao
public interface PmProjLicModuleDao extends CrudDao<PmProjLicModule> {
	
}