package com.example.redis3redo.repository;

import com.example.redis3redo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
    Admin findByUsername(String username);
}
