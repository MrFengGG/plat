### 表结构

* 用户表

  ~~~sql
  create table if not exists home_plat.sys_user(
      id int not null primary key auto_increment comment 'id',
      username varchar(50) not null unique comment '用户名',
      password varchar(256) not null comment '密码',
      real_name varchar(30) comment '名字',
      nick_name varchar(30) comment '昵称',
      mobile varchar(30) comment '手机号码',
      email varchar(50) comment '邮箱',
      head_image varchar(256) comment '头像',
      birthday timestamp comment '生日',
      last_login_time timestamp comment '上次登录时间',
      last_login_ip varchar(30) comment '上次登录ip',
      expire_start_time timestamp comment '开始封禁时间',
      expire_end_time timestamp comment '结束封禁时间',
      create_time timestamp comment '账号创建时间',
      update_time timestamp comment '账号更新时间',
      wx_open_id varchar(256) comment '微信openid',
      status tinyint comment '账号状态,0:不可用,1:可用'
  );
  ~~~

* 资源表

  ~~~sql
  create table if not exists home_plat.resource(
      id int not null primary key auto_increment comment 'id',
      code varchar(256) unique not null comment '资源代码',
      resource_name varchar (30) not null comment '资源名称',
      resource_desc varchar(256) comment '资源详细描述',
      url varchar(256) unique comment '资源链接',
      resource_group varchar(30) comment '资源所属项目组',
      parent_Code varchar(256) comment '父资源代码',
      create_time timestamp comment '创建时间',
      update_time timestamp comment '修改时间'
  );
  ~~~

* 角色表

  ~~~sql
  create table if not exists home_plat.role(
      id int not null primary key auto_increment comment 'id',
      code varchar(256) unique not null comment '角色代码',
      role_name varchar(30) not null comment '角色名称',
      role_desc varchar(256) comment '角色描述',
      create_time timestamp comment '创建时间',
      update_time timestamp comment '修改时间'
  );
  ~~~

* 菜单表

  ~~~sql
  create table if not exists home_plat.menu(
  	id int not null primary key auto_increment comment 'id',
      code varchar(256) unique not null comment '菜单代码',
      type int not null comment '菜单类型,0:子菜单，1：父菜单',
      icon varchar(256) comment '图标',	
      menu_name varchar(30) not null comment '菜单名称',
      menu_desc varchar(256) comment '菜单描述',
      parent_code varchar(256) comment '父级菜单',
      resource_code varchar(256) comment '资源code',
      create_time timestamp comment '创建时间',
      update_time timestamp comment '修改时间'
  );
  ~~~

* 资源-角色表

  ~~~sql
  create table if not exists home_plat.resource_role(
      id int not null primary key auto_increment comment 'id',
      role_code varchar(256) not null comment '角色代码',
      resource_code varchar(30) not null comment '资源代码',
      create_time timestamp not null comment '创建时间'
  );
  ~~~

* 用户-角色表

  ~~~sql
  create table if not exists home_plat.user_role(
      id int not null primary key auto_increment comment'id',
      role_code varchar(256) not null comment '角色代码',
      user_id int not null comment '用户id',
      create_time timestamp not null comment '创建时间'
  );
  ~~~

* 菜单-角色表

  ~~~sql
  create table if not exists home_plat.menu_role(
  	id int not null primary key auto_increment comment 'id',
      role_code varchar(256) not null comment '角色代码',
      menu_code varchar(256) not null comment '资源代码',
      create_time timestamp not null comment '创建时间'
  );
  ~~~

  