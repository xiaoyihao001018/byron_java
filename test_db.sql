-- 删除已存在的表（按照外键关系的顺序删除）
DROP TABLE IF EXISTS sys_user_role;
DROP TABLE IF EXISTS sys_role;
DROP TABLE IF EXISTS sys_user;

-- 创建用户表
CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT uk_username UNIQUE (username)
) COMMENT '系统用户表';

-- 创建角色表
CREATE TABLE sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(50) NOT NULL COMMENT '角色编码',
    description VARCHAR(100) COMMENT '角色描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT uk_role_code UNIQUE (role_code)
) COMMENT '系统角色表';

-- 创建用户角色关联表
CREATE TABLE sys_user_role (
    user_id BIGINT COMMENT '用户ID',
    role_id BIGINT COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id) ON DELETE CASCADE,
    CONSTRAINT fk_ur_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id) ON DELETE CASCADE
) COMMENT '用户角色关联表';

-- 插入初始角色数据
INSERT INTO sys_role (role_name, role_code, description) VALUES 
('系统管理员', 'ADMIN', '系统管理员，拥有所有权限'),
('普通用户', 'USER', '普通用户，拥有基本权限');

-- 插入管理员用户
-- 密码为 123456 的 BCrypt 加密结果
INSERT INTO sys_user (username, password, nickname, status) VALUES 
('admin', '$2a$10$X8/iXjkwlVVSfY0dYhJ/9.KWXUUqGBMqyj0lTYtYA0CLPvGX4TR4S', '系统管理员', 1);

-- 给管理员分配角色
INSERT INTO sys_user_role (user_id, role_id) 
SELECT u.id, r.id 
FROM sys_user u, sys_role r 
WHERE u.username = 'admin' AND r.role_code = 'ADMIN';