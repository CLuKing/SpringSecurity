package com.ccc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
//    @PreAuthorize("hasAuthority('system:test:list')") // 授权
    //自定义权限校验的方法，huanfHasAuthority
    @PreAuthorize("@EX.HasAuthority('system:dept:list')")
    public String hello() {
        return "欢迎，开始你新的学习旅程吧";
    }

    @RequestMapping("/testAutho")
    @PreAuthorize("hasAuthority('system:list')") // 授权
    public String testAutho() {
        return "欢迎，开始你新的学习旅程吧";
    }
}

