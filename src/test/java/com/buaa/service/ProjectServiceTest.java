package com.buaa.service;

import com.buaa.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PageService pageService;

//    @Test
//    public void testCreate() {
//        pageService.getPageById(1);
//    }
//
//    @Test
//    public void testDelete() {
//        pageService.deletePage(3);
//    }
//
//    @Test
//    public void testName() {
//        pageService.updateName(1, "改名测试1");
//    }
//
//    @Test
//    public void testContent() {
//        pageService.updateContent(1, "改名测试1");
//    }
}
