/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.ObjectUtils;
import com.jeesite.common.lang.StringUtils;
import net.syzc.modules.pm.dao.PmProjLicModuleDao;
import net.syzc.modules.pm.entity.PmModule;
import net.syzc.modules.pm.entity.PmProjBase;
import net.syzc.modules.pm.service.PmModuleService;
import net.syzc.modules.pm.service.PmProjBaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.alibaba.fastjson.JSONValidator;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.web.BaseController;
import net.syzc.modules.pm.entity.PmProjLic;
import net.syzc.modules.pm.entity.PmProjLicModule;
import net.syzc.modules.pm.service.PmProjLicService;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * 项目授权信息Controller
 *
 * @author 张文相
 * @version 2021-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/pm/pmProjLic")
public class PmProjLicController extends BaseController {

    @Autowired
    private PmProjLicService pmProjLicService;

    @Autowired
    private PmModuleService pmModuleService;

    @Autowired
    private PmProjBaseService pmProjBaseService;


    /**
     * 获取数据
     */
    @ModelAttribute
    public PmProjLic get(String id, boolean isNewRecord) {
        return pmProjLicService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("pm:pmProjLic:view")
    @RequestMapping(value = {"list", ""})
    public String list(PmProjLic pmProjLic, Model model) {
        model.addAttribute("pmProjLic", pmProjLic);
        model.addAttribute("projectList", pmProjBaseService.findList(new PmProjBase()));
        return "modules/pm/pmProjLicList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("pm:pmProjLic:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<PmProjLic> listData(PmProjLic pmProjLic, HttpServletRequest request, HttpServletResponse response) {
        pmProjLic.setPage(new Page<>(request, response));
        Page<PmProjLic> page = pmProjLicService.findPage(pmProjLic);
        return page;
    }

    /**
     * 查询子表数据
     */
    @RequiresPermissions("pm:pmProjLic:view")
    @RequestMapping(value = "pmProjLicModuleListData")
    @ResponseBody
    public Page<PmModule> subListData(PmProjLicModule pmProjLicModule, HttpServletRequest request, HttpServletResponse response) {
        pmProjLicModule.setPage(new Page<>(request, response));
        //查找所有模块，按tag相同进行复合
        List<PmModule> modules = pmModuleService.findList(new PmModule());
        Page<PmModule> page = pmModuleService.findPage(new PmModule());
        return page;
    }

    @RequiresPermissions(value = {"pm:pmProjLic:view"})
    @RequestMapping(value = {"moduleTreeData"})
    @ResponseBody
    public Map<String, Object> moduleTreeData(@RequestBody PmProjLic lic, HttpServletRequest request) {
        HashMap a2 = MapUtils.newHashMap();
        List<PmModule> a3 = pmModuleService.findList(new PmModule());
        // a3.sort(Comparator.comparing(PmModule::getTreeNames));
        Iterator<PmModule> iterator2 = a3.iterator();
        //按顺序查找每轮节点
        List a9 = ListUtils.newArrayList();
        while (iterator2.hasNext()) {
            PmModule a8 = iterator2.next();
            HashMap hashMap = MapUtils.newHashMap();
            hashMap.put("id", a8.getModuleCode());
            hashMap.put("pId", a8.getParentCode());
            hashMap.put("name", new StringBuilder().insert(0, a8.getModuleName()).append("<font color=#888> &nbsp; &nbsp; ").append(StringUtils.abbr((String) new StringBuilder().append(" &nbsp; ").append(ObjectUtils.toString((Object) a8.getModuleUrl())).toString(), (int) 50)).append("</font>").toString());
            hashMap.put("title", new StringBuilder().insert(0, a8.getModuleName()).append("&nbsp;").append("\n").append(ObjectUtils.toString((Object) a8.getModuleUrl())).toString());
            a9.add(hashMap);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("licModules", a9);
        a2.put("menuMap", hashMap);
        PmProjLicModule a4 = new PmProjLicModule();
        //a4.setModuleLimit(100L);
        a4.setLicId(lic);

        //已授权的模块，按TAG覆盖ID
        List<PmProjLicModule> a10 = pmProjLicService.findSubModuleList(a4);
        a10.stream().iterator().forEachRemaining(licModule -> {
            String moduleTag = licModule.getModuleTag();
            Optional<PmModule> first = StreamSupport.stream(a3.spliterator(), false)
                    .map(it -> it).filter(it -> moduleTag.equals(it.getModuleTag()))
                    .findFirst();
            if (first.isPresent()) {
                licModule.setId(first.get().getId());
            }
        });
        a2.put("roleMenuList", a10);
        return a2;
    }


    /**
     * 查看编辑表单
     */
    @RequiresPermissions("pm:pmProjLic:view")
    @RequestMapping(value = "form")
    public String form(PmProjLic pmProjLic, Model model) {
        model.addAttribute("pmProjLic", pmProjLic);
        model.addAttribute("projectList", pmProjBaseService.findList(new PmProjBase()));
        if (StringUtils.isBlank(pmProjLic.getId())) {
            pmProjLic.setStartDate(DateUtils.getOfDayFirst(new Date()));
            pmProjLic.setEndTime(DateUtils.addMonths(DateUtils.getOfDayFirst(new Date()), 1));
            pmProjLic.setOperatorCount(100L);
            pmProjLic.setUserCount(1000L);
            pmProjLic.setSn(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        return "modules/pm/pmProjLicForm";
    }


    /**
     * 保存数据
     */
    @RequiresPermissions("pm:pmProjLic:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated PmProjLic pmProjLic) {
        //pmProjLicModuleIdList
        pmProjLicService.save(pmProjLic);
        //处理授权模块
        pmProjLicService.saveSubModuleList(pmProjLic);
        return renderResult(Global.TRUE, text("保存项目授权信息成功！"));
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("pm:pmProjLic:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(PmProjLic pmProjLic) {
        pmProjLicService.delete(pmProjLic);
        return renderResult(Global.TRUE, text("删除项目授权信息成功！"));
    }

    /**
     * 列表选择对话框
     */
    @RequiresPermissions("pm:pmProjLic:view")
    @RequestMapping(value = "pmProjLicSelect")
    public String pmProjLicSelect(PmProjLic pmProjLic, String selectData, Model model) {
        String selectDataJson = EncodeUtils.decodeUrl(selectData);
        if (selectDataJson != null && JSONValidator.from(selectDataJson).validate()) {
            model.addAttribute("selectData", selectDataJson);
        }
        model.addAttribute("pmProjLic", pmProjLic);
        return "modules/pm/pmProjLicSelect";
    }

}