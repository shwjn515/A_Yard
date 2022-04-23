package com.example.a_yard.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class MsgActivity extends AppCompatActivity {

    private ListView listView;
    private MsgAdapter msgAdapter;
    private List<Msg> msgList = new ArrayList<Msg>();
    private EditText input;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);

        //读取数据
        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra("position",0);
        String data[] = MyAdapter.getData().get(itemPosition).split(",",3);
        //顶部导航栏修改
        StatusBarUtils.setWindowStatusBarColor(this, R.color.blue);
        ActionBar actionBar = getSupportActionBar();  // 获取ActionBar
        if (actionBar != null) {
            actionBar.setTitle(data[0]);  // 设置ActionBar的标题
            actionBar.setDisplayHomeAsUpEnabled(true);  // 设置返回按钮
        }
        Msg.setUrl(data[2]);
        //聊天
        listView = (ListView) findViewById(R.id.msg_list_view);
        initMsg();
        msgAdapter  = new MsgAdapter(this, R.layout.listviewlayout, msgList);
        listView.setAdapter(msgAdapter);
        input = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = input.getText().toString();
                if(!"".equals(message)) {
                    Msg msg = new Msg(message, Msg.MSG_SEND);
                    msgList.add(msg);
                    msgAdapter.notifyDataSetChanged();//当有新消息时刷新
                    listView.setSelection(msgList.size());
                }else {
                    Toast.makeText(MsgActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                }
                input.setText("");
            }
        });
    }
    private void initMsg() {
        Msg msg;
        msg = new Msg("您好", Msg.MSG_RECEIVE);
        msgList.add(msg);
        msg = new Msg("您好", Msg.MSG_SEND);
        msgList.add(msg);
        msg = new Msg("请问有烧烤吗？", Msg.MSG_RECEIVE);
        msgList.add(msg);
    }
    // 监听返回按钮，如果点击返回按钮则关闭当前Activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}