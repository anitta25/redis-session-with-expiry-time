package com.example.redis3redo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter  extends OncePerRequestFilter {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/user/"))
        {
            String sessionid= redisTemplate.opsForValue().get("sessionid");
            if(sessionid==null)
            { response.setStatus(HttpStatus.FORBIDDEN.value());}
             else
                 filterChain.doFilter(request,response);
        }
        else filterChain.doFilter(request,response);
    }
}
