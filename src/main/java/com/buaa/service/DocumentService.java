package com.buaa.service;

import com.buaa.pojo.Document;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DocumentService {
//    public void createDocument(int tid, String content);
//    void deleteDocumentById(int id);
//    void updateDocumentContent(int id, String content);
//    Document selectById(int id);
//    List<Document> selectByTeam(int tid);
    int createDocument(int pid, String name);
    boolean setDocumentContent(int did, String content);
    boolean renameDocument(int did, String name);
    List<Document> selectByProject(int pid);
    Document selectByDid(int did);
    boolean checkNameRepeat(int pid, String name);
    boolean deleteDocumentById(int did);
}