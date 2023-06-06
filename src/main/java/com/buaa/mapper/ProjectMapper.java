package com.buaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buaa.mapper.utils.CopyProjectUtils;
import com.buaa.pojo.Project;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {


    @Select("select * from project where p_id = #{id}")
    Project getProjectById(int id);

    @Select("select * from project where t_id = #{id}")
    List<Project> getProjectByTeam(int id);

    @Select("select * from project where p_name = #{name}")
    Project getProjectByPName(String name);

    @Select("select * from project where p_name like '%#{pName}%'")
    List<Project> getProjectLike(Project project);

    @Update("update project set p_name = #{pName}, modify_time = now() where p_id = #{pid}")
    void updateProjectName(Project project);

    @Insert("insert into project (t_id, p_name, create_time, modify_time) values (#{tid}, #{pName}, now(), now())")
    void insertProject(Project project);

    @Select("select * from project where t_id = #{tid} and p_name = #{pName}")
    Project checkNameRepeat(Project project);

    @Select("select * from project where t_id = #{tid} and status = #{status}")
    List<Project> getProjectByStatus(Project project);

    @Update("update project set status = #{status}, modify_time = now() where p_id = #{pid}")
    void updateStatus(Project project);

    @Insert("insert into project (t_id, p_name, status, create_time, modify_time) select t_id, p_name, status, now(), now() from project where p_id = #{id};")
    void copyProject(int id);

    @Select("select * from project where p_name in (\n" +
            "    select p_name from project where p_id = #{id}\n" +
            "    )order by p_id desc limit 1;")
    Project getCopyProject(int id);

    @Update("update project set p_name = concat(p_name, '(副本)') where p_id = #{id};")
    void updateNameForNewProject(int id);

    @Insert("insert into document (d_name, d_pid, d_content) select d_name, #{copy}, d_content from document where d_pid = #{pre};")
    void copyDocsForProject(CopyProjectUtils utils);

    @Select("select * from project where t_id = #{id} order by modify_time;")
    List<Project> getProjectsByTeamModify(int id);
}
