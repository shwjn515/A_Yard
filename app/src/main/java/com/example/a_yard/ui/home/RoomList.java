package com.example.a_yard.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;
import com.example.a_yard.data.Apartment;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class RoomList extends AppCompatActivity {
    private ImageButton roomphoto;
    private EditText roomname,roomid,roomtupe,roomlivein,roomprice,roombed,roomservice,roomaddress,roomtip;
    private Button modification5,save5,delete5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("房间信息详情页");
        }
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
        roomphoto=findViewById(R.id.roomphoto);
        roomname=findViewById(R.id.roomname);
        roomid=findViewById(R.id.roomid);
        roomtupe=findViewById(R.id.roomtype);
        roomlivein=findViewById(R.id.roomlivein);
        roomprice=findViewById(R.id.roomprice);
        roombed=findViewById(R.id.roombed);
        roomservice=findViewById(R.id.roomservice);
        roomaddress=findViewById(R.id.roomaddress);
        roomtip=findViewById(R.id.roomtip);
        modification5=findViewById(R.id.modification5);
        save5=findViewById(R.id.save5);
        delete5=findViewById(R.id.delete5);

        roomname.setText(String.valueOf(info.get("a_name")));
        roomid.setText(String.valueOf(info.get("a_roomname")));
        roomtupe.setText(String.valueOf(info.get("a_type")));
        roomlivein.setText(String.valueOf(info.get("a_live")));
        roomprice.setText(String.valueOf(info.get("price")));
        roombed.setText(String.valueOf(info.get("b_type")));
        roomservice.setText(String.valueOf(info.get("facility")));
        roomtip.setText(String.valueOf(info.get("a_notes")));
        roomaddress.setText("天津市北辰区双口镇5340号");
        Glide.with(roomphoto)
                .load(String.valueOf(info.get("a_photo")))
                .thumbnail(Glide.with(roomphoto).load(R.drawable.icon_downloading))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(roomphoto);
        modification5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanEdit(roomname);
                setCanEdit(roomid);
                setCanEdit(roomtupe);
                setCanEdit(roomlivein);
                setCanEdit(roomprice);
                setCanEdit(roombed);
                setCanEdit(roomservice);
                setCanEdit(roomtip);
                setCanEdit(roomaddress);
            }
        });
        //保存
        save5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanEdit2(roomname);
                setCanEdit2(roomid);
                setCanEdit2(roomtupe);
                setCanEdit2(roomlivein);
                setCanEdit2(roomprice);
                setCanEdit2(roombed);
                setCanEdit2(roomservice);
                setCanEdit2(roomtip);
                setCanEdit2(roomaddress);
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