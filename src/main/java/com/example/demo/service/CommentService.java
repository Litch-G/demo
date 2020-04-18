package com.example.demo.service;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.enums.CommentTypeEnums;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.DataMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Data_Publish;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    DataMapper dataMapper;

    //捆绑事务，错误回滚
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TAGRET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnums.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_ERROR);
        }
        if(comment.getType() == CommentTypeEnums.COMMETN.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复某个信息
            Data_Publish dataPublish = dataMapper.getById(comment.getParentId());
            if (dataPublish == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            dataMapper.commentCount(dataPublish);
        }
    }

    public List<CommentDTO> listByParentId(Integer id) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        List<Comment>comments = commentMapper.selectByParentId(id);
        if (comments.size() == 0){
            return new ArrayList<>();
        }

        Set<Integer> userIdSet = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        for(Comment comment :comments){
            for(Integer i: userIdSet){
                if(comment.getCommentator() == i){
                    CommentDTO commentDTO = new CommentDTO();
                    User byId = userMapper.findById(i);
                    BeanUtils.copyProperties(comment,commentDTO);
                    commentDTO.setUser(byId);
                    commentDTOS.add(commentDTO);
                }
            }
        }
        return commentDTOS;
    }
}
