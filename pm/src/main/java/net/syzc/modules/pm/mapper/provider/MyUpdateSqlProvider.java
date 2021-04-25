package net.syzc.modules.pm.mapper.provider;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.mybatis.mapper.MapperHelper;
import com.jeesite.common.mybatis.mapper.provider.UpdateSqlProvider;

public class MyUpdateSqlProvider   extends UpdateSqlProvider {

    public String deleteAll(BaseEntity<?> entity) {

        return "delete from " + MapperHelper.getTableName(MapperHelper.getTable(entity), entity)  ;
    }

}
