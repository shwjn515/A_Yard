package com.example.a_yard.data;

public class Minshuku {
    Long m_id;
    Long id;
    String m_address;
    String m_photo;
    String HPOC;
    String m_sur;
    String m_service;

    public Minshuku(Long m_id, Long id, String m_address, String m_photo, String HPOC, String m_sur, String m_service) {
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
}
