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
import android.widget.Toast;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class ClientDetailPage extends AppCompatActivity {
    private EditText clientname,clientphone,clientaddress,clientid,clientin,clientout,clientroomname;
    private Button modification3,save3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_detail_page);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("顾客信息详情页");
        }
        clientname = findViewById(R.id.clientname);
        clientphone = findViewById(R.id.clientphone);
        clientaddress = findViewById(R.id.clientaddress);
        clientid = findViewById(R.id.clientid);
        clientin = findViewById(R.id.clientin);
        clientout = findViewById(R.id.clientout);
        clientroomname = findViewById(R.id.clientroomname);
        modification3 = findViewById(R.id.modification3);
        save3 = findViewById(R.id.save3);
        HTTP http = HTTP.builder()
                .config(builder -> builder.addInterceptor(chain -> {
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
        String itemPosition3 = intent.getStringExtra("c_id");
        ArrayList<String> arr =
                http.async("/clientDetailPage")
                        .bind(this)
                        .bodyType(OkHttps.JSON)
                        .setBodyPara(itemPosition3)
                        .post()
                        .getResult()
                        .getBody()
                        .toBean(ArrayList.class);
        String[] ret = arr.get(0).split(",", 7);
        clientname.setText(ret[0]);
        clientphone.setText(ret[1]);
        clientaddress.setText(ret[2]);
        clientid.setText(ret[3]);
        clientin.setText(ret[4]);
        clientout.setText(ret[5]);
        clientroomname.setText(ret[6]);
        //修改
        modification3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //保存
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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
}

