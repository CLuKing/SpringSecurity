package com.ccc;

import com.ccc.domain.User;
import com.ccc.mapper.MenuMapper;
import com.ccc.mapper.UserMapper;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testUserMapper(){
        List<User> userMappers = userMapper.selectList(null);
        System.out.println(userMappers);
    }


    @Test
    public void testBCryptPasswordEncoder(){
        //如果不想在下面那行new的话，那么就在该类注入PasswordEncoder，例如如下
        /**
         * @Autowired
         * private PasswordEncoder passwordEncoder;
         */
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 模拟用户输入的密码
        String encode1 = passwordEncoder.encode("1234");
        // 再模拟一次
        String encode2 = passwordEncoder.encode("1234");

        //虽然这两次的密码都是一样的，但是加密后是不一样的。每次运行，对同一原文都会有不同的加密结果
        //原因:会添加随机的盐，加密结果=盐+原文+加密。达到每次加密后的密文都不相同的效果
        System.out.println(encode1);
        System.out.println(encode2);

    }

    @Test
    public void testBCryptPasswordEncoder2(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean result = passwordEncoder.matches("1234", "$2a$10$j74GPpbZXgtCRTHSrTl1ZOfzcnZ/9tEd2pQ.PAnmRXFeJUhw8JHIu");

        // 看结果
        System.out.println(result);
    }

    @Autowired
    private MenuMapper menuMapper;
    @Test
    public void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(2L);
        System.out.println(list);
    }

}
