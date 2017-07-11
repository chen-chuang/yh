/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017-7-10 14:09:07                           */
/*==============================================================*/


drop table if exists sys_ccount;

drop table if exists sys_configTable;

drop table if exists sys_enterpriseInfo;

drop table if exists sys_integrationCash;

drop table if exists sys_order;

drop table if exists sys_orderDetail;

drop table if exists sys_orderIntegration;

drop table if exists sys_product;

drop table if exists sys_productType;

drop table if exists sys_region;

drop table if exists sys_user;

drop table if exists sys_withdraw;

/*==============================================================*/
/* Table: sys_ccount                                            */
/*==============================================================*/
create table sys_ccount
(
   id                   int not null auto_increment,
   enterprise_id        bigint comment '区域经销商id',
   price                decimal(18,3) comment '总金额',
   primary key (id)
);

alter table sys_ccount comment '资金账户表';

/*==============================================================*/
/* Table: sys_configTable                                       */
/*==============================================================*/
create table sys_configTable
(
   id                   int not null auto_increment,
   config_key           varchar(100) comment '配置key',
   config_name          varchar(100) comment '配置名称',
   config_value         varchar(5000) comment '配置值',
   config_user_id       bigint comment '配置人id',
   config_user_name     varchar(50) comment '配置人名称',
   config_region_id     int comment '配置人区域id',
   config_regin_name    varchar(100) comment '配置人区域名',
   config_create_time   datetime comment '配置时间',
   primary key (id)
);

alter table sys_configTable comment '配置表';

/*==============================================================*/
/* Table: sys_enterpriseInfo                                    */
/*==============================================================*/
create table sys_enterpriseInfo
(
   enterprise_id        bigint not null,
   enterprise_name      varchar(100) comment '企业名称',
   enterprise_image_url varchar(1024) comment '企业图片',
   enterprise_address   varchar(200) comment '企业地址',
   enterprise_phone     varchar(20) comment '手机',
   enterprise_tel       varchar(20) comment '电话',
   enterprise_contact   varchar(50) comment '联系人',
   enterprise_introduction text comment '简介',
   enterprise_longitude varchar(50) comment '经度',
   enterprise_latitude  varchar(50) comment '纬度',
   enterprise_area_id   varchar(50) comment '行政区域',
   enterprise_type      int comment '类型（1：生产厂家，2：经销商）',
   primary key (enterprise_id)
);

alter table sys_enterpriseInfo comment '企业信息表';

/*==============================================================*/
/* Table: sys_integrationCash                                   */
/*==============================================================*/
create table sys_integrationCash
(
   id                   int not null auto_increment,
   apply_user_id        bigint comment '申请用户id',
   integration          bigint comment '兑现积分',
   withdrawalAmount     decimal(18,3) comment '提现金额',
   apply_time           datetime comment '提现申请时间',
   withdraw_status      int comment '提现状态',
   operate_time         datetime comment '操作时间（针对状态）',
   user_id              bigint comment '管理员id',
   primary key (id)
);

alter table sys_integrationCash comment '积分兑现表（销售人员）';

/*==============================================================*/
/* Table: sys_order                                             */
/*==============================================================*/
create table sys_order
(
   order_id             varchar(100) not null,
   user_id              bigint comment '销售员id',
   order_create_time    datetime comment '订单生成时间',
   user_integral_count  bigint comment '使用积分数',
   order_all_price      decimal(18,3) comment '订单总价格',
   order_type           int comment '订单状态(0待支付 1已支付 2代配送 3已完成)',
   down_id              int comment '下单地址ID',
   order_address        varchar(100) comment '下单地址',
   order_send_time      datetime comment '要求配送时间',
   order_detail_address varchar(200) comment '详细地址',
   receiver_phone       varchar(100) comment '买者电话',
   receiver_name        varchar(50) comment '买者姓名',
   mark                 text comment '备注',
   order_pay_type       int comment '订单支付方式（1：支付宝，2：微信）',
   primary key (order_id)
);

