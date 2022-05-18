package com.example.a_yard.ui.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;
import com.example.a_yard.ui.home.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class ClientActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ClientAdapter IAdapter;
    public ArrayList<HashMap<String,String>> clients;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("顾客管理");
        }
        initData();
        getData();
        initView();
        IAdapter.setOnItemClickListener(new ClientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击事件
                Intent intent = new Intent(ClientActivity.this, ClientDetailPage.class);
                String c_id = clients.get(position).get("c_id");
                intent.putExtra("c_id", c_id);
                System.out.println(c_id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //长按

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
    ArrayList<String> data = new ArrayList<>();
    private void initData() {
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
        clients =
                http.async("/clients")
                        .bind(this)
                        .bodyType(OkHttps.JSON)
                        .setBodyPara(sharedPreferences.getLong("id", -1))
                        .post()
                        .getResult()
                        .getBody()
                        .<ArrayList>toBean(ArrayList.class);
        data = new ArrayList<>();
        for(HashMap<String,String> client : clients) {
            data.add(String.valueOf(client.get("c_name"))+","
                    +String.valueOf(client.get("c_phone")));
        }
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        IAdapter = new ClientAdapter(getData());
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.client_recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(IAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(ClientActivity.this, LinearLayoutManager.VERTICAL));
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private ArrayList<String> getData() {
        return data;
    }
}