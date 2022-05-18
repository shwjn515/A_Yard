package com.example.a_yard.data;

import android.content.SharedPreferences;

public class Minshuku {
    String m_name;
    Long m_id;
    Long id;
    String m_address;
    String m_photo;
    String HPOC;
    String m_sur;
    String m_service;

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public Minshuku(String m_name, Long m_id, Long id, String m_address, String m_photo, String HPOC, String m_sur, String m_service) {
        this.m_name = m_name;
        this.m_id = m_id;
        this.id = id;
        this.m_address = m_address;
        this.m_photo = m_photo;
        this.HPOC = HPOC;
        this.m_sur = m_sur;
        this.m_service = m_service;
    }

    public Long getM_id() {
        return m_id;
    }

    public void setM_id(Long m_id) {
        this.m_id = m_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getM_address() {
        return m_address;
    }

    public void setM_address(String m_address) {
        this.m_address = m_address;
    }

    public String getM_photo() {
        return m_photo;
    }

    public void setM_photo(String m_photo) {
        this.m_photo = m_photo;
    }

    public String getHPOC() {
        return HPOC;
    }

    public void setHPOC(String HPOC) {
        this.HPOC = HPOC;
    }

    public String getM_sur() {
        return m_sur;
    }

    public void setM_sur(String m_sur) {
        this.m_sur = m_sur;
    }

    public String getM_service() {
        return m_service;
    }

    public void setM_service(String m_service) {
        this.m_service = m_service;
    }

    public Minshuku() {
    }
    public void savePreference(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("m_name",m_name).commit();
        editor.putLong("m_id",m_id).commit();
        editor.putLong("id",id).commit();
        editor.putString("m_address",m_address).commit();
        editor.putString("m_photo",m_photo).commit();
        editor.putString("HPOC",HPOC).commit();
        editor.putString("m_sur",m_sur).commit();
        editor.putString("m_service",m_service).commit();
    }

    public Minshuku loadPreference(SharedPreferences preferences) {
        this.m_name = preferences.getString("m_name","民宿");
        this.m_id = preferences.getLong("m_id",-1);
        this.id = preferences.getLong("id",-1);
        this.m_address = preferences.getString("m_address","null");
        this.m_photo = preferences.getString("m_phpto","null");
        this.HPOC = preferences.getString("HPOC","null");
        this.m_sur = preferences.getString("m_sur","null");
        this.m_service = preferences.getString("m_service","null");
        return this;
    }
}
