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
import com.example.a_yard.data.Indent;
import com.example.a_yard.ui.home.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class IndentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private IndentAdapter IAdapter;
    public ArrayList<HashMap<String,String>> indents;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indent);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
            actionBar.setTitle("订单管理");
        }
        initData();
        initView();
        IAdapter.setOnItemClickListener(new IndentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击事件
                Intent intent = new Intent(IndentActivity.this, IndentDetailPage.class);
                String i_id = getData().get(position).split(",")[0];
                intent.putExtra("i_id", i_id);
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
        indents =
                http.async("/indents")
                        .bind(this)
                        .bodyType(OkHttps.JSON)
                        .setBodyPara(sharedPreferences.getLong("id", -1))
                        .post()
                        .getResult()
                        .getBody()
                        .<ArrayList>toBean(ArrayList.class);
        data = new ArrayList<>();
        for(HashMap<String,String> indent : indents) {
            data.add(String.valueOf(indent.get("i_id"))+","
                    +indent.get("i_time")+","
                    +String.valueOf(indent.get("i_money"))+","
                    +sharedPreferences.getString("name","user"));
        }
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        IAdapter = new IndentAdapter(data);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.indent_recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(IAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(IndentActivity.this, LinearLayoutManager.VERTICAL));
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public ArrayList<String> getData() {
        return data;
    }
}