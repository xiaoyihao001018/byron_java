package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.example.entity.SysUser;
import org.example.entity.SysRole;
import org.example.entity.SysUserRole;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    SysUser findByUsername(String username);
    
    @Select("SELECT r.role_code FROM sys_role r " +
            "JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> findUserRoles(Long userId);
    
    @Select("SELECT r.* FROM sys_role r " +
            "JOIN sys_user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<SysRole> findUserRoleList(Long userId);
    
    @Insert("INSERT INTO sys_user_role (user_id, role_id) VALUES (#{userId}, #{roleId})")
    void insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    @Delete("DELETE FROM sys_user_role WHERE user_id = #{userId}")
    void deleteUserRoles(Long userId);
    
    @Insert({
        "<script>",
        "INSERT INTO sys_user_role (user_id, role_id) VALUES ",
        "<foreach collection='userRoles' item='item' separator=','>",
        "(#{item.userId}, #{item.roleId})",
        "</foreach>",
        "</script>"
    })
    void batchInsertUserRoles(@Param("userRoles") List<SysUserRole> userRoles);
} 