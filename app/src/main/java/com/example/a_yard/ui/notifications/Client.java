package com.example.a_yard.ui.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;
import com.example.a_yard.ui.dashboard.MyAdapter;
import com.example.a_yard.ui.home.MyDividerItemDecoration;

import java.util.ArrayList;

public class Client extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ClientAdapter IAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
        }
        initData();
        initView();
        IAdapter.setOnItemClickListener(new ClientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //点击事件
                Toast.makeText(Client.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
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
    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        IAdapter = new ClientAdapter(getData());
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.client_recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(IAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(Client.this, LinearLayoutManager.VERTICAL));
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("张三,15699998293");
        data.add("李四,12345678989");
        data.add("王五,15699999631");
        return data;
    }
}