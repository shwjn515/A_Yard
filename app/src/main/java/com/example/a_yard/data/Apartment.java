package com.example.a_yard.data;

public class Apartment {
    Long a_id;
    String a_name;
    String a_type;
    Integer a_live;
    String b_type;
    String facility;
    Float price;
    String a_photo;
    String a_notes;
    Long m_id;
    Integer a_status;
    Integer a_roomname;

    public Long getA_id() {
        return a_id;
    }

    public void setA_id(Long a_id) {
        this.a_id = a_id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_type() {
        return a_type;
    }

    public void setA_type(String a_type) {
        this.a_type = a_type;
    }

    public Integer getA_live() {
        return a_live;
    }

    public void setA_live(Integer a_live) {
        this.a_live = a_live;
    }

    public String getB_type() {
        return b_type;
    }

    public void setB_type(String b_type) {
        this.b_type = b_type;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getA_photo() {
        return a_photo;
    }

    public void setA_photo(String a_photo) {
        this.a_photo = a_photo;
    }

    public String getA_notes() {
        return a_notes;
    }

    public void setA_notes(String a_notes) {
        this.a_notes = a_notes;
    }

    public Long getM_id() {
        return m_id;
    }

    public void setM_id(Long m_id) {
        this.m_id = m_id;
    }

    public Integer getA_status() {
        return a_status;
    }

    public void setA_status(Integer a_status) {
        this.a_status = a_status;
    }

    public Integer getA_roomname() {
        return a_roomname;
    }

    public void setA_roomname(Integer a_roomname) {
        this.a_roomname = a_roomname;
    }

    public Apartment() {
    }

    public Apartment(Long a_id, String a_name, String a_type, Integer a_live, String b_type, String facility, Float price, String a_photo, String a_notes, Long m_id, Integer a_status, Integer a_roomname) {
        this.a_id = a_id;
        this.a_name = a_name;
        this.a_type = a_type;
        this.a_live = a_live;
        this.b_type = b_type;
        this.facility = facility;
        this.price = price;
        this.a_photo = a_photo;
        this.a_notes = a_notes;
        this.m_id = m_id;
        this.a_status = a_status;
        this.a_roomname = a_roomname;
    }
}
