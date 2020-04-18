package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.DTO.CommentCreateDTO;
import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.DataPublishService;
import com.example.demo.spark.StringExu;
import com.example.demo.time.RequestLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller

public class DataController {
    @Autowired
    private DataPublishService service;
    @Autowired
    private CommentService commentService;
    @GetMapping("/dataList/{id}")

    public String dataList(@PathVariable(name = "id")Integer id,
                           Model model,HttpServletRequest request){
        DataPublishDTO dataPublishDTO = service.getById(id);
        List<CommentDTO> commentDTOS = commentService.listByParentId(id);
        List<DataPublishDTO> dataPublishDTOList = service.selectRelated(dataPublishDTO);
        service.addView(id);
        model.addAttribute("data",dataPublishDTO);
        model.addAttribute("comments",commentDTOS);
        model.addAttribute("relatedPublish",dataPublishDTOList);
        //jsonObject.putAll(map);
        User user = (User)request.getSession().getAttribute("user");
        Map<String,Integer> map = StringExu.exu(user.getName()+"_"+dataPublishDTO.getTitle()+".txt");
        model.addAttribute("map",map);
        return "datalist";
    }
}
