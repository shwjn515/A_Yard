package com.example.a_yard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Classify extends Activity {
    private Button mBtn_tenant;
    private Button mBtn_owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify);
        //设置导航栏主题颜色
        StatusBarUtils.setWindowStatusBarColor(this, R.color.black);

        //房客选项
        mBtn_tenant =findViewById(R.id.btn_tenant);
        mBtn_tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //房客界面预留
            }
        });
        //房主选项
        mBtn_owner =findViewById(R.id.btn_owner);
        mBtn_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Classify.this, HouseOwner.class);
                startActivity(intent);
                //动画切换效果，淡出淡入
                overridePendingTransition(R.xml.actions,0);
                Classify.this.finish();
            }
        });
    }
}