

/* Create Tables */

-- 功能模块
CREATE TABLE [pm_module]
(
	[id] varchar(20) NOT NULL,
	[parent_id] varchar(20),
	[module_name] nvarchar(200),
	[module_tag] varchar(50) UNIQUE,
	[module_url] nvarchar(500),
	PRIMARY KEY ([id])
);


-- 项目项目信息
CREATE TABLE [pm_proj_base]
(
	[id] varchar(20) NOT NULL,
	[project_name] varchar(200) NOT NULL UNIQUE,
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	PRIMARY KEY ([id])
);


-- 项目授权信息
CREATE TABLE [pm_proj_lic]
(
	[id] varchar(20) NOT NULL,
	[sn] varchar(32),
	[proj_id] varchar(20) NOT NULL,
	[company_name] nvarchar(300) NOT NULL,
	[company_shortname] nvarchar(50),
	[start_date] date NOT NULL,
	[end_time] date NOT NULL,
	[operator_count] int NOT NULL,
	[user_count] int NOT NULL,
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	PRIMARY KEY ([id])
);


-- 项目授权模块信息
CREATE TABLE [pm_proj_lic_module]
(
	[id] varchar(20) NOT NULL,
	[lic_id] varchar(20) NOT NULL,
	[module_name] nvarchar(200) NOT NULL,
	[module_tag] varchar(50) NOT NULL,
	[module_limit] int,
	[module_url] nvarchar(500),
	PRIMARY KEY ([id])
);


-- 项目二维码用户码参数
CREATE TABLE [pm_proj_qr_user]
(
	[id] varchar(20) NOT NULL UNIQUE,
	[proj_id] varchar(20) NOT NULL,
	[qr_type] nvarchar(20),
	[password] varchar(20) NOT NULL,
	[api_key] varchar(32),
	[is_card_hex] tinyint,
	[is_cardno_big] tinyint,
	[is_qr_big] tinyint,
	[status] char(1) DEFAULT '0' NOT NULL,
	[create_by] varchar(64) NOT NULL,
	[create_date] datetime NOT NULL,
	[update_by] varchar(64) NOT NULL,
	[update_date] datetime NOT NULL,
	[remarks] nvarchar(500),
	PRIMARY KEY ([id])
);



/* Create Indexes */

CREATE INDEX [pm_proj_qr_user_idx_api] ON [pm_proj_qr_user] ([api_key]);



