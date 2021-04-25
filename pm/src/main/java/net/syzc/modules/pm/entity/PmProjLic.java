/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package net.syzc.modules.pm.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 项目授权信息Entity
 * @author 张文相
 * @version 2021-04-23
 */
@Table(name="pm_proj_lic", alias="a", label="项目授权信息信息", columns={
		@Column(name="id", attrName="id", label="编号", isPK=true),
		@Column(name="sn", attrName="sn", label="授权标识"),
		@Column(name="proj_id", attrName="projId", label="项目编号"),
		@Column(name="company_name", attrName="companyName", label="单位名称", queryType=QueryType.LIKE),
		@Column(name="company_shortname", attrName="companyShortname", label="company_shortname"),
		@Column(name="start_date", attrName="startDate", label="生效日期"),
		@Column(name="end_time", attrName="endTime", label="截止日期"),
		@Column(name="operator_count", attrName="operatorCount", label="操作员数量"),
		@Column(name="user_count", attrName="userCount", label="用户数量"),
		@Column(includeEntity=DataEntity.class),
	}, joinTable = {
		@JoinTable(type = JoinTable.Type.JOIN, entity = PmProjBase.class, alias = "p",
				on = "p.id = a.proj_id", attrName = "project",
				columns = {
						@Column(includeEntity = PmProjBase.class),
						@Column(name="id", attrName="id", label="编号", isPK=true),
						@Column(name="project_name", attrName="projectName", label="项目名称", queryType=QueryType.LIKE),
				}),
},  orderBy="a.update_date DESC"
)
public class PmProjLic extends DataEntity<PmProjLic> {
	
	private static final long serialVersionUID = 1L;
	private String sn;		// 授权标识
	private String projId;		// 项目编号
	private String companyName;		// 单位名称
	private String companyShortname;		// company_shortname
	private Date startDate;		// 生效日期
	private Date endTime;		// 截止日期
	private Long operatorCount;		// 操作员数量
	private Long userCount;		// 用户数量
	private List<PmProjLicModule> pmProjLicModuleList = ListUtils.newArrayList();		// 子表列表
	private PmProjBase project; //项目信息

	public PmProjLic() {
		this(null);
	}

	public PmProjLic(String id){
		super(id);
	}
	
	@Length(min=0, max=32, message="授权标识长度不能超过 32 个字符")
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	@NotBlank(message="项目编号不能为空")
	@Length(min=0, max=20, message="项目编号长度不能超过 20 个字符")
	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}
	
	@NotBlank(message="单位名称不能为空")
	@Length(min=0, max=300, message="单位名称长度不能超过 300 个字符")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=50, message="company_shortname长度不能超过 50 个字符")
	public String getCompanyShortname() {
		return companyShortname;
	}

	public void setCompanyShortname(String companyShortname) {
		this.companyShortname = companyShortname;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="生效日期不能为空")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="截止日期不能为空")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@NotNull(message="操作员数量不能为空")
	public Long getOperatorCount() {
		return operatorCount;
	}

	public void setOperatorCount(Long operatorCount) {
		this.operatorCount = operatorCount;
	}
	
	@NotNull(message="用户数量不能为空")
	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	
	public List<PmProjLicModule> getPmProjLicModuleList() {
		return pmProjLicModuleList;
	}

	public void setPmProjLicModuleList(List<PmProjLicModule> pmProjLicModuleList) {
		this.pmProjLicModuleList = pmProjLicModuleList;
	}

	public PmProjBase getProject() {
		return project;
	}

	public void setProject(PmProjBase project) {
		this.project = project;
	}
}