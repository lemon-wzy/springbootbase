package com.lichi.springbootbase.auth.components;

import com.alibaba.fastjson2.JSON;
import com.lichi.springbootbase.response.ApiResponse;
import com.lichi.springbootbase.response.enums.ApiResponseStatusEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


import java.io.IOException;

/**
 * @description: 权限不足组件
 * @author: lychee
 * @date: 2022/12/19 16:16
 * @version: 1.0
 * @since: 2022/12/19
 */

public class AuthorAccessDeniedHandlerComponent implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ApiResponseStatusEnum.FORBIDDEN.getCode());
        apiResponse.setMessage(ApiResponseStatusEnum.FORBIDDEN.getMessage());
        apiResponse.setData(null);
        response.getWriter().println(JSON.toJSONString(apiResponse));
//        response.getWriter().flush();
    }
}
