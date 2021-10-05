BEGIN;
DROP DATABASE `db_sign_up`;
CREATE DATABASE `db_sign_up`;

USE db_sign_up;

CREATE TABLE `t_user`
(
    `uid`       int primary key auto_increment,
    `openid`    varchar(30) unique key NOT NULL,
    `phone`     varchar(11)            NOT NULL,
    `enroll`    int                    NOT NULL DEFAULT 100,
    `check`     int                    NOT NULL DEFAULT 0,
    `result`    varchar(600)           DEFAULT NULL,
    `que`       varchar(600)           DEFAULT NULL
)ENGINE = InnoDB
 DEFAULT CHARSET =utf8mb4;




CREATE TABLE `t_sign`
(
    `sid`       int primary key auto_increment,
    `uid`       int                    UNIQUE key NOT NULL,
    `name`      varchar(120)           NOT NULL,
    `grade`     int                    NOT NULL,
    `college`   int                    NOT NULL,
    `major`     varchar(120)           NOT NULL,
    `class`     varchar(120)           NOT NULL,
    `dsp`       varchar(900)           NOT NULL COMMENT '自我介绍',
    `dno`       int                    NOT NULL COMMENT '意向部门',
    `secdno`    int                    NOT NULL COMMENT '第二部门',
    `gender`    boolean                NOT NULL COMMENT '性别',
    `sno`       varchar(10)            NOT NULL COMMENT '学号',
    `qq`        varchar(13)            NOT NULL COMMENT 'qq',
    `domitory`  varchar(30)            NOT NULL COMMENT '宿舍',
    `know`      varchar(900)           NOT NULL COMMENT '如何了解协会',
    `party`     varchar(240)           NOT NULL COMMENT '已参加的社团'

)ENGINE = InnoDB
 DEFAULT CHARSET =utf8mb4;

CREATE TABLE `t_admin`
(
    `aid`       int primary key auto_increment,
    `openid`    varchar(30) unique key NOT NULL,
    `username`  varchar(120) unique key NOT NULL COMMENT '用户名',
    `adno`      int                    NOT NULL COMMENT '部门',
    `position`  int                    NOT NULL COMMENT '摊位',
    `password`  varchar(120)           NOT NULL COMMENT '密码',
    `phone`     varchar(11)            NOT NULL COMMENT '手机号'
)ENGINE = InnoDB
 DEFAULT CHARSET =utf8mb4;



CREATE TABLE `t_comment`
(
    `cid`       int primary key auto_increment,
    `aid`       int                    NOT NULL COMMENT '管理员id',
    `uid`       int                    NOT NULL COMMENT '用户id',
    `comment`   varchar(900)           DEFAULT null,
    `time`      DATETIME               NOT NULL

)ENGINE = InnoDB
 DEFAULT CHARSET =utf8mb4;

COMMIT;