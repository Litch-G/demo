package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token")String token);
    @Select("select * from user where name = #{name}")
    User findByName(@Param("name") String userName);
}
