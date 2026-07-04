package com.oilplatform.common.interceptor;

import com.oilplatform.common.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"未登录或Token已过期\"}");
            return false;
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            if (jwtUtils.isTokenExpired(token)) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"Token已过期\"}");
                return false;
            }

            Long userId = jwtUtils.getUserId(token);
            Integer roleId = jwtUtils.getRoleId(token);

            request.setAttribute("userId", userId);
            request.setAttribute("roleId", roleId);

            return true;
        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Token无效\"}");
            return false;
        }
    }
}