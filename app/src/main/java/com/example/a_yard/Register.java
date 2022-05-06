package com.example.a_yard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.data.UserBean;
import com.example.a_yard.data.UserInfo;

import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class Register extends Activity {
    private Button mBtn_delete;
    private EditText eT_username,person,address,phone,eT_password;
    private Button mBtn_yincang;
    private Button mBtn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //设置导航栏主题颜色
        StatusBarUtils.setWindowStatusBarColor(this,R.color.black);
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


        //清除用户名输入框内容
        mBtn_delete =findViewById(R.id.btn_delete2);
        eT_username =findViewById(R.id.sign_name);
        person=findViewById(R.id.person);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
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
                UserBean userBean = new UserBean(Long.valueOf(phone.getText().toString()),eT_password.getText().toString());
                HashMap<String,String> ret =
                        http.async("/login")
                                .bind(this)
                                .bodyType(OkHttps.JSON)
                                .setBodyPara(userBean)
                                .post()
                                .getResult()
                                .getBody()
                                .toBean(new HashMap<String,String>().getClass());
                if(!ret.get("result").equals("not found")) {
                    Toast.makeText(getApplicationContext(),"该手机号已注册",Toast.LENGTH_SHORT).show();
                    return;
                }
                UserInfo userInfo = new UserInfo("user",
                        eT_password.getText().toString(),
                        person.getText().toString(),
                        Long.valueOf(phone.getText().toString()),
                        address.getText().toString(),
                        eT_username.getText().toString(),
                        "default",
                        -1,
                        Long.valueOf(-1)
                        );
                HashMap<String,String> signRet =
                        http.async("/signup")
                                .bind(this)
                                .bodyType(OkHttps.JSON)
                                .setBodyPara(userInfo)
                                .post()
                                .getResult()
                                .getBody()
                                .toBean(new HashMap<String,String>().getClass());
                if(!signRet.get("result").equals("success")){
                    Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Register.this,com.example.a_yard.Classify.class);
                intent.putExtra("phone",userInfo.getPhone());
                startActivity(intent);
                overridePendingTransition(R.xml.actions,0);
                Register.this.finish();
            }
        });
    }
}