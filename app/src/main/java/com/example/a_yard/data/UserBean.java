package com.example.a_yard.data;

public class UserBean {
    Long phone;
    String password;

    public UserBean(Long phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public UserBean() {
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
