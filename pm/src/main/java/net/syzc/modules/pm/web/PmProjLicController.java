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
import com.alibaba.fastjson.JSONValidator;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.web.BaseController;
import net.syzc.modules.pm.entity.PmProjLic;
import net.syzc.modules.pm.entity.PmProjLicModule;
import net.syzc.modules.pm.service.PmProjLicService;

/**
 * 项目授权信息Controller
 * @author 张文相
 * @version 2021-04-23
 */
@Controller
@RequestMapping(value = "${adminPath}/pm/pmProjLic")
public class PmProjLicController extends BaseController {

	@Autowired
	private PmProjLicService pmProjLicService;
	
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
	public Page<PmProjLicModule> subListData(PmProjLicModule pmProjLicModule, HttpServletRequest request, HttpServletResponse response) {
		pmProjLicModule.setPage(new Page<>(request, response));
		Page<PmProjLicModule> page = pmProjLicService.findSubPage(pmProjLicModule);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pm:pmProjLic:view")
	@RequestMapping(value = "form")
	public String form(PmProjLic pmProjLic, Model model) {
		model.addAttribute("pmProjLic", pmProjLic);
		return "modules/pm/pmProjLicForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pm:pmProjLic:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated PmProjLic pmProjLic) {
		pmProjLicService.save(pmProjLic);
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
		if (selectDataJson != null && JSONValidator.from(selectDataJson).validate()){
			model.addAttribute("selectData", selectDataJson);
		}
		model.addAttribute("pmProjLic", pmProjLic);
		return "modules/pm/pmProjLicSelect";
	}
	
}