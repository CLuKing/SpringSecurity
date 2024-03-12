package com.ccc.config;

import com.ccc.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// 实现Security提供的WebSecurityConfigurerAdapter,对密码校验规则进行修改
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    //把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验
    //注意也可以注入PasswordEncoder，效果是一样的，因为PasswordEncoder是BCry..的父类
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //---------------------------认证过滤器的实现----------------------------------

    @Autowired
    //注入我们在filter目录写好的类
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //由于是前后端分离项目，所以要关闭csrf
                .csrf().disable()
                //由于是前后端分离项目，所以session是失效的，我们就不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //指定让spring security放行登录接口的规则
                .authorizeRequests()
                // 对于登录接口 anonymous表示允许匿名访问
                .antMatchers("/user/login").anonymous()  // 这里是重点，放行端口
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //---------------------------认证过滤器的实现----------------------------------

        //把token校验过滤器添加到过滤器链中
        //第一个参数是上面注入的我们在filter目录写好的类，第二个参数表示你想添加到哪个过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
