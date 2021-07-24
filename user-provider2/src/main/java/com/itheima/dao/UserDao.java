package com.itheima.dao;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select(value = "select * from tb_user where id=#{id}")
    public User findById(Integer id);
}
