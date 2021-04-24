

/* Create Tables */

-- 功能模块
CREATE TABLE [pm_module]
(
	[module_code] text NOT NULL,
	[module_name] text,
	[module_tag] text UNIQUE,
	[module_url] text,
	[parent_code] text NOT NULL,
	[parent_codes] text NOT NULL,
	[tree_sort] numeric NOT NULL,
	[tree_sorts] text NOT NULL,
	[tree_leaf] text NOT NULL,
	[tree_level] numeric NOT NULL,
	[tree_names] text NOT NULL,
	PRIMARY KEY ([module_code])
);


-- 项目项目信息
CREATE TABLE [pm_proj_base]
(
	[id] text NOT NULL,
	[project_name] text NOT NULL UNIQUE,
	[status] text DEFAULT '0' NOT NULL,
	[create_by] text NOT NULL,
	[create_date] none NOT NULL,
	[update_by] text NOT NULL,
	[update_date] none NOT NULL,
	[remarks] text,
	PRIMARY KEY ([id])
);


-- 项目授权信息
CREATE TABLE [pm_proj_lic]
(
	[id] text NOT NULL,
	[sn] text,
	[proj_id] text NOT NULL,
	[company_name] text NOT NULL,
	[company_shortname] text,
	[start_date] none NOT NULL,
	[end_time] none NOT NULL,
	[operator_count] integer NOT NULL,
	[user_count] integer NOT NULL,
	[status] text DEFAULT '0' NOT NULL,
	[create_by] text NOT NULL,
	[create_date] none NOT NULL,
	[update_by] text NOT NULL,
	[update_date] none NOT NULL,
	[remarks] text,
	PRIMARY KEY ([id]),
	FOREIGN KEY ([proj_id])
	REFERENCES [pm_proj_base] ([id])
);


-- 项目授权模块信息
CREATE TABLE [pm_proj_lic_module]
(
	[id] text NOT NULL,
	[lic_id] text NOT NULL,
	[module_name] text NOT NULL,
	[module_tag] text NOT NULL,
	[module_limit] integer,
	[module_url] text,
	PRIMARY KEY ([id]),
	FOREIGN KEY ([lic_id])
	REFERENCES [pm_proj_lic] ([id])
);


-- 项目二维码用户码参数
CREATE TABLE [pm_proj_qr_user]
(
	[id] text NOT NULL UNIQUE,
	[proj_id] text NOT NULL,
	[qr_type] text,
	[password] text NOT NULL,
	[api_key] text,
	[is_card_hex] integer,
	[is_cardno_big] integer,
	[is_qr_big] integer,
	[status] text DEFAULT '0' NOT NULL,
	[create_by] text NOT NULL,
	[create_date] none NOT NULL,
	[update_by] text NOT NULL,
	[update_date] none NOT NULL,
	[remarks] text,
	PRIMARY KEY ([id]),
	FOREIGN KEY ([proj_id])
	REFERENCES [pm_proj_base] ([id])
);



/* Create Indexes */

CREATE INDEX [pm_proj_qr_user_idx_api] ON [pm_proj_qr_user] ([api_key]);



