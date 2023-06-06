package com.buaa.pojo;

import lombok.Data;

@Data
public class Project {
    private int pid;
    private int tid;
    private String status;
    private String pName;
    private String createTime;
    private String modifyTime;
}
