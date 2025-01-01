# 数据库初始化

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
DROP TABLE IF EXISTS `user`;
create table if not exists `user`
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    phone        varchar(128)                           null comment '电话',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除'
) comment '用户' collate = utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `interface_info`;

CREATE TABLE `interface_info` (
  `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name`           varchar(20)  NULL     DEFAULT NULL COMMENT '接口名称',
  `description`    varchar(512) NULL     DEFAULT NULL COMMENT '接口描述',
  `url`            varchar(512) NOT NULL COMMENT '接口地址',
  `method`         varchar(20)  NOT NULL COMMENT '请求类型',
  `requestHeader`  text         NULL     COMMENT '请求头',
  `responseHeader` text         NULL     COMMENT '响应头',
  `status`         tinyint(1)   NOT NULL DEFAULT 0 COMMENT '接口状态（0 关闭 1 开启）',
  `userId`         bigint(20)   NOT NULL COMMENT '创建人id',
  `userName`       varchar(20)  NOT NULL COMMENT '创建人姓名',
  `createTime`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP
      ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete`       tinyint(4)   NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '接口信息表';

-- 用户调用接口关系表
create table if not exists `user_interface_info`
(
    `id` bigint not null auto_increment comment '主键' primary key,
    `userId` bigint not null comment '调用用户 id',
    `interfaceInfoId` bigint not null comment '接口 id',
    `totalNum` int default 0 not null comment '总调用次数',
    `leftNum` int default 0 not null comment '剩余调用次数',
    `status` int default 0 not null comment '0-正常，1-禁用',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    `isDelete` tinyint default 0 not null comment '是否删除(0-未删, 1-已删)'
) comment '用户调用接口关系';



-- API使用记录表
CREATE TABLE IF NOT EXISTS `interface_usage`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `userId` bigint NOT NULL COMMENT '用户 id',
    `interfaceInfoId` bigint NOT NULL COMMENT '接口 id',
    `transactionId` bigint COMMENT '积分交易 id',
    `createTime` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updateTime` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete` tinyint DEFAULT 0 NOT NULL COMMENT '是否删除'
) COMMENT 'API使用记录';


-- 积分交易表
CREATE TABLE IF NOT EXISTS `point_transaction`
(
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `userId` bigint NOT NULL COMMENT '用户 id',
    `interfaceInfoId` bigint COMMENT '接口 id',
    `pointsChange` int NOT NULL COMMENT '积分变动数量',
    `type` varchar(256) NOT NULL COMMENT '交易类型',
    `description` text COMMENT '描述',
    `createTime` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    `updateTime` datetime DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete` tinyint DEFAULT 0 NOT NULL COMMENT '是否删除'
) COMMENT '积分交易';
