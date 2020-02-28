package com.example.demo.controller;

import com.example.demo.mapper.DataMapper;
import com.example.demo.model.Data_Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @Autowired
    DataMapper dataMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(name = "title") String title,
                            @RequestParam(name = "description") String description,
                            @RequestParam(name = "tag") String tag,
                            HttpServletRequest request,
                            Model model){

        /*
        *
        *
        * 用request把之后登录的用户信息获取过来
        * Cookie[] cookies = request.getCookies();
        * for(Cookie cookie : cookies){
        * if(cookie.getName().equals("token")){
        * String token = cookie.getValue();
        * User user = findByToken(token);
        * if(user!=null){
        * request.getSession.setAttribute("user",user);
        * break;
        * }
        * }
        *
        * if(user==null){
        * model.addAttribute("error":"用户未登录");
        * }
        *
        * 登录里面用
        * response.addCookie(new Cookie(name:"token",token));
        * 设置该有的token（识别信息）;
        *
        *  */

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

        Data_Publish data = new Data_Publish();
        data.setTitle(title);
        data.setDescription(description);
        data.setTag(tag);
        data.setCreator(1);
        data.setGmtCreate(System.currentTimeMillis());
        data.setGmtModified(data.getGmtCreate());
        dataMapper.create(data);
        return "redirect:/";
    }

}
