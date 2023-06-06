package com.buaa.service;

import com.buaa.pojo.Project;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProjectService {
    int createProject(int tid, String name);
    boolean deleteProjectById(int id);
    public boolean updateStatus(int id, String status);
    boolean renameProject(int pid, String name);
    Project selectById(int id);
    List<Project> selectByTeam(int id);
    Project selectByName(String name);
    boolean checkNameRepeat(int tid, String name);
    List<Project> selectProjectByStatus(int tid, String status);
    List<Project> selectProjectLike(int tid, String key);
    int copyProject(int id);
    List<Project> selectByTeamModify(int id);
}
