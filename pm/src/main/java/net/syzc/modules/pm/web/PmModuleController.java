/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.web;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import net.syzc.modules.pm.entity.PmModule;
import net.syzc.modules.pm.service.PmModuleService;

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
	public PmModule get(String moduleCode, boolean isNewRecord) {
		return pmModuleService.get(moduleCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pm:pmModule:view")
	@RequestMapping(value = {"list", ""})
	public String list(PmModule pmModule, Model model) {
		model.addAttribute("pmModule", pmModule);
		return "modules/pm/pmModuleList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pm:pmModule:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<PmModule> listData(PmModule pmModule) {
		if (StringUtils.isBlank(pmModule.getParentCode())) {
			pmModule.setParentCode(PmModule.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(pmModule.getModuleName())){
			pmModule.setParentCode(null);
		}
		if (StringUtils.isNotBlank(pmModule.getModuleTag())){
			pmModule.setParentCode(null);
		}
		if (StringUtils.isNotBlank(pmModule.getModuleUrl())){
			pmModule.setParentCode(null);
		}
		List<PmModule> list = pmModuleService.findList(pmModule);
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pm:pmModule:view")
	@RequestMapping(value = "form")
	public String form(PmModule pmModule, Model model) {
		// 创建并初始化下一个节点信息
		pmModule = createNextNode(pmModule);
		model.addAttribute("pmModule", pmModule);
		return "modules/pm/pmModuleForm";
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pm:pmModule:leadin")
	@RequestMapping(value = "leadin")
	public String leadin( ) {
		// 创建并初始化下一个节点信息

		return "modules/pm/pmModuleLeadIn";
	}

	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("pm:pmModule:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public PmModule createNextNode(PmModule pmModule) {
		if (StringUtils.isNotBlank(pmModule.getParentCode())){
			pmModule.setParent(pmModuleService.get(pmModule.getParentCode()));
		}
		if (pmModule.getIsNewRecord()) {
			PmModule where = new PmModule();
			where.setParentCode(pmModule.getParentCode());
			PmModule last = pmModuleService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				pmModule.setTreeSort(last.getTreeSort() + 30);
				pmModule.setModuleCode(IdGen.nextCode(last.getModuleCode()));
			}else if (pmModule.getParent() != null){
				pmModule.setModuleCode(pmModule.getParent().getModuleCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (pmModule.getTreeSort() == null){
			pmModule.setTreeSort(PmModule.DEFAULT_TREE_SORT);
		}
		return pmModule;
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

	@RequiresPermissions("pm:pmModule:leadin")
	@PostMapping(value = "save4LeadIn")
	@ResponseBody
	public String save4LeadIn(PmModule json) {
		pmModuleService.parseJson(json.getModuleUrl());
		return renderResult(Global.TRUE, text("导入功能模块成功！"));
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
	
	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("pm:pmModule:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<PmModule> list = pmModuleService.findList(new PmModule());
		for (int i=0; i<list.size(); i++){
			PmModule e = list.get(i);
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getModuleCode(), e.getModuleName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("pm:pmModule:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(PmModule pmModule){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		pmModuleService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
	
}