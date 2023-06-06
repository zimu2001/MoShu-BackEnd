package com.buaa.pojo;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Data
@Repository
public class User implements Serializable {

    private int uId;
    private String uNickname;
    private String uName;
    private String email;
    private String password;
    private String profilePic;
}