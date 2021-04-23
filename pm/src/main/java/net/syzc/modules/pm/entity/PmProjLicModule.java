/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 项目授权信息Entity
 * @author 张文相
 * @version 2021-04-23
 */
@Table(name="pm_proj_lic_module", alias="a", label="项目授权信息信息", columns={
		@Column(name="id", attrName="id", label="编号", isPK=true),
		@Column(name="lic_id", attrName="licId.id", label="项目授权编号"),
		@Column(name="module_name", attrName="moduleName", label="模块名称", queryType=QueryType.LIKE),
		@Column(name="module_tag", attrName="moduleTag", label="模块标签"),
		@Column(name="module_limit", attrName="moduleLimit", label="模块规模限制", isQuery=false),
		@Column(name="module_url", attrName="moduleUrl", label="模块地址", queryType=QueryType.LIKE),
	}, orderBy="a.id ASC"
)
public class PmProjLicModule extends DataEntity<PmProjLicModule> {
	
	private static final long serialVersionUID = 1L;
	private PmProjLic licId;		// 项目授权编号 父类
	private String moduleName;		// 模块名称
	private String moduleTag;		// 模块标签
	private Long moduleLimit;		// 模块规模限制
	private String moduleUrl;		// 模块地址
	
	public PmProjLicModule() {
		this(null);
	}


	public PmProjLicModule(PmProjLic licId){
		this.licId = licId;
	}
	
	@NotBlank(message="项目授权编号不能为空")
	@Length(min=0, max=20, message="项目授权编号长度不能超过 20 个字符")
	public PmProjLic getLicId() {
		return licId;
	}

	public void setLicId(PmProjLic licId) {
		this.licId = licId;
	}
	
	@NotBlank(message="模块名称不能为空")
	@Length(min=0, max=200, message="模块名称长度不能超过 200 个字符")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@NotBlank(message="模块标签不能为空")
	@Length(min=0, max=50, message="模块标签长度不能超过 50 个字符")
	public String getModuleTag() {
		return moduleTag;
	}

	public void setModuleTag(String moduleTag) {
		this.moduleTag = moduleTag;
	}
	
	public Long getModuleLimit() {
		return moduleLimit;
	}

	public void setModuleLimit(Long moduleLimit) {
		this.moduleLimit = moduleLimit;
	}
	
	@Length(min=0, max=500, message="模块地址长度不能超过 500 个字符")
	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	
}