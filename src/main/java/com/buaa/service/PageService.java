package com.buaa.service;

import com.alibaba.fastjson.JSONObject;
import com.buaa.pojo.MyPage;

import java.util.List;

public interface PageService {
    void createPage(MyPage page);
    void deletePage(int pgId);
    boolean updateName(int pgId, String name);
    boolean updateContent(int pgId, String content);
    boolean checkNameRepeat(String name, int pgPid);
    MyPage getPageById(int id);
    MyPage getPageByName(String name, int pgPid);

    List<MyPage> getPagesByPId(int id);

}
