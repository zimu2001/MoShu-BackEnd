package com.buaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buaa.pojo.Document;
import com.buaa.pojo.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Mapper
public interface DocumentMapper extends BaseMapper<Document> {

    @Insert("insert into document (d_pid, d_name) values (#{dPid}, #{dName})")
    @Options(useGeneratedKeys = true, keyProperty = "did")
    void insertDocument(Document document);

    @Select("select * from document where d_name = #{dName}")
    Document getDocumentByName(String name);

    @Update("update document set d_content = #{dContent} where d_id = #{did}")
    void updateDocumentContent(Document document);

    @Update("update document set d_name = #{dName} where d_id = #{did}")
    void updateDocumentName(Document document);

    @Select("select * from document where d_pid = #{dPid}")
    List<Document> selectByProject(int pid);

    @Select("select * from document where d_id = #{did}")
    Document selectByDid(int did);

    @Select("select * from document where d_pid = #{dPid} and d_name = #{dName}")
    Document checkNameRepeat(Document document);

}