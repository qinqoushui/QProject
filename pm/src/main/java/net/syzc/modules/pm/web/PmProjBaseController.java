/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import net.syzc.modules.pm.entity.PmProjBase;
import net.syzc.modules.pm.service.PmProjBaseService;

/**
 * 项目项目信息Controller
 * @author 张文相
 * @version 2021-04-13
 */
@Controller
@RequestMapping(value = "${adminPath}/pm/pmProjBase")
public class PmProjBaseController extends BaseController {

	@Autowired
	private PmProjBaseService pmProjBaseService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PmProjBase get(String id, boolean isNewRecord) {
		return pmProjBaseService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pm:pmProjBase:view")
	@RequestMapping(value = {"list", ""})
	public String list(PmProjBase pmProjBase, Model model) {
		model.addAttribute("pmProjBase", pmProjBase);
		return "modules/pm/pmProjBaseList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pm:pmProjBase:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PmProjBase> listData(PmProjBase pmProjBase, HttpServletRequest request, HttpServletResponse response) {
		pmProjBase.setPage(new Page<>(request, response));
		Page<PmProjBase> page = pmProjBaseService.findPage(pmProjBase);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pm:pmProjBase:view")
	@RequestMapping(value = "form")
	public String form(PmProjBase pmProjBase, Model model) {
		model.addAttribute("pmProjBase", pmProjBase);
		return "modules/pm/pmProjBaseForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pm:pmProjBase:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PmProjBase pmProjBase) {
		pmProjBaseService.save(pmProjBase);
		return renderResult(Global.TRUE, text("保存项目项目信息成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("pm:pmProjBase:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(PmProjBase pmProjBase) {
		pmProjBase.setStatus(PmProjBase.STATUS_DISABLE);
		pmProjBaseService.updateStatus(pmProjBase);
		return renderResult(Global.TRUE, text("停用项目项目信息成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("pm:pmProjBase:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(PmProjBase pmProjBase) {
		pmProjBase.setStatus(PmProjBase.STATUS_NORMAL);
		pmProjBaseService.updateStatus(pmProjBase);
		return renderResult(Global.TRUE, text("启用项目项目信息成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pm:pmProjBase:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(PmProjBase pmProjBase) {
		pmProjBaseService.delete(pmProjBase);
		return renderResult(Global.TRUE, text("删除项目项目信息成功！"));
	}
	
}