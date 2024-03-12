package com.ccc.service.impl;

import com.ccc.domain.LoginUser;
import com.ccc.domain.ResponseResult;
import com.ccc.domain.User;
import com.ccc.service.LoginService;
import com.ccc.utils.JwtUtil;
import com.ccc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    //先在SecurityConfig，使用@Bean注解重写官方的authenticationManagerBean类，然后这里才能注入成功
    private AuthenticationManager authenticationManager;

    @Autowired
    //RedisCache是我们在utils目录写好的类
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //用户在登录页面输入的用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //获取AuthenticationManager的authenticate方法来进行用户认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断上面那行的authenticate是否为null，如果是则认证没通过，就抛出异常
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过，就使用userid生成一个jwt，然后把jwt存入ResponseResult后返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString(); // 这里有疑问  这里就是对id就行了加密
        String jwt = JwtUtil.createJWT(userid);

        //把完整的用户信息存入redis，其中userid作为key，注意存入redis的时候加了前缀 login:
        Map<String, String> map = new HashMap<>();
        map.put("token",jwt);
        redisCache.setCacheObject("login:"+userid,loginUser);
        return new ResponseResult(200,"登录成功",map);
    }

    @Override
    // 主要的业务逻辑就是删除redis存储的用户信息，当下次再来访问的时候
    public ResponseResult logout() {
        //获取我们在JwtAuthenticationTokenFilter类写的SecurityContextHolder对象中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取用户id
        Long userid = loginUser.getUser().getId();

        //根据用户id，删除redis中的token值，注意我们的key是被 login: 拼接过的，所以下面写完整key的时候要带上 longin:
        redisCache.deleteObject("login:"+userid);

        return new ResponseResult(200,"注销成功");


    }
}
