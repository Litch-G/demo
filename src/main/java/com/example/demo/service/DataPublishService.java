package com.example.demo.service;

import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.DTO.PaginationDTO;
import com.example.demo.DTO.SearchInfoDTO;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.DataMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Data_Publish;
import com.example.demo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataPublishService {
    @Autowired
    DataMapper dataMapper;
    @Autowired
    UserMapper userMapper;

    //首页面的分页逻辑
    public PaginationDTO list(Integer page, Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = dataMapper.selectCount();
        paginationDTO.setPagination(totalCount,page,size);

        if(page<1) page=1;//越界处理
        if(page>paginationDTO.getTotalPage()) page=paginationDTO.getTotalPage();
        Integer offset = size*(page-1);
        List<Data_Publish> data_publishes = dataMapper.list(offset,size);
        List<DataPublishDTO> dataPublishDTOList = new ArrayList<>();

        //拿到总表
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
//    搜索分页逻辑
    public PaginationDTO list(String search,Integer page, Integer size){
        if(StringUtils.isNotBlank(search)){
            String[] split = StringUtils.split(search, " ");
            search = Arrays.stream(split).collect(Collectors.joining("|"));
            SearchInfoDTO searchInfoDTO = new SearchInfoDTO();
            searchInfoDTO.setSearch(search);
            PaginationDTO paginationDTO = new PaginationDTO();
            Integer totalCount = dataMapper.countBySearch(searchInfoDTO);
            paginationDTO.setPagination(totalCount,page,size);
            if(page<1) page=1;//越界处理
            if(page>paginationDTO.getTotalPage()) page=paginationDTO.getTotalPage();
            Integer offset = size*(page-1);
            searchInfoDTO.setPage(offset);
            searchInfoDTO.setSize(size);
            List<Data_Publish> data_publishes = dataMapper.selectBySeach(searchInfoDTO);
            List<DataPublishDTO> dataPublishDTOList = new ArrayList<>();
            //拿到总表
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
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = dataMapper.selectCount();
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1) page=1;//越界处理
        if(page>paginationDTO.getTotalPage()) page=paginationDTO.getTotalPage();
        Integer offset = size*(page-1);
        List<Data_Publish> data_publishes = dataMapper.list(offset,size);
        List<DataPublishDTO> dataPublishDTOList = new ArrayList<>();

        //拿到总表
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
    //个人信息页面的分页逻辑
    public PaginationDTO list(Integer id,Integer page, Integer size){

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = dataMapper.selectCountByUserId(id);
        paginationDTO.setPagination(totalCount,page,size);

        if(page<1) page=1;
        if(page>paginationDTO.getTotalPage()) page=paginationDTO.getTotalPage();
        Integer offset = size*(page-1);
        List<Data_Publish> data_publishes = dataMapper.userList(id,offset,size);
        List<DataPublishDTO> dataPublishDTOList = new ArrayList<>();

        for (Data_Publish dataPublish : data_publishes){
            User user = userMapper.findById(id);
            DataPublishDTO dataPublishDTO = new DataPublishDTO();
            BeanUtils.copyProperties(dataPublish,dataPublishDTO);
            dataPublishDTO.setUser(user);
            dataPublishDTOList.add(dataPublishDTO);
        }
        paginationDTO.setDataPublishDTOList(dataPublishDTOList);
        return paginationDTO;
    }

    public DataPublishDTO getById(Integer id) {
        Data_Publish dataPublish = dataMapper.getById(id);
        if(dataPublish == null){
            throw new CustomizeException("分享不存在，请返回主页重试");
        }
        DataPublishDTO dataPublishDTO = new DataPublishDTO();
        BeanUtils.copyProperties(dataPublish,dataPublishDTO);
        User user = userMapper.findById(dataPublish.getCreator());
        dataPublishDTO.setUser(user);
        return dataPublishDTO;
    }

    public void createOrUpdata(Data_Publish data) {
        if (data.getId() == null){
            data.setGmtCreate(System.currentTimeMillis());
            data.setGmtModified(data.getGmtCreate());
            data.setCommentCount(0);
            data.setLikeCount(0);
            data.setViewCount(0);
            dataMapper.create(data);
            Data_Publish dataPublish =dataMapper.getById(data.getCreator());
        }
        else {
            //updata
            Data_Publish dataPublish = new Data_Publish();
            dataPublish.setGmtModified(System.currentTimeMillis());
            dataPublish.setTitle(data.getTitle());
            dataPublish.setDescription(data.getDescription());
            dataPublish.setTag(data.getTag());
            dataPublish.setCreator(data.getId());
            Integer status = dataMapper.updata(data);
            if(status != 1){
                throw new CustomizeException("内容更新失败");
            }
        }
    }

    public void addView(Integer id) {
        Data_Publish dataPublish = dataMapper.getById(id);
        Data_Publish updata = new Data_Publish();
        updata.setId(id);
        updata.setViewCount(1);
        dataMapper.updataView(updata);
    }
    //相关数据展示
    public List<DataPublishDTO> selectRelated(DataPublishDTO dataPublishDTO){
        if(StringUtils.isBlank(dataPublishDTO.getTag())){
            return new ArrayList<>();
        }

        String[] split = StringUtils.split(dataPublishDTO.getTag(), ",");
        String collect = Arrays.stream(split).collect(Collectors.joining("|"));
        Data_Publish dataPublish = new Data_Publish();
        dataPublish.setId(dataPublishDTO.getId());
        dataPublish.setTag(collect);
        List<Data_Publish> data_publishes = dataMapper.tagSearch(dataPublish);
        List<DataPublishDTO> dataPublishDTOS = data_publishes.stream().map(q -> {
            DataPublishDTO dto = new DataPublishDTO();
            BeanUtils.copyProperties(q,dto);
            return dto;
        }).collect(Collectors.toList());
        return  dataPublishDTOS;
    }

}
