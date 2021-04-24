/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 功能模块Entity
 * @author 张文相
 * @version 2021-04-23
 */
@Table(name="pm_module", alias="a", label="功能模块信息", columns={
		@Column(name="module_code", attrName="moduleCode", label="编号", isPK=true),
		@Column(name="module_name", attrName="moduleName", label="模块名称", queryType=QueryType.LIKE, isTreeName=true),
		@Column(name="module_tag", attrName="moduleTag", label="模块标签", queryType=QueryType.LIKE),
		@Column(name="module_url", attrName="moduleUrl", label="模块地址", queryType=QueryType.LIKE),
		@Column(includeEntity=TreeEntity.class),
	}, orderBy="a.tree_sorts, a.module_code"
)
public class PmModule extends TreeEntity<PmModule> {
	
	private static final long serialVersionUID = 1L;
	private String moduleCode;		// 编号
	private String moduleName;		// 模块名称
	private String moduleTag;		// 模块标签
	private String moduleUrl;		// 模块地址
	
	public PmModule() {
		this(null);
	}

	public PmModule(String id){
		super(id);
	}
	
	@Override
	public PmModule getParent() {
		return parent;
	}

	@Override
	public void setParent(PmModule parent) {
		this.parent = parent;
	}
	
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	@Length(min=0, max=200, message="模块名称长度不能超过 200 个字符")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@Length(min=0, max=50, message="模块标签长度不能超过 50 个字符")
	public String getModuleTag() {
		return moduleTag;
	}

	public void setModuleTag(String moduleTag) {
		this.moduleTag = moduleTag;
	}
	
	@Length(min=0, max=500, message="模块地址长度不能超过 500 个字符")
	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	
}