package com.ccc.controller;

import com.ccc.domain.ResponseResult;
import com.ccc.domain.User;
import com.ccc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    //LoginService是我们在service目录写好的接口
    private LoginService loginService;

    @PostMapping("/user/login")
    //ResponseResult和user是我们在domain目录写好的类
    public ResponseResult login(@RequestBody User user){
        //登录
        return loginService.login(user);
    }

}