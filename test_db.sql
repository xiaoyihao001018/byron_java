-- 创建数据库
CREATE DATABASE IF NOT EXISTS test_db;
USE test_db;

-- 创建系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    status TINYINT DEFAULT 1 COMMENT '1:启用 0:禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 创建角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL,
    role_code VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 创建用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES sys_user(id),
    FOREIGN KEY (role_id) REFERENCES sys_role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 插入测试角色数据
INSERT INTO sys_role (role_name, role_code, description) VALUES 
('管理员', 'ROLE_ADMIN', '系统管理员'),
('普通用户', 'ROLE_USER', '普通用户');

-- 插入测试用户数据（密码：123456）
INSERT INTO sys_user (username, password, nickname, status) VALUES
('admin', '$2a$10$X/uMNuiw3UZKzefO5w.NTOoEdxD7ZN3GE3z3uqyoEZuH.PE.8k6.2', '管理员', 1),
('user', '$2a$10$X/uMNuiw3UZKzefO5w.NTOoEdxD7ZN3GE3z3uqyoEZuH.PE.8k6.2', '测试用户', 1);

-- 关联用户和角色
INSERT INTO sys_user_role (user_id, role_id) VALUES 
(1, 1), -- admin用户关联管理员角色
(2, 2); -- user用户关联普通用户角色

-- 创建存储过程生成更多测试用户数据
DELIMITER //
CREATE PROCEDURE generate_test_users(IN num INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    WHILE i < num DO
        INSERT INTO sys_user (username, password, nickname, status) VALUES
        (
            CONCAT('test_user_', i),
            '$2a$10$X/uMNuiw3UZKzefO5w.NTOoEdxD7ZN3GE3z3uqyoEZuH.PE.8k6.2',
            CONCAT('测试用户', i),
            1
        );
        -- 为新用户分配普通用户角色
        INSERT INTO sys_user_role (user_id, role_id) VALUES (LAST_INSERT_ID(), 2);
        SET i = i + 1;
    END WHILE;
END //
DELIMITER ;

-- 执行存储过程生成10个测试用户（可选）
-- CALL generate_test_users(10);

-- 删除存储过程（可选）
-- DROP PROCEDURE IF EXISTS generate_test_users;