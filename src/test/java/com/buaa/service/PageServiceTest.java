package com.buaa.service;

import com.buaa.pojo.MyPage;
import com.buaa.service.PageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class PageServiceTest {
    @Autowired
    private PageService pageService;

    @Test
    private void testGet() {
        MyPage page = pageService.getPageById(1);
    }
}
