CREATE TABLE book (
   `id` int(11) NOT NULL AUTO_INCREMENT,
  book_store_id int(11),
  name          VARCHAR(80),
  author        VARCHAR(80),
  price         DECIMAL(10,2),
  topic         VARCHAR(80),
  publish_date  DATE,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

CREATE TABLE book_store (
   `id` int(11) NOT NULL AUTO_INCREMENT,
  name         VARCHAR(80),
  address      VARCHAR(80),
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_NAME` varchar(100) DEFAULT NULL COMMENT '用户名',
  `USER_PWD` varchar(200) DEFAULT NULL COMMENT '密码',
  `BIRTHDAY` datetime DEFAULT NULL COMMENT '生日',
  `NAME` varchar(200) DEFAULT NULL COMMENT '姓名',
  `USER_ICON` varchar(500) DEFAULT NULL COMMENT '头像图片',
  `SEX` char(1) DEFAULT NULL COMMENT '性别, 1:男，2:女，3：保密',
  `NICKNAME` varchar(200) DEFAULT NULL COMMENT '昵称',
  `STAT` varchar(10) DEFAULT NULL COMMENT '用户状态，01:正常，02:冻结',
  `LAST_LOGIN_DATE` datetime DEFAULT NULL COMMENT '最后登录时间',
  `LAST_LOGIN_IP` varchar(100) DEFAULT NULL COMMENT '最后登录IP',
  `SRC_OPEN_USER_ID` bigint(20) DEFAULT NULL COMMENT '来源的联合登录',
  `EMAIL` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `MOBILE` varchar(50) DEFAULT NULL COMMENT '手机',
  `IS_DEL` char(1) DEFAULT '0' COMMENT '是否删除',
  `IS_EMAIL_CONFIRMED` char(1) DEFAULT '0' COMMENT '是否绑定邮箱',
  `IS_PHONE_CONFIRMED` char(1) DEFAULT '0' COMMENT '是否绑定手机',
  `CREATER` bigint(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `UPDATE_DATE` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改日期',
  `PWD_INTENSITY` char(1) DEFAULT NULL COMMENT '密码强度',
  `MOBILE_TGC` char(64) DEFAULT NULL COMMENT '手机登录标识',
  `MAC` char(64) DEFAULT NULL COMMENT 'mac地址',
  `SOURCE` char(1) DEFAULT '0' COMMENT '1:WEB,2:IOS,3:ANDROID,4:WIFI,5:管理系统, 0:未知',
  `ACTIVATE` char(1) DEFAULT '1' COMMENT '激活，1：激活，0：未激活',
  `ACTIVATE_TYPE` char(1) DEFAULT '0' COMMENT '激活类型，0：自动，1：手动',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USER_NAME` (`USER_NAME`),
  KEY `MOBILE` (`MOBILE`),
  KEY `IDX_MOBILE_TGC` (`MOBILE_TGC`,`ID`),
  KEY `IDX_EMAIL` (`EMAIL`,`ID`),
  KEY `IDX_CREATE_DATE` (`CREATE_DATE`,`ID`),
  KEY `IDX_UPDATE_DATE` (`UPDATE_DATE`)
) ENGINE=InnoDB AUTO_INCREMENT=7122681 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_description` varchar(500) DEFAULT NULL,
  `accesses` varchar(500) DEFAULT NULL,
  `permissions` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `boss`.`picture` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `picture_file` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE `sqlscripts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sqlscript` varchar(8000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;


INSERT INTO book_store VALUES ('1', '新华书店', '湖北省武汉市洪山区文秀街131号');

INSERT INTO book VALUES ('1', '1', '社会研究方法教程', '袁方', '68.00', '社会学', '2015-03-01');
INSERT INTO book VALUES ('2', '1', '算法', '高德纳', '108.00', '数据结构', '2014-02-13');
INSERT INTO book VALUES ('3', '1', 'Java核心技术Ⅰ', 'Cay', '93.00', '编程语言', '2011-06-14');
INSERT INTO book VALUES ('4', '1', '现代操作系统', 'William', '56.50', '操作系统', '2016-08-23');
INSERT INTO book VALUES ('5', '1', 'Head First设计模式', 'Freeman', '32.00', '设计模式', '2013-10-15');
INSERT INTO book VALUES ('6', '1', '学习OpenCV', 'Bradski', '46.00', '技术', '2014-02-13');
INSERT INTO book VALUES ('7', '1', '小王子', '周克希', '15.00', '文学', '2008-07-13');
INSERT INTO book VALUES ('8', '1', 'Effective Java', 'Bloch', '38.00', '编程语言', '2014-12-03');
INSERT INTO book VALUES ('9', '1', '编程珠玑', 'Jon', '36.00', '数据结构', '2013-12-03');
INSERT INTO book VALUES ('10', '1', 'SQL必知必会', 'Ben', '13.00', '数据库', '2015-08-26');
INSERT INTO book VALUES ('11', '1', '编译器设计', 'Kelth', '59.00', '编译器', '2014-08-13');


INSERT INTO `role`(`id`,`role_description`,`accesses`,`permissions`)VALUES('1', 'admin role', 'ad,mu,mc,mb,mbl,mbd', 'ad,mu,mc,mb,mbl,mbd');
INSERT INTO `role`(`id`,`role_description`,`accesses`,`permissions`)VALUES('1', 'user role', 'ad,mu,mc,mb,mbl,mbd', 'ad,mu,mc,mb,mbl,mbd');

INSERT INTO `user` (`ID`, `USER_NAME`, `USER_PWD`, `NAME`) VALUES ('1', 'admin', 'admin', 'admin');
INSERT INTO `user` (`ID`, `USER_NAME`, `USER_PWD`, `NAME`) VALUES ('2', 'test', 'test', 'test');
INSERT INTO `user` (`ID`, `USER_NAME`, `USER_PWD`, `NAME`) VALUES ('3', 'test1', 'test1', 'test1');
INSERT INTO `user` (`ID`, `USER_NAME`, `USER_PWD`, `NAME`) VALUES ('4', 'test2', 'test2', 'test2');

INSERT INTO `user_role`(`id`,`user_id`,`user_role_id`)VALUES('1', '1', '1');
INSERT INTO `user_role`(`id`,`user_id`,`user_role_id`)VALUES('2', '2', '2');


DROP PROCEDURE IF EXISTS `Insert_update_user`;
DELIMITER $$
CREATE PROCEDURE `Insert_update_user`(In id int, IN uname varchar(100),IN email varchar(200), IN roleId int)
BEGIN
	if id=0 then
		insert into `user`(user_name, email) values(uname,email);
        insert into user_role(user_id,user_role_id) values(@@IDENTITY,roleId);
    else
		update `user` set user_name=uname,email=email where id=id;
        update user_role set user_role_id=roleId where user_id=id;
    end if;
    
END
$$
DELIMITER ;



DROP PROCEDURE IF EXISTS `search_user`;
DELIMITER $$
CREATE PROCEDURE `search_user`(IN uname varchar(100), IN roleId int, IN pageIndex int, IN pageSize int)
BEGIN
	declare ssql varchar(8000);
    declare ssqlc varchar(8000);
	declare sqlwhere varchar(8000);   
    declare lmt varchar(100);
    set lmt=concat(' LIMIT ', pageIndex , ',' , pageSize);
    set sqlwhere=' where 1=1 ';
    set ssql='select * from `user` u inner join user_role  ur on u.ID=ur.user_id ';
    set ssqlc='select count(1) as count from `user` u inner join user_role  ur on u.ID=ur.user_id ';
    if uname<>'' then
		set sqlwhere=concat(sqlwhere , ' and u.user_name like ''%',uname,'%''');
	end if;
	if roleId>0 then
		set sqlwhere=concat(sqlwhere ,' and ur.user_role_id=''',roleId,'''');
	end if;
    set ssqlc=concat(ssqlc,sqlwhere);
    
	set sqlwhere=concat(sqlwhere,lmt);
    
    set ssql=concat(ssql,sqlwhere);
       
    select ssql;
    
    SET @sql1=ssql;
    PREPARE stmt1 FROM @sql1;
	EXECUTE stmt1;
	DEALLOCATE PREPARE stmt1 ;
    
    select ssqlc;
    SET @sql2=ssqlc;
    PREPARE stmt2 FROM @sql2;
	EXECUTE stmt2;
	DEALLOCATE PREPARE stmt2 ;
END
$$
DELIMITER ;
