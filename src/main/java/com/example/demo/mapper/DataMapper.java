package com.example.demo.mapper;


import com.example.demo.model.Data_Publish;
import org.apache.ibatis.annotations.*;

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

    @Select("select *from data_publish where creator = #{id} limit #{offset} , #{size}")
    List<Data_Publish> userList(@Param(value = "id")Integer id,@Param(value = "offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from data_publish where creator = #{id}")
    Integer selectCountByUserId(@Param(value = "id")Integer id);

    @Select("select *from data_publish where id = #{id}")
    Data_Publish getById(@Param("id") Integer id);

    @Update("update data_publish set title = #{title},description = #{description}, gme_modified =#{gmtModified},tag = #{tag} where id = #{id}")
    int updata(Data_Publish data);

    @Update("update data_publish set  view_count =view_count+#{viewCount} where id = #{id}")
    int updataView(Data_Publish data);

    @Update("update data_publish set comment_count = comment_count+1 where id = #{id}")
    void commentCount(Data_Publish dataPublish);

}
