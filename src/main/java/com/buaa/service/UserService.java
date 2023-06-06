package com.buaa.service;

import com.buaa.mapper.UserMapper;
import com.buaa.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    public User insertRegisteredUser(User user);

    public User findUserByName(String username);

    public void updateUserInfo(User newUserInfo);

    // 根据用户注册信息进行注册链接的的生成和发送
    public boolean sendCode(User user);

    // 用户点击注册链接判断token是否过期
    public boolean eqToken(String token);

    User selectUserById(int id);

    public boolean isExistsEmail(String email);
}