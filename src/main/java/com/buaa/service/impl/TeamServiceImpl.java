package com.buaa.service.impl;

import com.buaa.controller.utils.InviteRequest;
import com.buaa.controller.utils.TwoUserParam;
import com.buaa.pojo.Team;
import com.buaa.pojo.TeamMember;
import com.buaa.pojo.User;
import com.buaa.mapper.TeamMapper;
import com.buaa.mapper.TeamMemberMapper;
import com.buaa.service.TeamService;
import com.buaa.service.UserService;
import com.buaa.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    TeamMemberMapper teamMemberMapper;

    @Resource
    UserService userService;

    @Resource
    private CodeUtils codeUtils;

    public Team selectTeamById(int id){
        return teamMapper.selectTeamByID(id);
    }

    public void createTeam(Team team, User creator){
        teamMapper.insertTeam(team);

//        return teamMemberMapper.selectTeamsByUserId(creator);
    }

    //TODO: invite/ add member
    public void addTeamMember(Team team, User user){
        int tid = team.getTid();
        int uid = user.getUId();
        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setTId(tid);
        newTeamMember.setUId(uid);
        newTeamMember.setUPosition(1);
        teamMemberMapper.insertTeamMember(newTeamMember);
    }


    public void addTeamMember(Team team, User user, int position){
        int tid = team.getTid();
        int uid = user.getUId();
        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setTId(tid);
        newTeamMember.setUId(uid);
        newTeamMember.setUPosition(position);
        teamMemberMapper.insertTeamMember(newTeamMember);
    }

    //TODO: remove/ delete member
    public void removeTeamMember(Team team,User user){
        int tid = team.getTid();
        int uid = user.getUId();
        TeamMember memberToDelete = new TeamMember();
        memberToDelete.setTId(tid);
        memberToDelete.setUId(uid);
        teamMemberMapper.deleteTeamMember(memberToDelete);
    }

    //TODO: select teams user is in
    public Team[] getUserTeams(User user){
        return teamMemberMapper.selectTeamsByUserId(user);
    }
    //TODO: change/ update member position
    public int selectMemberPosition(Team team,User user){
        int tid = team.getTid();
        int uid = user.getUId();
        TeamMember memberToFind = new TeamMember();
        memberToFind.setTId(tid);
        memberToFind.setUId(uid);
        return teamMemberMapper.selectMemberPositionInTeam(memberToFind);
    }
    public void updateMemberPosition(Team team,User user, int position){
        int tid = team.getTid();
        int uid = user.getUId();
        TeamMember memberToChange = new TeamMember();
        memberToChange.setTId(tid);
        memberToChange.setUId(uid);
        memberToChange.setUPosition(position);
        teamMemberMapper.updateMemberPositionInTeam(memberToChange);
    }
    //TODO: select team members
    public User[] getTeamMembers(Team team){
        return teamMemberMapper.selectMembersByTeam(team);
    }

    public boolean isMember(Team team, User user){
        int tid = team.getTid();
        int uid = user.getUId();
        TeamMember member = new TeamMember();
        member.setTId(tid);
        member.setUId(uid);
        if(teamMemberMapper.isMember(member)>0) return true;
        else return false;
    }

    @Override
    public boolean sendInvite(int tid, InviteRequest users) {
        Team team = selectTeamById(tid);
        User invited = userService.findUserByName(users.getInvited());
//        int inviterId = userService.findUserByName(users.getInviter()).getUId();
//        User user = userService.selectUserById(users.getInvited());
//        String inviter = userService.selectUserById(users.getInviter()).getUName();
        return codeUtils.sendInvite(invited, users.getInviter(), team);
    }

    public void deleteTeam(int tid){
        Team teamToDelete = teamMapper.selectTeamByID(tid);
        teamMapper.deleteTeam(teamToDelete);
    }

    public void updateTeamInfo(Team team){
        teamMapper.updateTeamInfo(team);
    }
}