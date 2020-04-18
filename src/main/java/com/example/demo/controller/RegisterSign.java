package com.example.demo.controller;


import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.DTO.PaginationDTO;
import com.example.demo.mapper.DataMapper;
import com.example.demo.model.Data_Publish;
import com.example.demo.model.User;
import com.example.demo.service.DataPublishService;
import com.example.demo.service.UserService;
import com.example.demo.time.RequestLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class RegisterSign {

    @Autowired
    UserService userService;
    @Autowired
    DataMapper dataMapper;
    @Autowired
    DataPublishService dataPublishService;

    @GetMapping("/")
    @RequestLimit(count = 10)
    public String regist(Model model,@RequestParam(name = "page",defaultValue = "1")Integer page,
                         @RequestParam(name = "size",defaultValue = "5")Integer size,
                         @RequestParam(name = "search",defaultValue = "")String search,HttpServletRequest request){

        PaginationDTO paginationDTOS = dataPublishService.list(search,page,size);
        model.addAttribute("All_data",paginationDTOS);
        return "index";
    }
    @GetMapping("/login")

    public String login(HttpServletResponse response, HttpServletRequest request){
      //  User user = userService.check(request.getParameter("username"));
       // if (user !=null){
        Cookie cookie = new Cookie("token","13579");
        response.addCookie(cookie);
      //  }
        return "redirect:/";
    }
}
