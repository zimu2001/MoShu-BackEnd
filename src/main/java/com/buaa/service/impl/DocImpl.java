package com.buaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buaa.pojo.Doc;
import com.buaa.pojo.Document;
import com.buaa.service.DocService;
import com.buaa.mapper.DocMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DocImpl implements DocService {


    @Autowired
    private DocMapper docMapper;

    @Override
    public int createDoc(int dfid, int dtid,String name) {
        Doc doc = new Doc();
        doc.setDFid(dfid);
        doc.setDName(name);
        doc.setDTid(dtid);
        docMapper.insertDoc(doc);
        doc = docMapper.selectByName(doc);
        return doc.getDocid();
    }

    @Override
    public boolean checkNameRepeat(int dtid, String name) {
        Doc doc = new Doc();
        doc.setDName(name);
        doc.setDTid(dtid);
        return docMapper.selectByName(doc) != null;
    }

    @Override
    public Doc selectById(int docid){
        return docMapper.selectById(docid);
    }
    @Override
    public boolean deleteDocById(int docid){
        QueryWrapper<Doc> qw = new QueryWrapper<>();
        qw.eq("doc_id",docid);
        return docMapper.delete(qw) > 0;
    }

    @Override
    public boolean renameDoc(int docid,String name){
        Doc doc = new Doc();
        doc.setDocid(docid);
        doc.setDName(name);
        docMapper.renameDoc(doc);
        return true;
    }

    @Override
    public boolean updateContent(int docid,String content){
        Doc doc = new Doc();
        doc.setDocid(docid);
        doc.setDContent(content);
        docMapper.updateContent(doc);
        return true;
    }

    @Override
    public List<Doc> selectByTeamId(int tid){
        return docMapper.selectByTeamId(tid);
    }

    @Override
    public List<Doc> selectByFolderId(int dfid){
        return docMapper.selectByFloderId(dfid);
    }
}
