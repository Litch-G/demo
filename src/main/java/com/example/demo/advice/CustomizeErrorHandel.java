package com.example.demo.advice;

import com.alibaba.fastjson.JSON;
import com.example.demo.DTO.ResultDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeErrorHandel {
        @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response){

       // HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO = null;
            //返回JSON
            if(ex instanceof CustomizeException){
                resultDTO = (ResultDTO) ResultDTO.errorOf((CustomizeException) ex);
            }
            else {
                resultDTO = (ResultDTO) ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            //错误页面跳转
            if(ex instanceof CustomizeException){
                model.addAttribute("note",ex.getMessage());
            }else{
                model.addAttribute("note","服务器崩坏，请返回主页");
            }
            return new ModelAndView("error");
        }

    }
//    private HttpStatus getStatus(HttpServletRequest request){
//        Integer statisCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if(statisCode == null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statisCode);
//    }

}
