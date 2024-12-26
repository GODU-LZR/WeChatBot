package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息实体类")
public class Student {
    @ApiModelProperty("用户账号")
    private String userName;
    @ApiModelProperty("用户密码")
    private String passWord;

    public Student() {
    }

    public Student(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    /**
     * 获取
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取
     * @return passWord
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 设置
     * @param passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String toString() {
        return "User{userName = " + userName + ", passWord = " + passWord + "}";
    }
}
