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
 * 项目项目信息Entity
 * @author 张文相
 * @version 2021-04-13
 */
@Table(name="pm_proj_base", alias="a", label="项目项目信息信息", columns={
		@Column(name="id", attrName="id", label="编号", isPK=true),
		@Column(name="project_name", attrName="projectName", label="项目名称", queryType=QueryType.LIKE),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class PmProjBase extends DataEntity<PmProjBase> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;		// 项目名称
	
	public PmProjBase() {
		this(null);
	}

	public PmProjBase(String id){
		super(id);
	}
	
	@NotBlank(message="项目名称不能为空")
	@Length(min=0, max=200, message="项目名称长度不能超过 200 个字符")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}