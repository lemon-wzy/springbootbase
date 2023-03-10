package com.lichi.springbootbase.auth.components;

import com.alibaba.fastjson2.JSON;
import com.lichi.springbootbase.response.ApiResponse;
import com.lichi.springbootbase.response.enums.ApiResponseStatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 鉴权失败401组件
 * @author: lychee
 * @date: 2022/12/19 16:11
 * @version: 1.0
 * @since: 2022/12/19
 */
public class AuthorFailedComponent implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ApiResponseStatusEnum.UNAUTHORIZED.getCode());
        apiResponse.setMessage(ApiResponseStatusEnum.UNAUTHORIZED.getMessage());
        apiResponse.setData(null);
        response.getWriter().println(JSON.toJSONString(apiResponse));
    }
}
