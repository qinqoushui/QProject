-- 自动生成
SET SESSION FOREIGN_KEY_CHECKS=0;


/* Create Tables */

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


-- 项目二维码用户码参数
CREATE TABLE pm_proj_qr_user
(
    id varchar(20) NOT NULL COMMENT '编号',
    proj_id varchar(20) NOT NULL COMMENT '项目编号',
    qr_type varchar(20) COMMENT '二维码厂商',
    password varchar(20) NOT NULL COMMENT '密码',
    api_key varchar(32) COMMENT 'API密钥',
    is_card_hex bit(1) COMMENT '十六进制卡号',
    is_cardno_big bit(1) COMMENT '卡号大端序',
    is_qr_big bit(1) COMMENT '二维码卡号大端序',
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

commit ;



