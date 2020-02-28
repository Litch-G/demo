package com.example.demo.controller;


import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.DTO.PaginationDTO;
import com.example.demo.mapper.DataMapper;
import com.example.demo.model.Data_Publish;
import com.example.demo.service.DataPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegisterSign {

    @Autowired
    DataMapper dataMapper;
    @Autowired
    DataPublishService dataPublishService;
    @GetMapping("/")
    public String regist(Model model,@RequestParam(name = "page",defaultValue = "1")Integer page,
                         @RequestParam(name = "size",defaultValue = "2")Integer size){

        PaginationDTO paginationDTOS = dataPublishService.list(page,size);
        model.addAttribute("All_data",paginationDTOS);
        return "index";
    }

}
