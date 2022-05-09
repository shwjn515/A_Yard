package com.example.a_yard.data;

import java.util.HashMap;

public class ImgResponse {
    Integer code;
    String msg;
    HashMap<String,String> data;

    public ImgResponse() {
    }

    public ImgResponse(Integer code, String msg, HashMap<String, String> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
