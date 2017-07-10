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
   enterprise_id        bigint comment '��������id',
   price                decimal(18,3) comment '�ܽ��',
   primary key (id)
);

alter table sys_ccount comment '�ʽ��˻���';

/*==============================================================*/
/* Table: sys_configTable                                       */
/*==============================================================*/
create table sys_configTable
(
   id                   int not null auto_increment,
   config_key           varchar(100) comment '����key',
   config_name          varchar(100) comment '��������',
   config_value         varchar(5000) comment '����ֵ',
   config_user_id       bigint comment '������id',
   config_user_name     varchar(50) comment '����������',
   config_region_id     int comment '����������id',
   config_regin_name    varchar(100) comment '������������',
   config_create_time   datetime comment '����ʱ��',
   primary key (id)
);

alter table sys_configTable comment '���ñ�';

/*==============================================================*/
/* Table: sys_enterpriseInfo                                    */
/*==============================================================*/
create table sys_enterpriseInfo
(
   enterprise_id        bigint not null,
   enterprise_name      varchar(100) comment '��ҵ����',
   enterprise_image_url varchar(1024) comment '��ҵͼƬ',
   enterprise_address   varchar(200) comment '��ҵ��ַ',
   enterprise_phone     varchar(20) comment '�ֻ�',
   enterprise_tel       varchar(20) comment '�绰',
   enterprise_contact   varchar(50) comment '��ϵ��',
   enterprise_introduction text comment '���',
   enterprise_longitude varchar(50) comment '����',
   enterprise_latitude  varchar(50) comment 'γ��',
   enterprise_area_id   varchar(50) comment '��������',
   enterprise_type      int comment '���ͣ�1���������ң�2�������̣�',
   primary key (enterprise_id)
);

alter table sys_enterpriseInfo comment '��ҵ��Ϣ��';

/*==============================================================*/
/* Table: sys_integrationCash                                   */
/*==============================================================*/
create table sys_integrationCash
(
   id                   int not null auto_increment,
   apply_user_id        bigint comment '�����û�id',
   integration          bigint comment '���ֻ���',
   withdrawalAmount     decimal(18,3) comment '���ֽ��',
   apply_time           datetime comment '��������ʱ��',
   withdraw_status      int comment '����״̬',
   operate_time         datetime comment '����ʱ�䣨���״̬��',
   user_id              bigint comment '����Աid',
   primary key (id)
);

alter table sys_integrationCash comment '���ֶ��ֱ�������Ա��';

/*==============================================================*/
/* Table: sys_order                                             */
/*==============================================================*/
create table sys_order
(
   order_id             varchar(100) not null,
   user_id              bigint comment '����Աid',
   order_create_time    datetime comment '��������ʱ��',
   user_integral_count  bigint comment 'ʹ�û�����',
   order_all_price      decimal(18,3) comment '�����ܼ۸�',
   order_type           int comment '����״̬(0��֧�� 1��֧�� 2������ 3�����)',
   down_id              int comment '�µ���ַID',
   order_address        varchar(100) comment '�µ���ַ',
   order_send_time      datetime comment 'Ҫ������ʱ��',
   order_detail_address varchar(200) comment '��ϸ��ַ',
   receiver_phone       varchar(100) comment '���ߵ绰',
   receiver_name        varchar(50) comment '��������',
   mark                 text comment '��ע',
   order_pay_type       int comment '����֧����ʽ��1��֧������2��΢�ţ�',
   primary key (order_id)
);

alter table sys_order comment '������';

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

alter table sys_orderDetail comment '������ϸ��';

/*==============================================================*/
/* Table: sys_orderIntegration                                  */
/*==============================================================*/
create table sys_orderIntegration
(
   id                   int not null auto_increment,
   user_id              bigint comment '�û�id',
   order_id             varchar(100) not null comment '����id',
   order_sum_price      decimal(18,3) comment '�������',
   integration          bigint comment '���ɻ���',
   price_integration_type int comment '�۸�������ͣ�1�����ͻ���?��2�����ۻ��֣�',
   primary key (id)
);

alter table sys_orderIntegration comment '�������ֱ�������Ա������Ա��';

/*==============================================================*/
/* Table: sys_product                                           */
/*==============================================================*/
create table sys_product
(
   product_id           bigint not null auto_increment comment '��ƷID',
   product_name         varchar(100) comment '��Ʒ����',
   product_picture_url  varchar(1024) comment 'ͼƬ��ַ',
   product_detail       text comment '���',
   product_type         int comment '��Ʒ���ͣ�1.�̻� 2.���� 3.�ײ� 4.С�̻�',
   product_video_url    varchar(1024) comment '��Ƶ��ַ',
   product_num          bigint comment '���',
   product_trade_price  decimal(18,3) comment '������',
   product_retail_price decimal(18,3) comment '���ۼ�',
   enterprise_id        bigint comment '��ҵID',
   is_hot               int comment '�Ƿ�������1��������2����������',
   enter_type           int comment '¼�����ͣ�1������Ա��2���������',
   primary key (product_id)
);

alter table sys_product comment '��Ʒ��';

/*==============================================================*/
/* Table: sys_productType                                       */
/*==============================================================*/
create table sys_productType
(
   id                   int not null auto_increment,
   type                 varchar(50),
   primary key (id)
);

alter table sys_productType comment '��Ʒ�����';

/*==============================================================*/
/* Table: sys_region                                            */
/*==============================================================*/
create table sys_region
(
   id                   int not null,
   name                 varchar(100) comment '������������',
   pid                  int comment '��ID',
   primary key (id)
);

alter table sys_region comment '����������';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              bigint not null comment '�û�id',
   username             varchar(50) not null comment '�û���',
   password             varchar(100) comment '����',
   salt                 varchar(20) comment '��',
   email                varchar(100) comment '����',
   mobile               varchar(100) comment '�ֻ���',
   "expiry date"        datetime comment '��������',
   create_user_id       bigint comment '������ID',
   create_time          datetime comment '����ʱ��',
   area_id              varchar(50) comment '��������ID',
   user_area            varchar(100) comment '����������',
   belong_to_agency     bigint comment '����������ID',
   user_permission      int comment '�û�Ȩ�ޣ�1������Ա��2�����ɳ��ң�3�������̣�4������Ա��5������Ա��6������Ա��',
   user_integral        bigint comment '����',
   primary key (user_id)
);

alter table sys_user comment 'ϵͳ�û�';

/*==============================================================*/
/* Table: sys_withdraw                                          */
/*==============================================================*/
create table sys_withdraw
(
   id                   int not null auto_increment,
   apply_user_id        bigint comment '�����û�id',
   withdrawalAmount     decimal(18,3) comment '���ֽ��',
   apply_time           datetime comment '��������ʱ��',
   withdraw_status      int comment '����״̬',
   operate_time         datetime comment '����ʱ�䣨���״̬��',
   user_id              bigint comment '����Աid',
   primary key (id)
);

alter table sys_withdraw comment '���ּ�¼���������̣�';

