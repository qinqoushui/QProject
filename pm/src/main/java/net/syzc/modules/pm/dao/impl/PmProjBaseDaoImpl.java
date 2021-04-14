package net.syzc.modules.pm.dao.impl;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import net.syzc.modules.pm.dao.PmProjBaseDao;
import net.syzc.modules.pm.entity.PmProjBase;

import java.util.List;

@MyBatisDao
public class PmProjBaseDaoImpl   implements PmProjBaseDao {
    @Override
    public long updateStatus(PmProjBase pmProjBase) {
        return 0;
    }

    @Override
    public List<PmProjBase> findList(PmProjBase pmProjBase) {
        return null;
    }

    @Override
    public long insert(PmProjBase pmProjBase) {
        return 0;
    }

    @Override
    public int phyDelete(PmProjBase pmProjBase) {
        return 0;
    }

    @Override
    public PmProjBase getByEntity(PmProjBase pmProjBase) {
        return null;
    }

    @Override
    public long deleteByEntity(PmProjBase pmProjBase) {
        return 0;
    }

    @Override
    public int phyDeleteByEntity(PmProjBase pmProjBase) {
        return 0;
    }

    @Override
    public long updateStatusByEntity(PmProjBase pmProjBase, PmProjBase t1) {
        return 0;
    }

    @Override
    public PmProjBase get(PmProjBase pmProjBase) {
        return null;
    }

    @Override
    public long findCount(PmProjBase pmProjBase) {
        return 0;
    }

    @Override
    public long updateByEntity(PmProjBase pmProjBase, PmProjBase t1) {
        return 0;
    }

    @Override
    public long insertBatch(List<PmProjBase> list) {
        return 0;
    }

    @Override
    public long delete(PmProjBase pmProjBase) {
        return 0;
    }

    @Override
    public long update(PmProjBase pmProjBase) {
        return 0;
    }
}
