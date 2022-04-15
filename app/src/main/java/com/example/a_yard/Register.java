package com.example.a_yard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity {
    private Button mBtn_delete;
    private EditText eT_username;
    private EditText eT_password;
    private Button mBtn_yincang;
    private Button mBtn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //设置导航栏主题颜色
        StatusBarUtils.setWindowStatusBarColor(this,R.color.black);



        //清除用户名输入框内容
        mBtn_delete =findViewById(R.id.btn_delete2);
        eT_username =findViewById(R.id.sign_name);
        mBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eT_username.setText("");
            }
        });


        //设置密码显示模式
        mBtn_yincang = findViewById(R.id.btn_yincang2);
        eT_password = findViewById(R.id.sign_pw);
        mBtn_yincang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eT_password.getInputType() == 128) {
                    //如果现在是显示密码模式
                    eT_password.setInputType(129);//设置为隐藏密码
                } else {
                    eT_password.setInputType(128);//设置为显示密码
                }
                eT_password.setSelection(eT_password.getText().length());//设置光标的位置到末尾
            }
        });


        //点击继续按钮监听
        mBtn_register = findViewById(R.id.btn_zhuce2);
        mBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // com.example.a_yard.Register.this.finish();
                Intent intent = new Intent(Register.this,com.example.a_yard.Classify.class);
                startActivity(intent);
                overridePendingTransition(R.xml.actions,0);
                Register.this.finish();
            }
        });
    }
}