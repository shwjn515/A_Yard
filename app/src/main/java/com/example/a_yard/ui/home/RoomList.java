package com.example.a_yard.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.R;
import com.example.a_yard.data.Apartment;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class RoomList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        Intent intent = getIntent();
        Integer a_id = intent.getIntExtra("a_id",-1);
        HTTP http = HTTP.builder()
                .config( builder -> builder.addInterceptor(chain -> {
                    Response res = chain.proceed(chain.request());
                    ResponseBody body = res.body();
                    ResponseBody newBody = null;
                    if (body != null) {
                        newBody = ResponseBody.create(body.contentType(), body.bytes());
                    }
                    return res.newBuilder().body(newBody).build();
                }))
                .baseUrl("http://499270u7q7.51vip.biz")
                .addMsgConvertor(new JacksonMsgConvertor())
                .build();
        HashMap<String, Object> info = http.async("/apartment")
                .bind(this)
                .bodyType(OkHttps.JSON)
                .setBodyPara(String.valueOf(a_id))
                .post()
                .getResult()
                .getBody()
                .toBean(HashMap.class);

    }
}