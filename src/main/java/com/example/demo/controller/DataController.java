package com.example.demo.controller;

import com.example.demo.DTO.DataPublishDTO;
import com.example.demo.mapper.DataMapper;
import com.example.demo.service.DataPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class DataController {
    @Autowired
    private DataPublishService service;

    @GetMapping("/dataList/{id}")
    public String dataList(@PathVariable(name = "id")Integer id,
                           Model model){
        DataPublishDTO dataPublishDTO = service.getById(id);
        service.addView(id);
        model.addAttribute("data",dataPublishDTO);
        return "datalist";
    }
}
