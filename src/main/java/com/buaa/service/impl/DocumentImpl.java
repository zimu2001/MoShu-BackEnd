package com.buaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buaa.mapper.DocumentMapper;
import com.buaa.pojo.Document;
import com.buaa.service.DocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentImpl implements DocumentService {
    @Autowired
    private DocumentMapper documentMapper;

    @Override
    public int createDocument(int pid, String name) {
        Document document = new Document();
        document.setDPid(pid);
        document.setDName(name);
        documentMapper.insertDocument(document);
//        document = documentMapper.getDocumentByName(name);
        return document.getDid();
    }

    @Override
    public boolean deleteDocumentById(int did) {
        QueryWrapper<Document> qw = new QueryWrapper<>();
        qw.eq("d_id", did);
        return documentMapper.delete(qw) > 0;
    }


    @Override
    public boolean setDocumentContent(int did, String content) {
        Document document = new Document();
        document.setDid(did);
        document.setDContent(content);
        documentMapper.updateDocumentContent(document);
        return true;
    }

    @Override
    public boolean renameDocument(int did, String name) {
        Document document = new Document();
        document.setDid(did);
        document.setDName(name);
        documentMapper.updateDocumentName(document);
        return true;
    }

    @Override
    public List<Document> selectByProject(int pid) {
        return documentMapper.selectByProject(pid);


    }

    @Override
    public boolean checkNameRepeat(int pid, String name) {
        Document document = new Document();
        document.setDPid(pid);
        document.setDName(name);
        return documentMapper.checkNameRepeat(document) != null;
    }

    @Override
    public Document selectByDid(int did) {
        return documentMapper.selectByDid(did);
    }
//
//    @Override
//    public void deleteDocumentById(int id) {
//        documentMapper.deleteDocumentById(id);
//    }
//
//    @Override
//    public void updateDocumentContent(int id, String content) {
//        documentMapper.updateDocumentContent(id, content);
//    }
//
//    @Override
//    public Document selectById(int id) {
//        return documentMapper.getDocumentById(id);
//    }
//
//    @Override
//    public List<Document> selectByTeam(int tid) {
//        return documentMapper.getDocumentByTeamId(tid);
//    }
}