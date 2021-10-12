package com.example.financialmanagementv2.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理 403 异常界面
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
         // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); //403
        //  设置响应类型及编码
        response.setContentType("application/json;charset=utf-8");
        //  设置响应内容
        PrintWriter out = response.getWriter();
        out.print("{\"code\":\"403\",\"error\":\"访问受限,请与管理员联系\"}");
        String message = accessDeniedException.getMessage();
        System.out.println(message);
        out.flush();
        out.close();
    }
}