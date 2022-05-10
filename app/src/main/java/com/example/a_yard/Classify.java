package com.example.a_yard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;

import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;

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
                boolean success =  updateClass(0);
                Intent intent = new Intent(Classify.this, ClientMainActivity.class);
                startActivity(intent);
                Classify.this.finish();
            }
        });
        //房主选项
        mBtn_owner =findViewById(R.id.btn_owner);
        mBtn_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = updateClass(1);
                Intent intent = new Intent(Classify.this, HouseOwner.class);
                startActivity(intent);
                //动画切换效果，淡出淡入
                overridePendingTransition(R.xml.actions,0);
                Classify.this.finish();
            }
        });
    }
    boolean updateClass(int classInfo) {
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
        Intent intent = getIntent();
        String update = String.valueOf(intent.getExtras().get("phone")) + "," + String.valueOf(classInfo);
        HashMap<String,String> updateRet =
                http.async("/update-class")
                        .bind(this)
                        .bodyType(OkHttps.JSON)
                        .setBodyPara(update)
                        .post()
                        .getResult()
                        .getBody()
                        .toBean(HashMap.class);
        return  updateRet.get("result").equals("success");
    }
}