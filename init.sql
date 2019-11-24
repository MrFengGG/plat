create database home_plat;
create table if not exists home_plat.sys_user(
    id int not null primary key auto_increment comment 'id',
    username varchar(50) not null unique comment '用户名',
    password varchar(256) not null comment '密码',
    real_name varchar(30) comment '名字',
    nick_name varchar(30) comment '昵称',
    mobile varchar(30) comment '手机号码',
    email varchar(50) comment '邮箱',
    head_image varchar(256) comment '头像',
    birthday DATE comment '生日',
    last_login_time DATETIME comment '上次登录时间',
    last_login_ip varchar(30) comment '上次登录ip',
    expire_start_time DATETIME comment '开始封禁时间',
    expire_end_time DATETIME comment '结束封禁时间',
    create_time DATETIME comment '账号创建时间',
    update_time DATETIME comment '账号更新时间',
    wx_open_id varchar(256) comment '微信openid',
    status tinyint comment '账号状态,0:不可用,1:可用'
);

create table if not exists home_plat.resource(
    id int not null primary key auto_increment comment 'id',
    code varchar(256) unique not null comment '资源代码',
    resource_name varchar (30) not null comment '资源名称',
    resource_desc varchar(256) comment '资源详细描述',
    url varchar(256) comment '资源链接',
    resource_group varchar(30) comment '资源所属项目组',
    parent_Code varchar(256) comment '父资源代码',
    create_time DATETIME comment '创建时间',
    update_time DATETIME comment '修改时间'
);

create table if not exists home_plat.role(
    id int not null primary key auto_increment comment 'id',
    code varchar(256) unique not null comment '角色代码',
    role_name varchar(30) not null comment '角色名称',
    role_desc varchar(256) comment '角色描述',
    create_time DATETIME comment '创建时间',
    update_time DATETIME comment '修改时间'
);

create table if not exists home_plat.menu(
	id int not null primary key auto_increment comment 'id',
	code varchar(256) unique not null comment '菜单代码',
	menu_type int not null comment '菜单类型,0:子菜单，1：父菜单',
	menu_group_code varchar(256) not null comment '项目组',
    icon varchar(256) comment '图标',
    menu_name varchar(30) not null comment '菜单名称',
    menu_desc varchar(256) comment '菜单描述',
    parent_code varchar(256) comment '父级菜单',
    create_time DATETIME comment '创建时间',
    update_time DATETIME comment '修改时间',
    menu_path varchar(256) comment'菜单路径',
    priority int comment '显示优先级'
);

create table if not exists home_plat.menu_group(
	id int not null primary key auto_increment comment 'id',
	group_name varchar(256) not null comment '菜单组名称',
	code varchar(30) not null comment '菜单组代码',
	group_desc varchar(256) comment '菜单组描述',
	index_path varchar(256) comment '首页路径',
	priority int comment '显示优先级'
);

create table if not exists home_plat.resource_role(
    id int not null primary key auto_increment comment 'id',
    role_code varchar(256) not null comment '角色代码',
    resource_code varchar(30) not null comment '资源代码',
    create_time DATETIME not null comment '创建时间'
);

create table if not exists home_plat.user_role(
    id int not null primary key auto_increment comment'id',
    role_code varchar(256) not null comment '角色代码',
    user_id int not null comment '用户id',
    create_time DATETIME not null comment '创建时间'
);

create table if not exists home_plat.menu_role(
	id int not null primary key auto_increment comment 'id',
    role_code varchar(256) not null comment '角色代码',
    menu_code varchar(256) not null comment '资源代码',
    create_time DATETIME not null comment '创建时间'
);


insert into home_plat.sys_user(username,password,nick_name) values ('admin','$2a$10$o0A/HBA7xcfMUSSEcYfJrOp4eUIlkNq/nNP.45X2shWdoqjufQQlW','管理员');

insert into home_plat.role(code,role_name,role_desc, create_time) values('S_ADMIN','超级管理员','拥有无上权力的皇帝', now());

insert into home_plat.user_role(role_code, user_id, create_time) values('S_ADMIN','1', now());

insert into home_plat.menu_group(group_name, code, index_path, group_desc, priority) values('管理平台','plat', '/plat/index', '管理平台', 1);

insert into home_plat.menu(code, menu_type, menu_group_code, icon, menu_name, menu_desc, create_time, priority)
values('menu', 1, 'plat', 'fa fa-cog', '菜单管理', '菜单管理父级菜单', now(), 2)

insert into home_plat.menu(code, menu_type, menu_group_code, icon, menu_name, menu_desc, parent_code, create_time, menu_path, priority)
values('menu_edit', 0, 'plat', 'fa fa-pencil-square-o','菜单编辑器','用于编辑新的菜单', 'menu', now(), '/menu/edit', 2)