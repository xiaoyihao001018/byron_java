-- 插入测试用户数据（密码：123456）
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `status`) VALUES
('admin', '$2a$10$X/uMNuiw3UZKzefO5w.NTOoEdxD7ZN3GE3z3uqyoEZuH.PE.8k6.2', '管理员', 1);

-- 插入角色数据
INSERT INTO `sys_role` (`role_name`, `role_code`, `description`) VALUES
('管理员', 'ROLE_ADMIN', '系统管理员'),
('普通用户', 'ROLE_USER', '普通用户');

-- 关联用户和角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1);  -- admin用户关联管理员角色