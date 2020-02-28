package com.example.demo.mapper;


import com.example.demo.model.Data_Publish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DataMapper {
    @Insert("insert into data_publish (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Data_Publish data);

    @Select("select * from data_publish limit #{offset},#{size}")
    List<Data_Publish> list(@Param(value = "offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from data_publish")
    Integer selectCount();
}