alter table sys_order comment '订单表';

/*==============================================================*/
/* Table: sys_orderDetail                                       */
/*==============================================================*/
create table sys_orderDetail
(
   order_id             varchar(100) not null,
   product_id           bigint not null,
   product_num          bigint,
   product_name         varchar(100),
   product_price        decimal(18,3),
   product_sum_price    decimal(18,3),
   enterprise_id        bigint,
   enterprise_name      varchar(100),
   product_picture_url  varchar(1024),
   primary key (order_id, product_id)
);

alter table sys_orderDetail comment '订单明细表';

/*==============================================================*/
/* Table: sys_orderIntegration                                  */
/*==============================================================*/
create table sys_orderIntegration
(
   id                   int not null auto_increment,
   user_id              bigint comment '用户id',
   order_id             varchar(100) not null comment '订单id',
   order_sum_price      decimal(18,3) comment '订单金额',
   integration          bigint comment '生成积分',
   price_integration_type int comment '价格积分类型（1：配送积分?，2：销售积分）',
   primary key (id)
);

alter table sys_orderIntegration comment '订单积分表（销售人员、配送员）';

/*==============================================================*/
/* Table: sys_product                                           */
/*==============================================================*/
create table sys_product
(
   product_id           bigint not null auto_increment comment '产品ID',
   product_name         varchar(100) comment '产品名称',
   product_picture_url  varchar(1024) comment '图片地址',
   product_detail       text comment '简介',
   product_type         int comment '产品类型：1.烟花 2.爆竹 3.套餐 4.小烟花',
   product_video_url    varchar(1024) comment '视频地址',
   product_num          bigint comment '库存',
   product_trade_price  decimal(18,3) comment '批发价',
   product_retail_price decimal(18,3) comment '零售价',
   enterprise_id        bigint comment '企业ID',
   is_hot               int comment '是否热销（1：热销，2：不热销）',
   enter_type           int comment '录入类型（1：管理员，2：区域代理）',
   primary key (product_id)
);

alter table sys_product comment '产品表';

/*==============================================================*/
/* Table: sys_productType                                       */
/*==============================================================*/
create table sys_productType
(
   id                   int not null auto_increment,
   type                 varchar(50),
   primary key (id)
);

alter table sys_productType comment '产品分类表';

/*==============================================================*/
/* Table: sys_region                                            */
/*==============================================================*/
create table sys_region
(
   id                   int not null,
   name                 varchar(100) comment '行政区划名称',
   pid                  int comment '父ID',
   primary key (id)
);

alter table sys_region comment '行政区划表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              bigint not null comment '用户id',
   username             varchar(50) not null comment '用户名',
   password             varchar(100) comment '密码',
   salt                 varchar(20) comment '盐',
   email                varchar(100) comment '邮箱',
   mobile               varchar(100) comment '手机号',
   expiry_date          datetime comment '过期日期',
   create_user_id       bigint comment '创建者ID',
   create_time          datetime comment '创建时间',
   area_id              varchar(50) comment '所属区域ID',
   user_area            varchar(100) comment '所属区域名',
   belong_to_agency     bigint comment '所属经销商ID',
   user_permission      int comment '用户权限（1：管理员，2：生成厂家，3：经销商，4：配送员，5：发货员，6：销售员）',
   user_integral        bigint comment '积分',
   primary key (user_id)
);

alter table sys_user comment '系统用户';

/*==============================================================*/
/* Table: sys_withdraw                                          */
/*==============================================================*/
create table sys_withdraw
(
   id                   int not null auto_increment,
   apply_user_id        bigint comment '申请用户id',
   withdrawalAmount     decimal(18,3) comment '提现金额',
   apply_time           datetime comment '提现申请时间',
   withdraw_status      int comment '提现状态',
   operate_time         datetime comment '操作时间（针对状态）',
   user_id              bigint comment '管理员id',
   primary key (id)
);

alter table sys_withdraw comment '提现记录表（区域经销商）';

