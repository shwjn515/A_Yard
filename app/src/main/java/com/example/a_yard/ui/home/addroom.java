package com.example.a_yard.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;

public class addroom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addroom);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("添加新房间");
        }
    }
}