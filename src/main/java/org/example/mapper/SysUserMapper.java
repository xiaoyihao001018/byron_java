package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.entity.SysUser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 系统用户Mapper接口
 * 继承MyBatis-Plus的BaseMapper，提供基础的CRUD功能
 */
@Mapper  // 标记这是一个MyBatis的Mapper接口
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    /**
     * 根据用户名查询用户信息
     * 使用@Select注解直接编写SQL语句
     * 
     * @param username 用户名
     * @return 返回用户实体，如果未找到则返回null
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    SysUser findByUsername(@Param("username") String username);
} 