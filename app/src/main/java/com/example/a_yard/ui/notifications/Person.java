package com.example.a_yard.ui.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.Login;
import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;
import com.example.a_yard.data.UserInfo;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class Person extends AppCompatActivity {
    private Button mbtn_modification,mbtn_save;
    private EditText med_userName,med_userPwd,med_userphone,med_usertrueid,med_address,med_usertruename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("个人信息管理");
        }
        mbtn_modification = findViewById(R.id.modification);
        mbtn_save= findViewById(R.id.save);
        med_userName= findViewById(R.id.userName);
        med_userPwd= findViewById(R.id.userPwd);
        med_userphone= findViewById(R.id.userphone);
        med_usertrueid= findViewById(R.id.usertrueid);
        med_address = findViewById(R.id.useraddress);
        med_usertruename=findViewById(R.id.userturename);
        SharedPreferences sharedPreferences =
                getSharedPreferences("userinfo",MODE_PRIVATE);
        med_userName.setText(sharedPreferences.getString("name","user"));
        med_userPwd.setText(sharedPreferences.getString("password","user"));
        med_usertruename.setText(sharedPreferences.getString("u_name","user"));
        med_userphone.setText(String.valueOf(sharedPreferences.getLong("phone",1880000000)));
        med_usertrueid.setText(sharedPreferences.getString("u_id",""));
        med_address.setText(sharedPreferences.getString("u_address",""));

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
        //修改
        mbtn_modification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanEdit(med_userName);
                setCanEdit(med_userPwd);
                setCanEdit(med_userphone);
                setCanEdit(med_usertrueid);
                setCanEdit(med_usertruename);
                setCanEdit(med_address);
            }
        });
        //保存
        mbtn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanEdit2(med_userName);
                setCanEdit2(med_userPwd);
                setCanEdit2(med_userphone);
                setCanEdit2(med_usertrueid);
                setCanEdit2(med_usertruename);
                setCanEdit2(med_address);
                sharedPreferences.edit().putString("name",med_userName.getText().toString()).commit();
                sharedPreferences.edit().putString("password",med_userPwd.getText().toString()).commit();
                sharedPreferences.edit().putLong("phone",Long.valueOf(med_userphone.getText().toString())).commit();
                sharedPreferences.edit().putString("u_name",med_usertruename.getText().toString()).commit();
                sharedPreferences.edit().putString("u_id",med_usertrueid.getText().toString()).commit();
                sharedPreferences.edit().putString("u_address",med_address.getText().toString()).commit();
                Map sp = sharedPreferences.getAll();
                UserInfo userInfo = new UserInfo().loadPreference(sharedPreferences);
                HashMap<String,String> signRet =
                        http.async("/update")
                                .bind(this)
                                .bodyType(OkHttps.JSON)
                                .setBodyPara(userInfo)
                                .post()
                                .getResult()
                                .getBody()
                                .toBean(new HashMap<String,String>().getClass());
                if(!signRet.get("result").equals("success")){
                    Toast.makeText(getApplicationContext(),"修改失败",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    public void setCanEdit2(EditText etContent) {
        etContent.setFocusable(false);
        etContent.setFocusableInTouchMode(false);
        etContent.setTextColor(ContextCompat.getColor(this,R.color.grey));
    }
    public void setCanEdit(EditText etContent) {
        etContent.setFocusable(true);
        etContent.setFocusableInTouchMode(true);
        etContent.setTextColor(ContextCompat.getColor(this,R.color.black));
    }
    //返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}