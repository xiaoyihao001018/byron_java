-- 1. 先查看表结构
SHOW COLUMNS FROM sys_user;

-- 2. 删除关联表
DROP TABLE IF EXISTS sys_user_role;

-- 3. 删除角色表
DROP TABLE IF EXISTS sys_role;

-- 4. 重新创建用户表 (保留基本字段)
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
);

-- 5. 插入测试数据
INSERT INTO sys_user (username, password) 
VALUES ('admin', '$2a$10$QEzwJmWWzl0WNWN1vafRf.aS2dExqX6eysQfMMw0FXiF.FbM8GrFa');