package com.example.test.demos.servicer.imp;

import com.example.test.demos.mappers.StudentMapper;
import com.example.test.demos.pojo.Student;
import com.example.test.demos.servicer.StudentServicer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;


@Service
public class StudentServicerImp implements StudentServicer {



    @Autowired(required = false)
    private StudentMapper studentMapper;

    @Override
    public String insertStudent(Student student) {
        studentMapper.insert(student);
        return "插入成功";
    }

    @Override
    public String deleteStudent(String userName) {
        studentMapper.delete(userName);
        return "删除成功";
    }

    @Override
    public String updateStudent(Student student) {
        studentMapper.update(student);
        return "更新成功";
    }

    @Override
    public ArrayList<Student> selectStudents() {
        return studentMapper.select();
    }




}
