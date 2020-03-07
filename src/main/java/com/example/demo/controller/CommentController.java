package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.ResultDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
