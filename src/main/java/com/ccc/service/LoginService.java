package com.ccc.service;

import com.ccc.domain.ResponseResult;
import com.ccc.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    ResponseResult login(User user);
}