/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import net.syzc.modules.pm.entity.PmModule;
import net.syzc.modules.pm.entity.PmProjLic;
import net.syzc.modules.pm.entity.PmProjLicModule;
import net.syzc.modules.pm.mapper.provider.MyUpdateSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * 项目授权信息DAO接口
 * @author 张文相
 * @version 2021-04-23
 */
@MyBatisDao
public interface PmProjLicModuleDao extends CrudDao<PmProjLicModule> {
    @UpdateProvider(
            type = MyUpdateSqlProvider.class,
            method = "deleteByParent"
    )
    long deleteByParent(PmProjLicModule t  );

    List<PmProjLicModule> findListByLic( PmProjLicModule  t );
}