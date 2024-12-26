package com.example.test.demos.controller;

import com.example.test.demos.pojo.Student;

import com.example.test.demos.servicer.StudentServicer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/student") 
public class StduentController {

    @Autowired(required = false)
    private StudentServicer studentServicer;


    @ApiOperation(value="注册用户信息")
    @PostMapping
    public String insertStudnet(@RequestBody Student student)
    {
        if (null == student) return "插入失败";
        else return studentServicer.insertStudent(student);
    }

    @ApiOperation(value="删除用户信息")
    @DeleteMapping
    public String deleteStudent(String userName)
    {
        return studentServicer.deleteStudent(userName);
    }

    @ApiOperation("更新用户信息")
    @PutMapping
    public String updateStudent(@RequestBody Student student)
    {
        return studentServicer.updateStudent(student);
    }

    @ApiOperation("获取所有用户")
    @GetMapping
    public ArrayList<Student> selectStudents()
    {
        return studentServicer.selectStudents();
    }




}
