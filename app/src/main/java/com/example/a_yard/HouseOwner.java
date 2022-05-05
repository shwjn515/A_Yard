package com.example.a_yard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class HouseOwner extends Activity{
    private EditText minshuku_name,minshuku_address,minshuku_surroundings;
    private ImageButton minshuku_property;
    private Button btn_hoseowner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_owner);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.black);
        minshuku_property=findViewById(R.id.minshuku_property);
        minshuku_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseOwner.this, Photo.class);
                startActivity(intent);
                //动画切换效果，淡出淡入
                overridePendingTransition(R.xml.actions,0);
            }
        });
        btn_hoseowner = findViewById(R.id.btn_hoseowner);
        btn_hoseowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseOwner.this, Login.class);
                startActivity(intent);
                //动画切换效果，淡出淡入
                overridePendingTransition(R.xml.actions,0);
                HouseOwner.this.finish();
            }
        });
    }
    protected void onStart() {
        super.onStart();
        minshuku_property.setImageURI(Photo.mCameraUri);
    }
}