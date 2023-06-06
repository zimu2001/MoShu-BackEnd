package com.buaa.mapper;

import com.buaa.pojo.Team;
import com.buaa.pojo.TeamMember;
import com.buaa.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TeamMemberMapper {

//    @Insert("insert into team_member(t_id,u_id,u_position) values(#{tid},#{uId},#{position})")
//    public void insertTeamMember(User user,@Param("tid") int tid,@Param("position") int position);
    @Insert("insert into team_member(t_id,u_id,u_position) values(#{tId},#{uId},#{uPosition})")
    public void insertTeamMember(TeamMember teamMember);

    @Delete("delete from team_member where t_id = #{tId} and u_id = #{uId}")
    public void deleteTeamMember(TeamMember teamMember);

    @Select("select * from user where u_id in (select u_id from team_member where t_id = #{tid} )")
    public User[] selectMembersByTeam(Team team);

    @Select("select * from team where t_id in (select t_id from team_member where u_id=#{uId})")
    public Team[] selectTeamsByUserId(User user);

    @Update("update team_member set u_position=#{uPosition} where t_id=#{tId} and u_id=#{uId}")
    public void updateMemberPositionInTeam(TeamMember teamMember);

    @Select("select u_position from team_member where t_id=#{tId} and u_id=#{uId}")
    public int selectMemberPositionInTeam(TeamMember teamMember);

    @Select("select COUNT(*) from team_member where t_id=#{tId} and u_id=#{uId}")
    public int isMember(TeamMember teamMember);
}