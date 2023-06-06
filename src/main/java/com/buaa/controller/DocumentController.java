package com.buaa.controller;


import com.buaa.controller.utils.R;
import com.buaa.pojo.Document;
import com.buaa.service.DocumentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documents")
@CrossOrigin
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping
    public R createDocument(@RequestBody Document document) {
        int pid = document.getDPid();
        String name = document.getDName();
        if(documentService.checkNameRepeat(pid, name))
            return new R(false, "项目内已有同名文档，请改名！");
        return new R(true, documentService.createDocument(pid,name),
                "新文档创建成功！");
    }

    @ApiOperation(value = "将对应did的文档删除")
    @DeleteMapping("/{did}")
    public R removeDocument(@PathVariable int did) {
        return new R(documentService.deleteDocumentById(did), null,
                "成功删除文档！");
    }

    @PutMapping("/content")
    public R setDocumentContent(@RequestBody Document document) {
        return new R(documentService.setDocumentContent(document.getDid(), document.getDContent()),
                null, "文档内容已变更");
    }

    @PutMapping
    public R renameDocument(@RequestBody Document document) {
        int pid = document.getDPid();
        String name = document.getDName();
        if(documentService.checkNameRepeat(pid, name))
            return new R(false, "项目内已有同名文档，请改名！");
        return new R(documentService.renameDocument(document.getDid(), document.getDName()),
                null, "文档名称已变更");
    }

    @GetMapping("//project/{pid}")
    public R selectByProject(@PathVariable int pid) {
        List<Document> documents = documentService.selectByProject(pid);
        for (Document document : documents) {
            document.setDPid(pid);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("documents", documents);
        return new R(true, map, "查询成功");
    }

    @GetMapping("/{did}")
    public R selectByDid(@PathVariable int did) {
        Document document = documentService.selectByDid(did);
        return new R(true, document, "查询成功");
    }
//    @PostMapping("/createDocument")
//    public Map<String,Object> createDocument(@RequestBody Map<String,String> map){
//        String content = map.get("documentContent");
//        int tid = Integer.parseInt(map.get("documentTeamId"));
//        Map<String,Object> out_map = new HashMap<>();
//
//        System.out.println("content:" + content);
//        System.out.println("tid:" + tid);
//
//        try{
//            documentService.createDocument( tid, content);
//            out_map.put("error", 0);
//            out_map.put("msg", "文档创建成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            out_map.put("error", 4001);
//            out_map.put("msg", "文档创建失败  ");
//        }
//
//        return out_map;
//    }
//
//    @PostMapping("/deleteDocumentById")
//    public Map<String,Object> deleteDocumentById(@RequestBody Map<String,String> map){
//        int id = Integer.parseInt(map.get("documentId"));
//        Map<String,Object> out_map = new HashMap<>();
//
//        try{
//            documentService.deleteDocumentById(id);
//            out_map.put("error", 0);
//            out_map.put("msg", "文档删除成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            out_map.put("error", 4001);
//            out_map.put("msg", "文档删除失败");
//        }
//
//        return out_map;
//    }
//    @PostMapping("/updateDocumentContent")
//    public Map<String,Object> updateDocumentContent(@RequestBody Map<String,String> map){
//        int id = Integer.parseInt(map.get("documentId"));
//        String content = map.get("documentContent");
//        Map<String,Object> out_map = new HashMap<>();
//
//        try{
//            documentService.updateDocumentContent(id, content);
//            out_map.put("error", 0);
//            out_map.put("msg", "文档更新成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            out_map.put("error", 4001);
//            out_map.put("msg", "文档更新失败");
//        }
//
//        return out_map;
//    }
//
//    @PostMapping("/selectById")
//    public Map<String,Object> selectById(@RequestBody Map<String,String> map){
//        int id = Integer.parseInt(map.get("documentId"));
//        Map<String,Object> out_map = new HashMap<>();
//
//        try{
//            Document document = documentService.selectById(id);
//            out_map.put("error", 0);
//            out_map.put("msg", "文档查询成功");
//            out_map.put("document", document);
//        }catch (Exception e){
//            e.printStackTrace();
//            out_map.put("error", 4001);
//            out_map.put("msg", "文档查询失败");
//        }
//
//        return out_map;
//    }
//
//    @PostMapping("/selectByTeam")
//    public Map<String,Object> selectByTeam(@RequestBody Map<String,String> map){
//        int tid = Integer.parseInt(map.get("documentTeamId"));
//        Map<String,Object> out_map = new HashMap<>();
//
//        try{
//            List<Document> documents = documentService.selectByTeam(tid);
//            out_map.put("error", 0);
//            out_map.put("msg", "文档查询成功");
//            out_map.put("documents", documents);
//        }catch (Exception e){
//            e.printStackTrace();
//            out_map.put("error", 4001);
//            out_map.put("msg", "文档查询失败");
//        }
//
//        return out_map;
//    }

}