/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.common.mybatis.mapper.provider.UpdateSqlProvider;
import net.syzc.modules.pm.entity.PmModule;
import net.syzc.modules.pm.mapper.provider.MyUpdateSqlProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * 功能模块DAO接口
 * @author 张文相
 * @version 2021-04-23
 */
@MyBatisDao
public interface PmModuleDao extends TreeDao<PmModule> {
    @UpdateProvider(
            type = MyUpdateSqlProvider.class,
            method = "deleteAll"
    )
    long deleteAll(PmModule t );
}