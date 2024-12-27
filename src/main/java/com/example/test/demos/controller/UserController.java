package com.example.test.demos.controller;


import com.example.test.demos.dao.UserRepository;
import com.example.test.demos.pojo.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "用户管理", description = "关于用户的 CRUD 操作")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "创建一个新用户", description = "通过提供用户的姓名和电子邮件来创建一个新用户。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "用户创建成功"),
            @ApiResponse(responseCode = "400", description = "无效的输入")
    })
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @Operation(summary = "获取所有用户", description = "检索数据库中所有用户的列表。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取用户列表"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Operation(summary = "根据ID获取用户", description = "通过用户的ID来检索特定用户的信息。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取用户"),
            @ApiResponse(responseCode = "404", description = "用户未找到")
    })
    @GetMapping("/{id}")
    public Optional<User> getUserById(
            @Parameter(description = "用户的唯一标识符", required = true)
            @PathVariable Long id) {
        return userRepository.findById(id);
    }

    @Operation(summary = "更新用户信息", description = "通过用户的ID更新其姓名和电子邮件。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "用户更新成功"),
            @ApiResponse(responseCode = "404", description = "用户未找到"),
            @ApiResponse(responseCode = "400", description = "无效的输入")
    })
    @PutMapping("/{id}")
    public User updateUser(
            @Parameter(description = "用户的唯一标识符", required = true)
            @PathVariable Long id,
            @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    @Operation(summary = "删除用户", description = "通过用户的ID删除指定用户。")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "用户删除成功"),
            @ApiResponse(responseCode = "404", description = "用户未找到")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(
            @Parameter(description = "用户的唯一标识符", required = true)
            @PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
