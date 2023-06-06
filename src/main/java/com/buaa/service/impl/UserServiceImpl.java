package com.buaa.service.impl;

import com.buaa.pojo.User;
import com.buaa.mapper.UserMapper;
import com.buaa.service.UserService;
import com.buaa.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Resource
    CodeUtils codeUtils;

    public User insertRegisteredUser(User user){
        userMapper.insertUser(user);
        return user;
    }

    public User findUserByName(String username){
        return userMapper.selectUserByName(username);
    }
    public boolean isExistsEmail(String email){
        if(userMapper.selectEmailCount(email)>0){
            return true;
        }
        else return false;
    }

    public void updateUserInfo(User newUserInfo) {
        userMapper.updateUserInfo(newUserInfo);
    }

    @Override
    public boolean sendCode(User user) {
        return codeUtils.sendCode(user);
    }

    @Override
    public boolean eqToken(String token) {
        boolean flag = codeUtils.eqToken(token);

        if (flag){
            User user = codeUtils.findUser(token);
            insertRegisteredUser(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User selectUserById(int id) {
        return userMapper.selectUserById(id);
    }
}