package com.buaa.controller;

import com.buaa.controller.utils.R;
import com.buaa.pojo.Document;
import com.buaa.pojo.Project;
import com.buaa.service.DocumentService;
import com.buaa.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@CrossOrigin
public class DocumentCenterController {
    @Autowired
    ProjectService projectService;
    @Autowired
    DocumentService documentService;

    @GetMapping("/docCenter/{team_id}")
    public R getDocCenter(@PathVariable("team_id") int team_id){
        ArrayList<HashMap<Project, ArrayList<Document>>> docCenterContentList = new ArrayList<>();

        ArrayList<Project> projectList = new ArrayList<>();
        projectList.addAll(projectService.selectByTeam(team_id));
        for (Project project : projectList) {
            HashMap<Project,ArrayList<Document>> docList = new HashMap<>();

            ArrayList<Document> docListByProject = new ArrayList<>();
            docListByProject.addAll(documentService.selectByProject(project.getPid()));

            docList.put(project,docListByProject);
            docCenterContentList.add(docList);
        }

        R r = new R(true,docCenterContentList,"成功查询项目文档中心目录结构");
        return r;
    }
}
