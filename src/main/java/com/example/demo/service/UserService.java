package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User check(String userName){
        User user = userMapper.findByName(userName);
        if (user !=null){
            return user;
        }
        return null;
    }
}
