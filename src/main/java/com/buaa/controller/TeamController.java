package com.buaa.controller;

import com.buaa.controller.utils.ChangePositionParam;
import com.buaa.controller.utils.InviteRequest;
import com.buaa.controller.utils.R;
import com.buaa.controller.utils.TwoUserParam;
import com.buaa.pojo.*;
import com.buaa.service.TeamService;
import com.buaa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@Api(tags = "团队管理")
@RestController
@CrossOrigin
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    UserService userService;


    @ApiOperation(value = "根据团队id获取团队成员列表")
    @GetMapping("/team/{team_id}/members")
    public R showTeamMembers(@PathVariable("team_id") int team_id){
        Team team = new Team();
        team.setTid(team_id);
        //return
        User[] users = teamService.getTeamMembers(team);
        ArrayList<Map<String,String>> usersList = new ArrayList<>();

        for (User user: users) {
            System.out.println(user);
            TreeMap<String,String> userInfos = new TreeMap<String,String>();
            userInfos.put("uname",user.getUName());
            userInfos.put("uid",String.valueOf(user.getUId()));
            userInfos.put("unickname",user.getUNickname());
            userInfos.put("email",user.getEmail());
            userInfos.put("identity",String.valueOf(teamService.selectMemberPosition(team,user)));
            usersList.add(userInfos);
        }
        R r = new R();
        r.setData(usersList);
        r.setFlag(true);
        return r;
    }


    @ApiOperation(value = "获取用户所在的全部团队列表")
    @GetMapping("/team/{user_name}")
    public R showUserTeam(@PathVariable("user_name") String username){
        User user = userService.findUserByName(username);
        //return
        R r = new R();
        r.setData(teamService.getUserTeams(user));
        r.setFlag(true);
        return r;
    }


    @ApiOperation(value = "创建团队")
    @PostMapping("/team/{user_name}")
    public R createTeam(@PathVariable("user_name") String username, @RequestBody Team team){
        User creator = userService.findUserByName(username);
        teamService.createTeam(team,creator);

        int tid = team.getTid();
        int uid = creator.getUId();
        team.setTid(tid);

        TeamMember teamCreator = new TeamMember();
        teamCreator.setTId(tid);
        teamCreator.setUId(uid);
        teamService.addTeamMember(team,creator,3);

        R r = new R();
        r.setMsg("创建成功");
        r.setData(teamService.getUserTeams(creator));
        r.setFlag(true);
        return r;
    }

    @ApiOperation(value = "删除团队")
    @DeleteMapping("/team/{user_name}")
    public R deleteTeam(@PathVariable("user_name") String username, @RequestBody Team team){
        User deleter = userService.findUserByName(username);
        teamService.deleteTeam(team.getTid());
        if(teamService.selectMemberPosition(team,deleter)!=3){
            R r = new R();
            r.setMsg("不够权限删除团队");
            r.setFlag(false);
            return r;
        }
        R r = new R();
        r.setMsg("删除团队成功");
        r.setFlag(true);
        return r;
    }

    @ApiOperation(value = "修改团队信息")
    @PutMapping("/team/{user_name}")
    public R updateTeam(@PathVariable("user_name") String username, @RequestBody Team team){
        User changer = userService.findUserByName(username);
        teamService.updateTeamInfo(team);
        if(teamService.selectMemberPosition(team,changer)<2){
            R r = new R();
            r.setMsg("不够权限修改团队团队信息");
            r.setFlag(false);
            return r;
        }
        return new R(true,"团队信息修改成功");
    }


    @ApiOperation(value = "邀请用户加入团队")
    @PostMapping("/team/{team_id}/members")
    public R inviteUserToTeam(@PathVariable("team_id") int t_id,@RequestBody InviteRequest twoUsers){
        Team team = teamService.selectTeamById(t_id);
        User inviter = userService.findUserByName(twoUsers.getInviter());
        User invitee = userService.findUserByName(twoUsers.getInvited());
        R r = new R();
        if(!teamService.isMember(team,invitee)){
//            teamService.addTeamMember(team,invitee);
            teamService.sendInvite(team.getTid(),twoUsers);
            r.setMsg("成功邀请");
            r.setFlag(true);
        }
        else{
            r.setMsg("此用户已经在此团队中");
            r.setFlag(false);
        }
        return r;
        //TODO: 发送消息而不是直接邀请
    }


    @ApiOperation(value = "团队移除用户")
    @DeleteMapping("/team/{team_id}/members")
    public R removeUserFromTeam(@PathVariable("team_id") int t_id,@RequestBody TwoUserParam twoUsers){
        Team team = teamService.selectTeamById(t_id);
        User kicker = userService.findUserByName(twoUsers.getuName1());
        int kickerPosition = teamService.selectMemberPosition(team,kicker);
        User userToKick = userService.findUserByName(twoUsers.getuName2());
        int userToKickPosition = teamService.selectMemberPosition(team,userToKick);
        if(kickerPosition >=2 && kickerPosition>userToKickPosition) {
            teamService.removeTeamMember(team, userToKick);
            return new R(true,"成员移除成功");
        }
        else return new R(false, "权限不够移除该用户");
    }

    @ApiOperation(value = "修改团队成员权限")
    @PutMapping("/team/{team_id}/members")
    public R changeUserPosition(@PathVariable("team_id") int t_id,@RequestBody ChangePositionParam p){
        Team team = teamService.selectTeamById(t_id);
        User changer = userService.findUserByName(p.getTwoUserParam().getuName1());
        User toChange = userService.findUserByName(p.getTwoUserParam().getuName2());
        int targetPosition = p.getTargetPosition();
        int changerPosition = teamService.selectMemberPosition(team,changer);
        if(changerPosition>=2 && targetPosition<3) {
            teamService.updateMemberPosition(team, toChange, targetPosition);
            return new R(true,"成员权限修改成功");
        }
        else return new R(false, "权限不够修改该用户权限");
    }

//    @ApiOperation(value = "向成员发送加入团队的邀请")
//    @PostMapping("/invite/{tid}")
//    public R sendInvite(@PathVariable("tid") int tid, @RequestBody InviteRequest users) {
//        teamService.sendInvite(tid, users);
//        return new R(true, "发送成功，请等待对方查看");
//    }
    @ApiOperation(value = "向成员发送加入团队的邀请")
    @PostMapping("/invite/{tid}")
    public R sendInvite(@PathVariable("tid") int tid, @RequestBody InviteRequest users) {
        teamService.sendInvite(tid, users);
        return new R(true, "发送成功，请等待对方查看");
    }

//    @ApiOperation(value = "临时接口，用于邀请加入团队")
//    @GetMapping("/invite/temp/{uid}/{tid}")
//    public R inviteJoin(@PathVariable int uid, @PathVariable int tid) {
//        Team team = teamService.selectTeamById(tid);
//        User invited = userService.selectUserById(uid);
//        teamService.addTeamMember(team, invited);
//        return new R (true, "邀请成功");
//    }
    @ApiOperation(value = "临时接口，用于邀请加入团队")
    @GetMapping("/invite/temp/{user_name}/{tid}")
    public R inviteJoin(@PathVariable("user_name") String username, @PathVariable int tid) {
        Team team = teamService.selectTeamById(tid);
        User invited = userService.findUserByName(username);

        if(!teamService.isMember(team,invited)) {
            teamService.addTeamMember(team, invited);
            return new R (true, "邀请成功");
        }
        else return new R(false,"用户已在团队中");
    }
}

