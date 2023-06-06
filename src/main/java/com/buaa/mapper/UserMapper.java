package com.buaa.mapper;

import com.buaa.pojo.User;
import org.apache.ibatis.annotations.*;

import static com.buaa.pojo.User.*;

@Mapper
public interface UserMapper {
    public static final String userTable = "user";
    @Insert("insert into user(u_nickname,u_name,email,password,profile_pic) " +
            "values(#{uNickname},#{uName},#{email},#{password},#{profilePic})")
    public void insertUser(User user);

    @Select("select * from user where u_name = #{user_name}")
    public User selectUserByName(String user_name);

    @Select("select count(*) from user where email = #{mail}")
    public int selectEmailCount(String mail);

    @Update("update user set u_nickname = #{uNickname}, profile_pic = #{profilePic}, u_name = #{uName}, email = #{email} , password = #{password}" +
            " where u_id = #{uId}")
    public void updateUserInfo(User newUserInfo);

    @Update("update user set u_nickname = #{uNickname} where u_name = #{uName}")
    public User updateUserNickname(User user);

    @Update("update user set profile_pic = #{profilePic} where u_name = #{uName}")
    public User updateUserProfilePic(User user);

    @Select("select * from user where u_id = #{id}")
    User selectUserById(int id);
}