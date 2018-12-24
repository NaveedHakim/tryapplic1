package com.example.naveed.tryapplic;

public class CreateUser {
    public String name,email,password,code,image,thumb_nail,schoolkey,userId,role;
    public  CreateUser(){

    }
    public CreateUser(String name, String email, String password, String code, String image, String thumb_nail, String schoolkey, String userId, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.image = image;
        this.thumb_nail = thumb_nail;
        this.schoolkey = schoolkey;
        this.userId = userId;
        this.role=role;
    }


}