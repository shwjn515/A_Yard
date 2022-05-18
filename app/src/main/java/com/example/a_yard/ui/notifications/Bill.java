package com.example.a_yard.ui.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.MessageFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.Login;
import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;
import com.example.a_yard.data.UserInfo;


import okhttp3.Response;
import okhttp3.ResponseBody;

public class Bill extends AppCompatActivity {
    public static Button mbtn_starttime,mbtn_endtime,Confirmthequery;
    private TextView totalmoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("账单管理");
        }
        //获取当前系统时间
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        int lastmonth =month-1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mbtn_starttime=findViewById(R.id.start_time);
        mbtn_endtime=findViewById(R.id.end_time);
        Confirmthequery=findViewById(R.id.Confirmthequery);
        totalmoney=findViewById(R.id.totalmoney);
        mbtn_starttime.setText(String.format("%d-%02d-01",year, lastmonth));
        mbtn_endtime.setText(String.format("%d-%02d-01",year, month));
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
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo",MODE_PRIVATE);
        String query = String.valueOf(sharedPreferences.getLong("id", -1))+","+mbtn_starttime.getText().toString()+","+mbtn_endtime.getText().toString();
        String total =
                http.async("/bill")
                        .bind(this)
                        .bodyType(OkHttps.JSON)
                        .setBodyPara(query)
                        .post()
                        .getResult()
                        .getBody()
                        .toBean(String.class);
        if(total.equals(null)){
            totalmoney.setText("0.0元");
        }
        totalmoney.setText(total+"元");
        Confirmthequery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query2 = String.valueOf(sharedPreferences.getLong("id", -1))+","+mbtn_starttime.getText().toString()+","+mbtn_endtime.getText().toString();
                String total =
                        http.async("/bill")
                                .bind(this)
                                .bodyType(OkHttps.JSON)
                                .setBodyPara(query2)
                                .post()
                                .getResult()
                                .getBody()
                                .toBean(String.class);
                totalmoney.setText(total+"元");
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
    public void showTimePickerDialog(View view) {
        Button btn = (Button) view;
        DialogFragment newFragment = new DatePickerFragment().setBtn(btn);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
