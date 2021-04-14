/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.db;

import com.jeesite.modules.sys.entity.Menu;
import com.jeesite.modules.sys.entity.Module;
import com.jeesite.modules.sys.service.MenuService;
import com.jeesite.modules.sys.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.jeesite.common.tests.BaseInitDataTests;
import com.jeesite.modules.gen.utils.GenUtils;

import java.util.List;

/**
 * 初始化核心表数据
 *
 * @author ThinkGem
 * @version 2020-5-26
 */
@Component
@ConditionalOnProperty(name = "pm.initdata", havingValue = "true", matchIfMissing = false)
public class InitPMData extends BaseInitDataTests {

    @Override
    public boolean initData() throws Exception {
        // 如果表已存在，则无需初始化
        if (!GenUtils.isTableExists("pm_proj_base")) {
            runCreateScript("pm.sql");
        }
        runCreateScript("pm_initdata.sql");
        return true;
    }

    @Autowired
    private MenuService menuService;
    @Autowired
    private ModuleService moduleService;


    void initMenu(){
        Module pm = new Module();
        pm.setModuleCode("pm");
        pm = moduleService.get(pm);
        Menu menu = new Menu();
        menu.setModuleCodes(pm.getModuleCode());
        List<Menu> list = menuService.findList(menu);
        Menu pmMenu = new Menu();
        pmMenu.setMenuIcon("fa fa-institution");
        pmMenu.setMenuNameOrig("项目管理");
        pmMenu.setModuleCodes(pm.getModuleCode());
        pmMenu.setMenuType("1");
        pmMenu.setIsNewRecord(true);

        Menu pmBase = new Menu();
        pmBase.setMenuHref("/pm/pmProjBase/list");
        pmBase.setMenuIcon("fa fa-book");
        pmBase.setMenuNameOrig("项目信息");
        pmBase.setMenuTitle("项目信息");
        pmBase.setModuleCodes(pm.getModuleCode());
        pmBase.setMenuType("1");
    }
}
