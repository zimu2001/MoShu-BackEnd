package com.buaa.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class MyPage {
    private int pgId;
    private String pgName;
    private int pgPid;
    private String pgContent;
//    private String jsonString;

//    public void updateJsonString() {
//        jsonString = pgContent.toJSONString();
//    }
//    public void parseJson() {
//        pgContent = JSON.parseObject(jsonString);
//    }
}
