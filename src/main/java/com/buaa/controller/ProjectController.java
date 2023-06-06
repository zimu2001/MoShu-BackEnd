package com.buaa.controller;

import com.buaa.controller.utils.R;
import com.buaa.mapper.DocumentMapper;
import com.buaa.pojo.Document;
import com.buaa.pojo.Project;
import com.buaa.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "项目管理")
@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DocumentMapper documentMapper;

    /**
     * 新建项目
     * @param "组号tid, 项目名pName"
     * @return "新项目id"
     */
    @ApiOperation(value = "创建项目")
    @PostMapping
    public R createProject(@RequestBody Project project) {
        int tid = project.getTid();
        String name = project.getPName();
        if(projectService.checkNameRepeat(tid, name))
            return new R(false, "组内已有同名项目，请改名！");
        int newId = projectService.createProject(tid,name);
        Document document = new Document();
        document.setDName("UML");
        document.setDPid(newId);
        documentMapper.insertDocument(document);
        return new R(true, newId,
                "新项目创建成功！");
    }

    @ApiOperation(value = "将对应pid的项目设为已完成")
    @PutMapping("/finish/{pid}")
    public R finishProject(@PathVariable int pid) {
        return new R(projectService.updateStatus(pid, "finish"), null,
                "将项目设置为已完成");
    }

    @ApiOperation(value = "将对应pid的项目设为正在进行")
    @PutMapping("/doing/{pid}")
    public R doingProject(@PathVariable int pid) {
        return new R(projectService.updateStatus(pid, "doing"), null,
                "将项目设置为进行中");
    }

    /**
     * 移入回收站
     */
    @ApiOperation(value = "将对应pid的项目移入回收站")
    @DeleteMapping("/{pid}")
    public R removeProject(@PathVariable int pid) {
        return new R(projectService.updateStatus(pid, "trash"), null,
                "成功移入回收站！");
    }

    /**
     * 从回收站移除
     */
    @ApiOperation(value = "将对应pid的彻底删除")
    @DeleteMapping("/!/{pid}")
    public R deleteProject(@PathVariable int pid) {
        return new R(projectService.deleteProjectById(pid), null,
                "从回收站移除成功！");
    }

    /**
     * 项目更名
     * @param “pid, pName”
     */
    @ApiOperation(value = "改变项目名称")
    @PutMapping
    public R renameProject(@RequestBody Project project) {
        return new R(projectService.renameProject(project.getPid(), project.getPName()),
                null, "项目名称已变更");
    }

    /**
     * 根据组号索引项目
     * @param tid
     */

    @ApiOperation(value = "查询tid对应团队的项目，按创建时间排序")
    @GetMapping("/team/{tid}")
    public R selectByTeam(@PathVariable int tid) {
        R r = new R();
        r.setFlag(true);
        r.setData(projectService.selectByTeam(tid));
        r.setMsg("查询成功！");
        return r;
    }

    /**
     * 根据项目号索引项目
     * @param pid
     */
    @ApiOperation(value = "查询对应pid的项目")
    @GetMapping("/{pid}")
    public R selectById(@PathVariable int pid) {
        R r = new R();
        r.setFlag(true);
        r.setData(projectService.selectById(pid));
        r.setMsg("查询成功！");
        return r;
    }


    @ApiOperation(value = "查询对应tid团队的回收站中的项目")
    @GetMapping("/trash/{tid}")
    public R selectTrash(@PathVariable int tid) {
        return new R(true, projectService.selectProjectByStatus(
                tid, "trash"), "查询成功");
    }

    @ApiOperation(value = "查询对应tid团队已完成的项目")
    @GetMapping("/finish/{tid}")
    public R selectFinish(@PathVariable int tid) {
        return new R(true, projectService.selectProjectByStatus(
                tid, "finish"), "查询成功");
    }

    @ApiOperation(value = "查询对应tid团队正在进行中的项目")
    @GetMapping("/doing/{tid}")
    public R selectDoing(@PathVariable int tid) {
        return new R(true, projectService.selectProjectByStatus(
                tid, "doing"), "查询成功");
    }

    @ApiOperation(value = "根据关键字查询对应tid团队的项目")
    @GetMapping("/like/{tid}/{key}")
    public R selectLike(@PathVariable String key, @PathVariable int tid) {
        return new R(true, projectService.selectProjectLike(tid, key),
                "搜索成功");
    }

    @ApiOperation(value = "复制指定pid对应的项目")
    @GetMapping("/copy/{pid}")
    public R copyProject(@PathVariable int pid) {
        return new R(true, projectService.copyProject(pid), "项目复制成功");
    }

    @ApiOperation(value = "查询tid对应团队的项目，按最近一次修改时间排序")
    @GetMapping("/modify/{tid}")
    public R selectByTeamModify(@PathVariable int tid) {
        return new R(true, projectService.selectByTeamModify(tid), "查询成功");
    }
}
