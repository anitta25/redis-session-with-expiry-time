package com.example.redis3redo.service;

import com.example.redis3redo.DTO.LoginRequestDTO;
import com.example.redis3redo.DTO.SignupRequestDTO;
import com.example.redis3redo.entity.Admin;
import com.example.redis3redo.repository.AdminRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

  @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AdminRepo adminRepo;
    public  Admin signup(SignupRequestDTO signupRequestDTO) {
        Optional<Admin> admin_fetched=Optional.ofNullable(adminRepo.findByUsername(signupRequestDTO.getUsername()));
        if (admin_fetched.isPresent())
        {

            return  null;
        }
        else
        {  Admin admin=new Admin();
            admin.setUsername(signupRequestDTO.getUsername());
            admin.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
            //it is better not to use model mapper cz password has to be encoded
            adminRepo.save(admin);
            return  admin;

        }

    }

    public Admin login(LoginRequestDTO loginRequestDTO) {
        String username=loginRequestDTO.getUsername();
        Optional<Admin> admin= Optional.ofNullable(adminRepo.findByUsername(username));
        if(! admin.isPresent())
        {
             return null;
        }
        else
        {
            if(passwordEncoder.matches(loginRequestDTO.getPassword(),admin.get().getPassword()))
                return  admin.get();
            else
                return  null;

        }

    }


    public String getUUId() {
        UUID uuid =UUID.randomUUID();
        return  uuid.toString();
    }
}
