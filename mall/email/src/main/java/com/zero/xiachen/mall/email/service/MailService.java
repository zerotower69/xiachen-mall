package com.zero.xiachen.mall.email.service;

import javax.servlet.http.HttpServletResponse;

public interface MailService {
    void checkEmail(String email, HttpServletResponse response);
}