package com.buaa.service;

import com.buaa.pojo.Doc;

import java.util.List;

public interface DocService {

    int createDoc(int dfid,int dtid, String name);
    boolean checkNameRepeat(int dtid, String name);
    boolean deleteDocById(int docid);
    boolean renameDoc(int docid,String name);
    boolean updateContent(int docid,String content);
    List<Doc> selectByTeamId(int tid);
    List<Doc> selectByFolderId(int dfid);
    Doc selectById(int docid);
}
