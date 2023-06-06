package com.buaa.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buaa.mapper.PageMapper;
import com.buaa.pojo.MyPage;
import com.buaa.service.PageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    @Resource
    PageMapper pageMapper;

    @Override
    public void createPage(MyPage page) {
//        page.updateJsonString();
        pageMapper.createPage(page);
    }

    @Override
    public void deletePage(int pgId) {
        pageMapper.deletePage(pgId);
    }

    @Override
    public boolean updateName(int pgId, String name) {
        MyPage page = new MyPage();
        page.setPgPid(pgId);
        page.setPgName(name);
        pageMapper.renameById(page);
        return true;
    }

    @Override
    public boolean updateContent(int pgId, String content) {
        MyPage page = new MyPage();
        page.setPgPid(pgId);
        page.setPgContent(content);
//        page.updateJsonString();
        pageMapper.updateById(page);
        return true;
    }

    @Override
    public boolean checkNameRepeat(String name, int pgPid) {
        MyPage page = new MyPage();
        page.setPgName(name);
        page.setPgPid(pgPid);
        return pageMapper.selectPageByName(page) != null;
    }

    @Override
    public MyPage getPageById(int id) {
        MyPage page = pageMapper.selectPageById(id);
//        page.parseJson();
        return page;
    }

    @Override
    public MyPage getPageByName(String name, int pgPid) {
        MyPage page = new MyPage();
        page.setPgName(name);
        page.setPgPid(pgPid);
        MyPage ret = pageMapper.selectPageByName(page);
//        ret.parseJson();
        return ret;
    }

    @Override
    public List<MyPage> getPagesByPId(int id) {
        List<MyPage> pages = pageMapper.selectPagesByPId(id);
//        for(MyPage page : pages){
//            page.parseJson();
//        }
        return pages;
    }
}
