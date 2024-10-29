/*
Navicat MySQL Data Transfer

Source Server         : local3306
Source Server Version : 80039
Source Host           : localhost:3306
Source Database       : test_db

Target Server Type    : MYSQL
Target Server Version : 80039
File Encoding         : 65001

Date: 2024-10-29 19:59:30
*/
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `permission_code` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_code` (`permission_code`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '用户查询', 'USER:READ', '查询用户信息', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_permission` VALUES ('2', '用户创建', 'USER:CREATE', '创建新用户', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_permission` VALUES ('3', '用户修改', 'USER:UPDATE', '修改用户信息', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_permission` VALUES ('4', '用户删除', 'USER:DELETE', '删除用户', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_permission` VALUES ('5', '角色查询', 'ROLE:READ', '查询角色信息', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_permission` VALUES ('6', '角色创建', 'ROLE:CREATE', '创建新角色', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_permission` VALUES ('7', '角色修改', 'ROLE:UPDATE', '修改角色信息', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_permission` VALUES ('8', '角色删除', 'ROLE:DELETE', '删除角色', '2024-10-29 18:45:20', '2024-10-29 18:45:20');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `role_code` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '系统管理员', 'ROLE_ADMIN', '系统管理员，拥有所有权限', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_role` VALUES ('2', '普通用户', 'ROLE_USER', '普通用户，拥有基本权限', '2024-10-29 18:45:20', '2024-10-29 18:45:20');
INSERT INTO `sys_role` VALUES ('3', '测试用户', 'ROLE_TEST', '测试用户，用于测试', '2024-10-29 18:45:20', '2024-10-29 18:45:20');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '2');
INSERT INTO `sys_role_permission` VALUES ('1', '3');
INSERT INTO `sys_role_permission` VALUES ('1', '4');
INSERT INTO `sys_role_permission` VALUES ('1', '5');
INSERT INTO `sys_role_permission` VALUES ('1', '6');
INSERT INTO `sys_role_permission` VALUES ('1', '7');
INSERT INTO `sys_role_permission` VALUES ('1', '8');
INSERT INTO `sys_role_permission` VALUES ('2', '1');
INSERT INTO `sys_role_permission` VALUES ('2', '5');
INSERT INTO `sys_role_permission` VALUES ('3', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '2');
INSERT INTO `sys_role_permission` VALUES ('3', '5');
INSERT INTO `sys_role_permission` VALUES ('3', '6');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` tinyint DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$QEzwJmWWzl0WNWN1vafRf.aS2dExqX6eysQfMMw0FXiF.FbM8GrFa', '系统管理员', '1', '2024-10-29 18:45:20', '2024-10-29 18:55:56');
INSERT INTO `sys_user` VALUES ('2', 'test', '$2a$10$QEzwJmWWzl0WNWN1vafRf.aS2dExqX6eysQfMMw0FXiF.FbM8GrFa', '测试用户', '1', '2024-10-29 18:45:20', '2024-10-29 18:55:57');
INSERT INTO `sys_user` VALUES ('3', 'user', '$2a$10$QEzwJmWWzl0WNWN1vafRf.aS2dExqX6eysQfMMw0FXiF.FbM8GrFa', '普通用户', '1', '2024-10-29 18:45:20', '2024-10-29 18:55:59');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2');
INSERT INTO `sys_user_role` VALUES ('2', '3');
INSERT INTO `sys_user_role` VALUES ('3', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三', '25', 'zhangsan@example.com');
INSERT INTO `user` VALUES ('2', '李四', '30', 'lisi@example.com');
INSERT INTO `user` VALUES ('3', '王五', '28', 'wangwu@example.com');
INSERT INTO `user` VALUES ('4', '赵六', '35', 'zhaoliu@example.com');
INSERT INTO `user` VALUES ('5', '孙七', '22', 'sunqi@example.com');
INSERT INTO `user` VALUES ('6', '周八', '27', 'zhouba@example.com');
INSERT INTO `user` VALUES ('7', '吴九', '33', 'wujiu@example.com');
INSERT INTO `user` VALUES ('8', '郑十', '29', 'zhengshi@example.com');
INSERT INTO `user` VALUES ('9', '刘一', '31', 'liuyi@example.com');
INSERT INTO `user` VALUES ('10', '陈二', '26', 'chener@example.com');
