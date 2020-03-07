package com.example.demo.service;

import com.example.demo.enums.CommentTypeEnums;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.DataMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Data_Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

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
}
