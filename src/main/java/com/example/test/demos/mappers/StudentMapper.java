package com.example.test.demos.mappers;

import com.example.test.demos.pojo.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;

public interface StudentMapper {

    @Insert("INSERT INTO student values(#{userName},#{passWord})")
    void insert(Student student);

    @Delete("DELETE FROM student WHERE userName=#{userName}")
    void delete(String userName);

    @Update("UPDATE student SET passWord=#{passWord} where userName=#{userName}")
    void update(Student student);

    @Select("SELECT * FROM student")
    ArrayList<Student> select();

    @Select("SELECT * FROM student WHERE userName={userName}")
    Student selectByUserName(String userName);

}
