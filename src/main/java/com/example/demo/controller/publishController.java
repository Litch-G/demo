package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.DataMapper;
import com.example.demo.mapper.FileMapper;
import com.example.demo.model.Data_Publish;
import com.example.demo.model.FileDemo;
import com.example.demo.model.User;
import com.example.demo.service.DataPublishService;
import com.example.demo.spark.StringExu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
public class publishController {
    @Autowired
    FileMapper fileMapper;
    @Autowired
    DataPublishService dataPublishService;
    @Autowired
    DataMapper dataMapper;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model){
        DataPublishDTO dataPublish = dataPublishService.getById(id);
        model.addAttribute("title",dataPublish.getTitle());
        model.addAttribute("description",dataPublish.getDescription());
        model.addAttribute("tag",dataPublish.getTag());
        model.addAttribute("id",dataPublish.getId());
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title") String title,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "tag") String tag,
                            @RequestParam(name = "id",required = false)Integer id,
                            @RequestParam(name = "file") MultipartFile file,
                            HttpServletRequest request,
                            Model model){

        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //差错检查
        if(title == null ||title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if( description== null ||description.equals("")){
            model.addAttribute("error","description不能为空");
            return "publish";
        }

        if(tag == null ||tag.equals("")){
            model.addAttribute("error","tag不能为空");
            return "publish";
        }
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Data_Publish data = new Data_Publish();
        data.setId(id);
        data.setTitle(title);
        data.setDescription(description);
        data.setTag(tag);
        data.setCreator(1);
        data.setGmtCreate(System.currentTimeMillis());
        data.setGmtModified(data.getGmtCreate());
        User user1 = (User)request.getSession().getAttribute("user");
        data.setCreator(user1.getId());
        dataPublishService.createOrUpdata(data);
        //文件上传
        if (file.isEmpty()){
            throw new CustomizeException(CustomizeErrorCode.SYSTEM_ERROR);
        }
        String fileName = file.getOriginalFilename();
        String filePath ="F:\\upload\\demo_";
        File dest = new File(filePath+user.getName()+"_"+title+".txt");
        try {
            file.transferTo(dest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件保存
        FileDemo fileDemo = new FileDemo();
        Integer fid = dataMapper.selectCount();
        fileDemo.setCreator(fid+1);
        fileDemo.setPath(filePath+"_"+user.getName()+"_"+fileName);
        fileMapper.create(fileDemo);
        //跳转
        return "redirect:/";
    }

}
