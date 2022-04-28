package com.example.a_yard.ui.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a_yard.Login;
import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;

public class Person extends AppCompatActivity {
    private Button mbtn_modification,mbtn_save;
    private EditText med_userName,med_userPwd,med_userphone,med_usertrueid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.black);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
        }
        mbtn_modification = findViewById(R.id.modification);
        mbtn_save= findViewById(R.id.save);
        med_userName= findViewById(R.id.userName);
        med_userPwd= findViewById(R.id.userPwd);
        med_userphone= findViewById(R.id.userphone);
        med_usertrueid= findViewById(R.id.usertrueid);
        //修改
        mbtn_modification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanEdit(med_userName);
                setCanEdit(med_userPwd);
                setCanEdit(med_userphone);
                setCanEdit(med_usertrueid);
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