package com.buaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buaa.pojo.MyPage;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface PageMapper extends BaseMapper {
    @Insert("insert into page (pg_name, pg_pid, pg_content) values (#{pgName}, #{pgPid}, #{pgContent});")
    void createPage(MyPage page);

    @Select("select * from page where pg_name = #{pgName} and pg_pid = #{pgPid};")
    MyPage selectPageByName(MyPage page);

    @Select("select * from page where pg_id = #{id};")
    MyPage selectPageById(int id);

    @Select("select * from page where pg_pid = #{id};")
    List<MyPage> selectPagesByPId(int id);

    @Delete("delete from page where pg_id = #{id};")
    void deletePage(int id);

    @Update("update page set pg_name = #{pgName} where pg_id = #{pgPid}")
    void renameById(MyPage page);

    @Update("update page set pg_content = #{pgContent} where pg_id = #{pgPid}")
    void updateById(MyPage page);
}
