package com.example.demo.mapper;

import com.example.demo.model.FileDemo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    @Insert("insert into file (creator,path) values (#{creator},#{path})")
    void create(FileDemo fileDemo);
}
