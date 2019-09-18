package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User findUserByUsername(String username);


    void add(User user);

    Page<User> selectByCondition(String queryString);

    @Delete("delete from t_User where id=#{id}")
    void delete(Integer id);


    @Select("select * from t_User where id=#{id}")
    User findById(Integer id);

    void edit(User User);

    void setUserAndRole(@Param("id") Integer id, @Param("roleId") Integer roleId);

    @Select("select Role_id from t_User_Role where User_id=#{id}")
    List<Integer> findRoleIdsById(Integer id);

    @Delete("delete from t_User_Role where User_id=#{id}")
    void deleteUserAndRole(Integer id);
}
