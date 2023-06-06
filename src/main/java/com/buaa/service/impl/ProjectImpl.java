package com.buaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buaa.mapper.ProjectMapper;
import com.buaa.mapper.utils.CopyProjectUtils;
import com.buaa.pojo.Project;
import com.buaa.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public int createProject(int tid, String name) {
        Project project = new Project();
        project.setTid(tid);
        project.setPName(name);
        projectMapper.insertProject(project);
        project = projectMapper.getProjectByPName(name);
        return project.getPid();
    }

    @Override
    public boolean deleteProjectById(int id) {
        QueryWrapper<Project> qw = new QueryWrapper<>();
        qw.eq("p_id", id);
        return projectMapper.delete(qw) > 0;
    }

    @Override
    public boolean updateStatus(int id, String status) {
        Project project = new Project();
        project.setPid(id);
        project.setStatus(status);
        projectMapper.updateStatus(project);
        return true;
    }

    @Override
    public boolean renameProject(int id, String name) {
        Project project = new Project();
        project.setPid(id);
        project.setPName(name);
        projectMapper.updateProjectName(project);
        return true;
    }

    @Override
    public Project selectById(int id) {
        return projectMapper.getProjectById(id);
    }

    @Override
    public List<Project> selectByTeam(int id) {
        return projectMapper.getProjectByTeam(id);
    }

    @Override
    public Project selectByName(String name) {
        return projectMapper.getProjectByPName(name);
    }

    @Override
    public boolean checkNameRepeat(int tid, String name) {
        Project project = new Project();
        project.setTid(tid);
        project.setPName(name);
        return projectMapper.checkNameRepeat(project) != null;
    }

    @Override
    public List<Project> selectProjectByStatus(int tid, String status) {
        Project project = new Project();
        project.setTid(tid);
        project.setStatus(status);
        return projectMapper.getProjectByStatus(project);
    }

    @Override
    public List<Project> selectProjectLike(int tid, String key) {
        QueryWrapper<Project> qw = new QueryWrapper<>();
        qw.select("*");
        qw.like("p_name", key);
        qw.eq("t_id", tid);
        return projectMapper.selectList(qw);
    }

    @Override
    public int copyProject(int id) {
        CopyProjectUtils utils = new CopyProjectUtils();
        int newPId;

        projectMapper.copyProject(id);
        Project project = projectMapper.getCopyProject(id);
        newPId = project.getPid();

        projectMapper.updateNameForNewProject(newPId);

        utils.setPre(id);
        utils.setCopy(newPId);
        projectMapper.copyDocsForProject(utils);
        return newPId;
    }

    @Override
    public List<Project> selectByTeamModify(int id) {
        return projectMapper.getProjectsByTeamModify(id);
    }

}
