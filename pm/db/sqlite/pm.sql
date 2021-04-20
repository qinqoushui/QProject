

/* Create Tables */

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



