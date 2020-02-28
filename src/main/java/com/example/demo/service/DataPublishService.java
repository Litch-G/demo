package com.example.demo.service;

import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.DTO.PaginationDTO;
import com.example.demo.mapper.DataMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Data_Publish;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataPublishService {
    @Autowired
    DataMapper dataMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = dataMapper.selectCount();
        paginationDTO.setPagination(totalCount,page,size);

        if(page<1) page=1;
        if(page>paginationDTO.getTotalPage()) page=paginationDTO.getTotalPage();
        Integer offset = size*(page-1);
        List<Data_Publish> data_publishes = dataMapper.list(offset,size);
        List<DataPublishDTO> dataPublishDTOList = new ArrayList<>();

        for (Data_Publish dataPublish : data_publishes){
            User user = userMapper.findById(dataPublish.getCreator());
            DataPublishDTO dataPublishDTO = new DataPublishDTO();
            BeanUtils.copyProperties(dataPublish,dataPublishDTO);
            dataPublishDTO.setUser(user);
            dataPublishDTOList.add(dataPublishDTO);
        }
        paginationDTO.setDataPublishDTOList(dataPublishDTOList);
        return paginationDTO;
    }
}
