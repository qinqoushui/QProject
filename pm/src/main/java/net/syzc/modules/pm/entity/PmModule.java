/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.entity;

import com.jeesite.modules.sys.entity.User;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 功能模块Entity
 * @author 张文相
 * @version 2021-04-23
 */
@Table(name="pm_module", alias="a", label="功能模块信息", columns={
		@Column(name="id", attrName="id", label="编号", isPK=true),
		@Column(name="parent_id", attrName="parentId", label="父级编号"),
		@Column(name="module_name", attrName="moduleName", label="模块名称", queryType=QueryType.LIKE),
		@Column(name="module_tag", attrName="moduleTag", label="模块标签", queryType=QueryType.LIKE),
		@Column(name="module_url", attrName="moduleUrl", label="模块地址", queryType=QueryType.LIKE),
	}, joinTable={
		@JoinTable(type=Type.LEFT_JOIN, entity=PmModule.class, attrName="parentModule", alias="p",
			on="p.id = a.parent_id" ,columns = {
				@Column(includeEntity = PmModule.class),
		}),
}, orderBy="a.id DESC"
)
public class PmModule extends DataEntity<PmModule> {
	
	private static final long serialVersionUID = 1L;
	private String parentId;// 父级编号
	private String moduleName;		// 模块名称
	private String moduleTag;		// 模块标签
	private String moduleUrl;		// 模块地址
	private PmModule parentModule;
	public PmModule() {
		this(null);
	}

	public PmModule(String id){
		super(id);
	}
	
	public PmModule getParentModule() {
		return parentModule;
	}

	public void setParentModule(PmModule ParentModule) {
		this.parentModule = ParentModule;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}