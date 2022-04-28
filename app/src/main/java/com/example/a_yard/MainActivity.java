package com.example.a_yard;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a_yard.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ImageButton btn_lightmenu;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;//抽屉

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.blue);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //设置顶部菜单栏
        String menuTitle = "一个院子";
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.title_layout);//设置标题样式
            TextView textView = (TextView) actionBar.getCustomView().findViewById(R.id.display_title);//获取标题布局的textview
            textView.setText(menuTitle);//设置标题名称，menuTitle为String字符串
            actionBar.setHomeButtonEnabled(false);//设置左上角的图标是否可以点击
            actionBar.setDisplayHomeAsUpEnabled(false);//给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayShowCustomEnabled(true);// 使自定义的普通View能在title栏显示，即actionBar.setCustomView能起作用
        }
        navigationView=findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawer_layout);
        btn_lightmenu=findViewById(R.id.btn_lightmenubutton);
        btn_lightmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    //关闭抽屉
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }

            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            //滑动时触发，会多次调用
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            //抽屉滑出显示时触发
            @Override
            public void onDrawerOpened(View drawerView) {
                btn_lightmenu.setImageResource(R.drawable.back);
            }
            //抽屉滑入隐藏时触发
            @Override
            public void onDrawerClosed(View drawerView) {
                btn_lightmenu.setImageResource(R.drawable.lightmenu);
            }
            //状态改变时触发，也就是当显示变为隐藏，或反过来时触发
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
//        //给navigation抽屉对象添加项目选择监视器，根据点击的项目不同，所显示的消息不同
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.nav_2:
//                        Toast.makeText(headview.getContext(),"2",Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_4:
//                        Toast.makeText(headview.getContext(),"4",Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_5:
//                        Toast.makeText(headview.getContext(),"5",Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_6:
//                        Toast.makeText(headview.getContext(),"6",Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return false;
//            }
//        });
    }
}