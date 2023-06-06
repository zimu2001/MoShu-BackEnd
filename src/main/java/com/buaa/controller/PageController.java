package com.buaa.controller;

import com.buaa.controller.utils.R;
import com.buaa.pojo.MyPage;
import com.buaa.service.PageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "设计原型页面管理")
@RestController
@RequestMapping("/pages")
@CrossOrigin
public class PageController {
    @Resource
    private PageService pageService;

    @ApiOperation(value = "创建页面，需指定所在项目id，页面名称，页面json内容，返回值为新页面的id")
    @PutMapping
    public R createPage(@RequestBody MyPage page) {
        if(pageService.checkNameRepeat(page.getPgName(), page.getPgPid()))
            return new R(false, "项目内已有同名页面，请重试！");
        pageService.createPage(page);
        return new R(true, pageService.getPageByName(page.getPgName(), page.getPgPid()).getPgId(),
                "创建成功");
    }

    @ApiOperation(value = "删除对应id的页面")
    @DeleteMapping ("/{id}")
    public R deletePage(@PathVariable int id) {
        pageService.deletePage(id);
        return new R(true, "删除成功！");
    }

    @ApiOperation(value = "页面重命名，需指定页面id，新名称")
    @PostMapping("/rename")
    public R renamePage(@RequestBody MyPage page) {
        pageService.updateName(page.getPgId(), page.getPgName());
        return new R(true, "改名成功！");
    }

    @ApiOperation(value = "修改页面内容，需指定页面id，新内容")
    @PostMapping("/content")
    public R updatePage(@RequestBody MyPage page) {
        pageService.updateContent(page.getPgId(), page.getPgContent());
        return new R(true, "页面修改成功！");
    }

    @ApiOperation(value = "查找对应id的页面")
    @GetMapping("/{id}")
    public R getById(@PathVariable int id) {
        return new R(true, pageService.getPageById(id), "查询成功");
    }

    @ApiOperation(value = "查找对应项目内对应名称的页面")
    @GetMapping("/name/{pid}/{name}")
    public R getByName(@PathVariable int pid, @PathVariable String name) {
        return new R(true, pageService.getPageByName(name, pid), "查询成功");
    }

    @ApiOperation(value = "查找对应id项目的所有页面")
    @GetMapping("/project/{id}")
    public R getByPId(@PathVariable int id) {
        return new R(true, pageService.getPagesByPId(id), "查询成功");
    }
}
