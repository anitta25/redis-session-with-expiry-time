package com.example.redis3redo.controller;

import com.example.redis3redo.DTO.LoginRequestDTO;
import com.example.redis3redo.DTO.SignupRequestDTO;
import com.example.redis3redo.entity.Admin;
import com.example.redis3redo.service.AdminService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private  final RedisTemplate<String,String> redisTemplate;
    private final AdminService adminService;

    public AdminController(RedisTemplate<String, String> redisTemplate, AdminService adminService) {
        this.redisTemplate = redisTemplate;
        this.adminService = adminService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?>  signup(@RequestBody SignupRequestDTO signupRequestDTO)
    {
        Admin admin= adminService.signup(signupRequestDTO);
        if(admin==null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username  already exists");
        else {
            Map<String, Object> response = new HashMap<>();
            response.put("message","successful creation");
            response.put("data",admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        Admin admin=adminService.login(loginRequestDTO);
        if (admin==null)
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login fail");
        else { redisTemplate.opsForValue().set("sessionid",adminService.getUUId(),60, TimeUnit.SECONDS);
            return ResponseEntity.status(HttpStatus.OK).body("login success");
        }
    }

}
