package com.buaa.pojo;


import lombok.Data;

@Data
public class Fd {
    private int fid;
    private int ffid;//father 文件夹id
    private int tid;
    private String fName;
}
