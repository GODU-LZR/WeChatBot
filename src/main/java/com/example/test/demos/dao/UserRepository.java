package com.example.test.demos.dao;

import com.example.test.demos.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 自定义查询方法（如果需要）
}
