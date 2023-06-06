package com.buaa.mapper;

import com.buaa.pojo.Team;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamMapper {

    @Select("select * from team")
    public Team[] selectAllTeam();

    @Select("select * from team where t_id = #{tid}")
    public Team selectTeamByID(int id);

    @Insert("insert into team(t_name) values(#{tname})")
    @Options(useGeneratedKeys = true, keyProperty = "tid")
    public void insertTeam(Team team);

    @Delete("delete * from team where t_id = #{tid}")
    public void deleteTeam(Team team);

    @Update("update team set t_name = #{tname}, t_brief = #{tbrief}")
    public void updateTeamInfo(Team team);

    @Update("update team set t_name = #{tname}")
    public void updateTeamName(Team team);

    @Update("update team set t_brief = #{tbrief}")
    public void updateTeamBrief(Team team);

}