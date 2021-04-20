/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.lang.StringUtils;
import net.syzc.modules.pm.entity.PmProjBase;
import net.syzc.modules.pm.service.PmProjBaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import net.syzc.modules.pm.entity.PmProjQrUser;
import net.syzc.modules.pm.service.PmProjQrUserService;

import java.util.Locale;
import java.util.UUID;

/**
 * 项目二维码用户码参数Controller
 *
 * @author 张文相
 * @version 2021-04-19
 */
@Controller
@RequestMapping(value = "${adminPath}/pm/pmProjQrUser")
public class PmProjQrUserController extends BaseController {

    @Autowired
    private PmProjQrUserService pmProjQrUserService;

    @Autowired
    private PmProjBaseService pmProjBaseService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public PmProjQrUser get(String id, boolean isNewRecord) {
        //新记录或密码修改后动态更新API
        return
                pmProjQrUserService.get(id, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("pm:pmProjQrUser:view")
    @RequestMapping(value = {"list", ""})
    public String list(PmProjQrUser pmProjQrUser, Model model) {
        model.addAttribute("pmProjQrUser", pmProjQrUser);
        model.addAttribute("projectList", pmProjBaseService.findList(new PmProjBase()));

        return "modules/pm/pmProjQrUserList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("pm:pmProjQrUser:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<PmProjQrUser> listData(PmProjQrUser pmProjQrUser, HttpServletRequest request, HttpServletResponse response) {
        pmProjQrUser.setPage(new Page<>(request, response));
        Page<PmProjQrUser> page = pmProjQrUserService.findPage(pmProjQrUser);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("pm:pmProjQrUser:view")
    @RequestMapping(value = "form")
    public String form(PmProjQrUser pmProjQrUser, Model model) {
        //新记录，
//        if (pmProjQrUser != null && StringUtils.isBlank(pmProjQrUser.getProjId())) {
//            pmProjQrUser.setApiKey(createAPIKey());
//        }
        model.addAttribute("pmProjQrUser", pmProjQrUser);
        model.addAttribute("projectList", pmProjBaseService.findList(new PmProjBase()));
        return "modules/pm/pmProjQrUserForm";
    }

    String createAPIKey() {
        //APIKey 用于第三方使用，使其快速定位一个二维码参数
        return  UUID.randomUUID().toString().replaceAll("-","").substring(16 ).toUpperCase(Locale.ROOT);
    }

    /**
     * 保存数据
     */
    @RequiresPermissions("pm:pmProjQrUser:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated PmProjQrUser pmProjQrUser) {
        //每次保存都重新生成一次APIKey,用于对应密码
        pmProjQrUser.setApiKey(createAPIKey());
        pmProjQrUser.setId("");
        pmProjQrUser.setIsNewRecord(true);
        pmProjQrUserService.save(pmProjQrUser);
        return renderResult(Global.TRUE, text("保存项目二维码用户码参数成功！"));
    }

    /**
     * 停用数据
     */
    @RequiresPermissions("pm:pmProjQrUser:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(PmProjQrUser pmProjQrUser) {
        pmProjQrUser.setStatus(PmProjQrUser.STATUS_DISABLE);
        pmProjQrUserService.updateStatus(pmProjQrUser);
        return renderResult(Global.TRUE, text("停用项目二维码用户码参数成功"));
    }

    /**
     * 启用数据
     */
    @RequiresPermissions("pm:pmProjQrUser:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(PmProjQrUser pmProjQrUser) {
        pmProjQrUser.setStatus(PmProjQrUser.STATUS_NORMAL);
        pmProjQrUserService.updateStatus(pmProjQrUser);
        return renderResult(Global.TRUE, text("启用项目二维码用户码参数成功"));
    }

    /**
     * 删除数据
     */
    @RequiresPermissions("pm:pmProjQrUser:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(PmProjQrUser pmProjQrUser) {
        pmProjQrUserService.delete(pmProjQrUser);
        return renderResult(Global.TRUE, text("删除项目二维码用户码参数成功！"));
    }

}