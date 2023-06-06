package com.buaa.service;

import com.buaa.controller.utils.InviteRequest;
import com.buaa.controller.utils.TwoUserParam;
import com.buaa.pojo.Team;
import com.buaa.pojo.User;

public interface TeamService {
    public Team selectTeamById(int id);

    public void createTeam(Team team, User creator);

    //TODO: invite/ add member
    public void addTeamMember(Team team, User user);
    public void addTeamMember(Team team, User user, int position);

    //TODO: remove/ delete member
    public void removeTeamMember(Team team,User user);

    //TODO: select teams user is in
    public Team[] getUserTeams(User user);
    //TODO: change/ update member position
    public int selectMemberPosition(Team team, User user);
    public void updateMemberPosition(Team team, User user, int position);
    //TODO: select team members
    public User[] getTeamMembers(Team team);

    public boolean isMember(Team team, User user);

    boolean sendInvite(int tid, InviteRequest users);

    public void deleteTeam(int tid);

    public void updateTeamInfo(Team team);
}