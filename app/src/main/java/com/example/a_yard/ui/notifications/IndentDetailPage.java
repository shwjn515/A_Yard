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
import com.example.a_yard.data.UserInfo;
import com.example.a_yard.ui.dashboard.MyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class IndentDetailPage extends AppCompatActivity {
    EditText i_id,i_time,i_in,i_out,i_money,a_id,c_name;
    Button modification4,save4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indent_detail_page);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("订单信息详情页");
        }
        i_id=findViewById(R.id.i_id);
        i_time=findViewById(R.id.i_time);
        i_in=findViewById(R.id.i_in);
        i_out=findViewById(R.id.i_out);
        i_money=findViewById(R.id.i_money);
        a_id=findViewById(R.id.a_id);
        c_name=findViewById(R.id.c_name);
        modification4=findViewById(R.id.modification4);
        save4=findViewById(R.id.save4);
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
        String itemPosition2 = intent.getStringExtra("i_id");
        ArrayList<String> arr =
                http.async("/indentDetailPage")
                        .bind(this)
                        .bodyType(OkHttps.JSON)
                        .setBodyPara(itemPosition2)
                        .post()
                        .getResult()
                        .getBody()
                        .toBean(ArrayList.class);
        String[] ret = arr.get(0).split(",", 8);
        i_id.setText(ret[0]);
        i_time.setText(ret[1]);
        i_in.setText(ret[2]);
        i_out.setText(ret[3]);
        i_money.setText(ret[4]);
        a_id.setText(ret[5]+"--"+ret[6]);
        String cname = "";
            for (int i = 0; i < arr.size(); i++) {
                cname+=arr.get(i).substring(arr.get(i).lastIndexOf(",")+1)+" ";
            }
        c_name.setText(cname);
        System.out.println(arr.size());
        //修改
        modification4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanEdit(i_in);
                setCanEdit(i_out);
                setCanEdit(i_money);
                setCanEdit(a_id);
                setCanEdit(c_name);
            }
        });
        //保存
        save4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanEdit2(i_in);
                setCanEdit2(i_out);
                setCanEdit2(i_money);
                setCanEdit2(a_id);
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
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