
-- 权限功能表
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(64) DEFAULT '',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单',
  `link` varchar(64) DEFAULT NULL,
  `seq` tinyint DEFAULT 0 COMMENT '排序',
  `category` tinyint DEFAULT 1 COMMENT '1目录 2菜单 3按钮',
  `create_time` timestamp NOT NULL DEFAULT '2017-01-01 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 角色表
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) NOT NULL,
  `comments` varchar(64) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT '2017-01-01 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 角色权限关联表
DROP TABLE IF EXISTS `role_privilege_ref`;
CREATE TABLE `role_privilege_ref` (
  `role_id` int(11) NOT NULL DEFAULT 0,
  `privilege_id` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT 0,
  `account` varchar(64) NOT NULL,
  `nickname` varchar(32) DEFAULT '',
  `password` varchar(32) NOT NULL,
  `readonly` tinyint DEFAULT 0,
  `enable` tinyint DEFAULT 1,
  `is_super` tinyint DEFAULT 0,
  `email` varchar(32) DEFAULT '',
  `phone` varchar(16) DEFAULT '',
  `sex` tinyint DEFAULT 1 COMMENT '1男, 2女',
  `comments` varchar(255) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT '2017-01-01 00:00:00',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `USER_account_uindex` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表主键id',
  `level` enum('debug', 'info', 'error') DEFAULT 'info' COMMENT '日志等级',
  `module` varchar(32) DEFAULT '' COMMENT '模块',
  `client_ip` varchar(16) DEFAULT '' COMMENT '客户端IP',
  `os` varchar(16) DEFAULT '' COMMENT '操作系统',
  `browser` varchar(32) DEFAULT '' COMMENT '浏览器',
  `tag` varchar(32) DEFAULT '' COMMENT '标记',
  `content` varchar(255) DEFAULT '' COMMENT '内容',
  `user` varchar(32) DEFAULT '' COMMENT '操作人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
