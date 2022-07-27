/*
 Target Server Type    : MySQL
*/

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id`TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `name` varchar(30) NULL DEFAULT NULL,
  `permission` varchar(20) NULL DEFAULT NULL,
  `url` varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '用户查询', 'user:view', 'user/list');
INSERT INTO `sys_permission` VALUES (2, '用户添加', 'user:add', 'user/add');
INSERT INTO `sys_permission` VALUES (3, '用户编辑', 'user:edit', 'user/edit');
INSERT INTO `sys_permission` VALUES (4, '用户删除', 'user:del', 'user/del');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `description` varchar(50) NULL DEFAULT NULL,
  `role` varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin');
INSERT INTO `sys_role` VALUES (2, '主管', 'manager');
INSERT INTO `sys_role` VALUES (3, '员工', 'employee');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `permission_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
-- 如果数据库中已经配置了权限，但一直提示:
--   Subject does not have permission　[XXXX]
--   org.apache.shiro.authz.AuthorizationException: Not authorized to invoke method:
-- 这是因为修改了数据库权限配置后，redis缓存出现的问题，需要重新清除缓存　redis-cli.exe -h 127.0.0.1 -p 6379:
--   keys *
--   del xxx
INSERT INTO `sys_role_permission`(role_id, permission_id) VALUES (1, 1);
INSERT INTO `sys_role_permission`(role_id, permission_id) VALUES (1, 2);
INSERT INTO `sys_role_permission`(role_id, permission_id) VALUES (1, 3);
INSERT INTO `sys_role_permission`(role_id, permission_id) VALUES (1, 4);
-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录号',
  `role_id` int(11) NOT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 3, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录号',
  jobno VARCHAR(5) NOT NULL UNIQUE COMMENT '工号',
  username VARCHAR(30) NOT NULL UNIQUE COMMENT '员工名字',
  password VARCHAR(60) NOT NULL COMMENT '密码',
  salt VARCHAR(30) COMMENT '加盐',
  sex VARCHAR(1) COMMENT '性别',
  age int(3) COMMENT '年龄',
  mobile VARCHAR(11) COMMENT '手机号',
  email VARCHAR(40) COMMENT '电子邮箱',
  address VARCHAR(80) COMMENT '住址',
  idno VARCHAR(18) COMMENT '身份证号',
  department VARCHAR(20) COMMENT '部门',
  status VARCHAR(1) COMMENT '状态：1在职,2离职',
  created_by int COMMENT '生成者',
  created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '生成时间',
  modified_by int COMMENT '修改者',
  modified_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP  COMMENT '修改时间',
  PRIMARY KEY(id),
  constraint `fk_role_id` foreign key (`role_Id`) references sys_role(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`(id, jobno, username, password, salt, status) VALUES (1, '1001', 'admin', 'd547d0d15e550fb68c3962137e5fb8e3', '', '1');
INSERT INTO `user`(id, jobno, username, password, salt, status)  VALUES (2, '1002', 'test', '402fe8c0a3bcea3d481ba7cba95aa221', NULL, '1');

-- SET FOREIGN_KEY_CHECKS = 1;


--======================================================================================
CREATE TABLE `sequence` (
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '序列的名字',
  `current_value` int(11) NOT NULL COMMENT '序列的当前值',
  `increment` int(11) NOT NULL DEFAULT '1' COMMENT '序列的自增值',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP FUNCTION IF EXISTS currval;
DELIMITER $
CREATE FUNCTION currval (seq_name VARCHAR(50))
RETURNS INTEGER
LANGUAGE SQL
DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
DECLARE value INTEGER;
SET value = 0;
SELECT current_value INTO value
FROM sequence
WHERE name = seq_name;
RETURN value;
END
$
DELIMITER ;

DELIMITER ;

DROP FUNCTION IF EXISTS setval;
DELIMITER $
CREATE FUNCTION setval (seq_name VARCHAR(50), value INTEGER)
RETURNS INTEGER
LANGUAGE SQL
DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
UPDATE sequence
SET current_value = value
WHERE name = seq_name;
RETURN currval(seq_name);
END
$
DELIMITER ;


DROP FUNCTION IF EXISTS nextval;
DELIMITER $
CREATE FUNCTION nextval (seq_name VARCHAR(50))
RETURNS INTEGER
LANGUAGE SQL
DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
UPDATE sequence
SET current_value = current_value + increment
WHERE name = seq_name;
RETURN currval(seq_name);
END
$
DELIMITER ;

INSERT INTO sequence VALUES ('hotel_sequence', 0, 1);
INSERT INTO sequence VALUES ('coupon_sequence', 0, 1);
--SELECT SETVAL('hotel_sequence', 0)；
--SELECT SETVAL('coupon_sequence', 0);-- 设置指定sequence的初始值
--SELECT CURRVAL('coupon_sequence');-- 查询指定sequence的当前值
--SELECT NEXTVAL('coupon_sequence');-- 查询指定sequence的下一个值
--======================================================================================

