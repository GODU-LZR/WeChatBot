package com.example.test.demos.servicer;

import com.example.test.demos.pojo.Student;

import java.util.ArrayList;

public interface StudentServicer {
    String insertStudent(Student student);

    String deleteStudent(String userName);

    String updateStudent(Student student);

    ArrayList<Student> selectStudents();


}
