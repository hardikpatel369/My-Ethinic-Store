package com.example.myethinicstore.Model;

public class Users {

    private String phone, password, username;

    public Users(){}

    public Users(String phone, String password, String username) {
        this.phone = phone;
        this.password = password;
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
