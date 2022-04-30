package com.example.a_yard.ui.notifications;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.a_yard.R;
import com.example.a_yard.StatusBarUtils;

import java.sql.Date;
import java.sql.Time;
import java.text.MessageFormat;

public class Bill extends AppCompatActivity {
    private Button mbtn_starttime,mbtn_endtime;

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
        mbtn_starttime.setText(MessageFormat.format("{0} 年 {1} 月 01 日", year, lastmonth));
        mbtn_endtime.setText(MessageFormat.format("{0} 年 {1} 月 01 日", year, month));
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
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}