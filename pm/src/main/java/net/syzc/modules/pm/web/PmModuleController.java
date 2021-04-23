/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.syzc.modules.pm.entity.PmProjBase;
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
import net.syzc.modules.pm.entity.PmModule;
import net.syzc.modules.pm.service.PmModuleService;

import java.util.List;

/**
 * 功能模块Controller
 * @author 张文相
 * @version 2021-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/pm/pmModule")
public class PmModuleController extends BaseController {

	@Autowired
	private PmModuleService pmModuleService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public PmModule get(String id, boolean isNewRecord) {
		return pmModuleService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pm:pmModule:view")
	@RequestMapping(value = {"list", ""})
	public String list(PmModule pmModule, Model model) {
		model.addAttribute("pmModule", pmModule);
		PmModule m=new PmModule();
		//m.setParentModule();
		List<PmModule> list = pmModuleService.findList(m);
		model.addAttribute("parentList",  list);
		return "modules/pm/pmModuleList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pm:pmModule:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<PmModule> listData(PmModule pmModule, HttpServletRequest request, HttpServletResponse response) {
		pmModule.setPage(new Page<>(request, response));
		Page<PmModule> page = pmModuleService.findPage(pmModule);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pm:pmModule:view")
	@RequestMapping(value = "form")
	public String form(PmModule pmModule, Model model) {
		model.addAttribute("pmModule", pmModule);
		PmModule m=new PmModule();
		//m.setParentModule();
		model.addAttribute("parentList", pmModuleService.findList(m));
		return "modules/pm/pmModuleForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pm:pmModule:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PmModule pmModule) {
		pmModuleService.save(pmModule);
		return renderResult(Global.TRUE, text("保存功能模块成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pm:pmModule:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(PmModule pmModule) {
		pmModuleService.delete(pmModule);
		return renderResult(Global.TRUE, text("删除功能模块成功！"));
	}
	
}