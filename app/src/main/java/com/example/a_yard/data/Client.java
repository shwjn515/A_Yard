package com.example.a_yard.data;

public class Client {
    String c_id;
    Long i_id;
    String c_name;
    Long c_phone;
    String c_address;

    public Client() {
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public Long getI_id() {
        return i_id;
    }

    public void setI_id(Long i_id) {
        this.i_id = i_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public Long getC_phone() {
        return c_phone;
    }

    public void setC_phone(Long c_phone) {
        this.c_phone = c_phone;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public Client(String c_id, Long i_id, String c_name, Long c_phone, String c_address) {
        this.c_id = c_id;
        this.i_id = i_id;
        this.c_name = c_name;
        this.c_phone = c_phone;
        this.c_address = c_address;
    }
}
