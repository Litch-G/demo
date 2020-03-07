package com.example.demo.controller;

import com.example.demo.DTO.PaginationDTO;
import com.example.demo.model.User;
import com.example.demo.service.DataPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class profileController {
    @Autowired
    DataPublishService dataPublishService;
    @GetMapping("/profile/{action}")
    public String profole(Model model, HttpServletRequest request,
                          @PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "9")Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user ==null){
            return "redirect:/";
        }

        if("publish".equals(action)){
            model.addAttribute("section","publish");
            model.addAttribute("sectionName","我的发布");
            PaginationDTO paginationDTO = dataPublishService.list(1,page,size);
            model.addAttribute("All_data",paginationDTO);
        }
        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","收到的回复");
        }

        return "profile";
    }
}
