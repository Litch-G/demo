package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.spark.StringExu;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class ChartController {
    @PostMapping("/chartInfo")
    public JSONObject chartDraw(){
        Map<String,Integer> map = StringExu.exu("123");
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        return  jsonObject;
    }
}
