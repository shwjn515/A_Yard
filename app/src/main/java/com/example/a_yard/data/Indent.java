package com.example.a_yard.data;

import java.sql.Date;
import java.sql.Timestamp;

public class Indent {
    Long i_id;
    String i_time;
    Date i_in;
    Date i_out;
    Long a_id;
    Float i_money;
    Long id;

    public Indent(Long i_id, String i_time, Date i_in, Date i_out, Long a_id, Float i_money, Long id, Integer i_status) {
        this.i_id = i_id;
        this.i_time = i_time;
        this.i_in = i_in;
        this.i_out = i_out;
        this.a_id = a_id;
        this.i_money = i_money;
        this.id = id;
        this.i_status = i_status;
    }

    public void setI_time(String i_time) {
        this.i_time = i_time;
    }

    Integer i_status;


    public Indent() {
    }

    public Long getI_id() {
        return i_id;
    }

    public void setI_id(Long i_id) {
        this.i_id = i_id;
    }


    public Date getI_in() {
        return i_in;
    }

    public void setI_in(Date i_in) {
        this.i_in = i_in;
    }

    public Date getI_out() {
        return i_out;
    }

    public void setI_out(Date i_out) {
        this.i_out = i_out;
    }

    public Long getA_id() {
        return a_id;
    }

    public void setA_id(Long a_id) {
        this.a_id = a_id;
    }

    public Float getI_money() {
        return i_money;
    }

    public void setI_money(Float i_money) {
        this.i_money = i_money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getI_status() {
        return i_status;
    }

    public void setI_status(Integer i_status) {
        this.i_status = i_status;
    }
}