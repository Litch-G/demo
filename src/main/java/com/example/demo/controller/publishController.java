package com.example.demo.controller;

import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.mapper.DataMapper;
import com.example.demo.model.Data_Publish;
import com.example.demo.model.User;
import com.example.demo.service.DataPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {

    @Autowired
    DataPublishService dataPublishService;
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
                            HttpServletRequest request,
                            Model model){

        User user = (User)request.getSession().getAttribute("user");
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null ||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if( description== null ||description==""){
            model.addAttribute("error","description不能为空");
            return "publish";
        }

        if(tag == null ||tag==""){
            model.addAttribute("error","tag不能为空");
            return "publish";
        }
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Data_Publish data = new Data_Publish();
        data.setTitle(title);
        data.setDescription(description);
        data.setTag(tag);
        data.setCreator(1);
        data.setGmtCreate(System.currentTimeMillis());
        data.setGmtModified(data.getGmtCreate());
        User user1 = (User)request.getSession().getAttribute("user");
        data.setCreator(user1.getId());
        dataPublishService.createOrUpdata(data);
        return "redirect:/";
    }

}
