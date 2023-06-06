package com.buaa.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.buaa.pojo.Team;
import com.buaa.pojo.User;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CodeUtils {
    @Resource
    JavaMailSender mailSender;

    @Resource
    RedisTemplate<String, User> redisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    // 生成链接,并给接收的邮箱发送邮件
    public boolean sendCode(User user){
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            // 生成UUID
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token, user);
            redisTemplate.expire(token, 300, TimeUnit.SECONDS);
            //发送方的邮箱地址
            messageHelper.setFrom(from);
            // 接收方的邮箱地址
            messageHelper.setTo(user.getEmail());
            // 邮箱标题
            messageHelper.setSubject("InkBook注册");

            TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
            Template template = engine.getTemplate("EmailTemplate.html");
            String html =template.render(Dict.create().set("href", "http://101.42.246.11/skip?url=" + token));

            messageHelper.setText(html, true); // 邮箱内容
            System.out.println("邮件内容设置成功");
            System.out.println(message);
            mailSender.send(message);  // 发送邮箱
            System.out.println("发送成功");
            return true;
        }catch (Exception e){
            System.out.println("发送失败");
            e.printStackTrace();
            return false;
        }
    }

    // 判断token是否过期
    public boolean eqToken(String token){
        return redisTemplate.hasKey(token);
    }

    // 根据token查询用户的信息
    public User findUser(String token){
        return redisTemplate.opsForValue().get(token);
    }

    public boolean sendInvite(User user, String inviter, Team team) {
        System.out.println(user);
        System.out.println(inviter);
        System.out.println(team);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            //发送方的邮箱地址
            messageHelper.setFrom(from);
            // 接收方的邮箱地址
            messageHelper.setTo(user.getEmail());
            // 邮箱标题
            messageHelper.setSubject("InkBook团队邀请");

            TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
            Template template = engine.getTemplate("InviteTemplate.html");
            Map<String, Object> model = Maps.newHashMap();
            model.put("username", user.getUNickname());
            model.put("inviter", inviter);
            model.put("team", team.getTname());
//            model.put("href", "http://101.42.246.11/invite/temp/" + user.getUId() + "/" + team.getTid());
            model.put("href", "http://101.42.246.11/skipInvite?uname=" + user.getUName() + "&t_id=" + team.getTid());
            StringWriter out = new StringWriter();
            template.render(model, out);

            String html = out.toString();

            messageHelper.setText(html, true); // 邮箱内容
//            System.out.println(message);
            mailSender.send(message);  // 发送邮箱
            System.out.println("发送成功");
            return true;
        }catch (Exception e){
            System.out.println("发送失败");
            e.printStackTrace();
            return false;
        }
    }
}
