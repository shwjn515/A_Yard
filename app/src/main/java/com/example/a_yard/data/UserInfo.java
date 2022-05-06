package com.example.a_yard.data;

public class UserInfo {
    String name;
    String password;
    String u_id;
    Long phone;
    String u_address;
    String u_name;
    String u_photo;
    int u_class;
    Long id;

    public UserInfo(String name, String password, String u_id, Long phone, String u_address, String u_name, String u_photo, int u_class, Long id) {
        this.name = name;
        this.password = password;
        this.u_id = u_id;
        this.phone = phone;
        this.u_address = u_address;
        this.u_name = u_name;
        this.u_photo = u_photo;
        this.u_class = u_class;
        this.id = id;
    }

    public UserInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_photo() {
        return u_photo;
    }

    public void setU_photo(String u_photo) {
        this.u_photo = u_photo;
    }

    public int getU_class() {
        return u_class;
    }

    public void setU_class(int u_class) {
        this.u_class = u_class;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
