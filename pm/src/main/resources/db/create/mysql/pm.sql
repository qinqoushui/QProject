SET SESSION FOREIGN_KEY_CHECKS=0;


/* Create Tables */

-- 功能模块
CREATE TABLE pm_module
(
    id varchar(20) NOT NULL COMMENT '编号',
    parent_id varchar(20) COMMENT '父级编号',
    module_name varchar(200) COMMENT '模块名称',
    module_tag varchar(50) COMMENT '模块标签',
    module_url varchar(500) COMMENT '模块地址',
    PRIMARY KEY (id),
    UNIQUE (module_tag)
) COMMENT = '功能模块';


-- 项目项目信息
CREATE TABLE pm_proj_base
(
    id varchar(20) NOT NULL COMMENT '编号',
    project_name varchar(200) NOT NULL COMMENT '项目名称',
    status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
    create_by varchar(64) NOT NULL COMMENT '创建者',
    create_date datetime NOT NULL COMMENT '创建时间',
    update_by varchar(64) NOT NULL COMMENT '更新者',
    update_date datetime NOT NULL COMMENT '更新时间',
    remarks varchar(500) COMMENT '备注信息',
    PRIMARY KEY (id),
    UNIQUE (project_name)
) COMMENT = '项目项目信息';


-- 项目授权信息
CREATE TABLE pm_proj_lic
(
    id varchar(20) NOT NULL COMMENT '编号',
    sn varchar(32) COMMENT '授权标识',
    proj_id varchar(20) NOT NULL COMMENT '项目编号',
    company_name varchar(300) NOT NULL COMMENT '单位名称',
    company_shortname varchar(50) COMMENT 'company_shortname',
    start_date date NOT NULL COMMENT '生效日期',
    end_time date NOT NULL COMMENT '截止日期',
    operator_count int NOT NULL COMMENT '操作员数量',
    user_count int NOT NULL COMMENT '用户数量',
    status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
    create_by varchar(64) NOT NULL COMMENT '创建者',
    create_date datetime NOT NULL COMMENT '创建时间',
    update_by varchar(64) NOT NULL COMMENT '更新者',
    update_date datetime NOT NULL COMMENT '更新时间',
    remarks varchar(500) COMMENT '备注信息',
    PRIMARY KEY (id)
) COMMENT = '项目授权信息';


-- 项目授权模块信息
CREATE TABLE pm_proj_lic_module
(
    id varchar(20) NOT NULL COMMENT '编号',
    lic_id varchar(20) NOT NULL COMMENT '项目授权编号',
    module_name varchar(200) NOT NULL COMMENT '模块名称',
    module_tag varchar(50) NOT NULL COMMENT '模块标签',
    module_limit int COMMENT '模块规模限制 ',
    module_url varchar(500) COMMENT '模块地址',
    PRIMARY KEY (id)
) COMMENT = '项目授权模块信息';


-- 项目二维码用户码参数
CREATE TABLE pm_proj_qr_user
(
    id varchar(20) NOT NULL COMMENT '编号',
    proj_id varchar(20) NOT NULL COMMENT '项目编号',
    qr_type varchar(20) COMMENT '二维码厂商',
    password varchar(20) NOT NULL COMMENT '密码',
    api_key varchar(32) COMMENT 'API密钥',
    is_card_hex tinyint COMMENT '十六进制卡号',
    is_cardno_big tinyint COMMENT '卡号大端序',
    is_qr_big tinyint COMMENT '二维码卡号大端序',
    status char(1) DEFAULT '0' NOT NULL COMMENT '状态（0正常 1删除 2停用）',
    create_by varchar(64) NOT NULL COMMENT '创建者',
    create_date datetime NOT NULL COMMENT '创建时间',
    update_by varchar(64) NOT NULL COMMENT '更新者',
    update_date datetime NOT NULL COMMENT '更新时间',
    remarks varchar(500) COMMENT '备注信息',
    PRIMARY KEY (id),
    UNIQUE (id)
) COMMENT = '项目二维码用户码参数';



/* Create Indexes */

CREATE INDEX pm_proj_qr_user_idx_api ON pm_proj_qr_user (api_key ASC);



