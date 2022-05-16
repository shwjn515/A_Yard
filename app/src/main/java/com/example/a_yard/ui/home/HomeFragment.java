package com.example.a_yard.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.a_yard.DataBean;
import com.example.a_yard.ImageAdapter;
import com.example.a_yard.Login;
import com.example.a_yard.R;
import com.example.a_yard.databinding.FragmentHomeBinding;
import com.example.a_yard.ui.notifications.Bill;
import com.google.android.material.snackbar.Snackbar;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ImageButton btn_addroom;
    private Banner banner;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    @SuppressWarnings("unchecked")
    public void onStart() {
        super.onStart();
        banner = getActivity().findViewById(R.id.home_banner);
        ImageAdapter imageAdapter = new ImageAdapter(DataBean.getTestData());
        //加载本地图片
//        banner.setAdapter(imageAdapter)
//                .addBannerLifecycleObserver(this)
//                .setIndicator(new CircleIndicator(getContext()))
//                .setOnBannerListener(new OnBannerListener() {
//                    @Override
//                    public void OnBannerClick(Object data, int position) {
//                        Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
//                    }
//                });
        //something
        //DataBean.addImages(url);
        //轮播图加载网络图片
        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
            @Override
            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                Glide.with(holder.imageView)
                        .load(data.imageUrl)
                        .thumbnail(Glide.with(holder.itemView).load(R.drawable.icon_downloading))
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        });
        banner.setIndicator(new CircleIndicator(getContext()));
        //设置轮播时间
        banner.setLoopTime(5000);

        //服务按钮颜色
        Button mbtn_room1=getActivity().findViewById(R.id.btn_room1);
        GradientDrawable myGrad1 = (GradientDrawable)mbtn_room1.getBackground();
        myGrad1.setColor(ContextCompat.getColor(getActivity(),R.color.red));

        Button mbtn_room2=getActivity().findViewById(R.id.btn_room2);
        GradientDrawable myGrad2 = (GradientDrawable)mbtn_room2.getBackground();
        myGrad2.setColor(ContextCompat.getColor(getActivity(),R.color.orange));
        Button mbtn_room3=getActivity().findViewById(R.id.btn_room3);
        GradientDrawable myGrad3 = (GradientDrawable)mbtn_room3.getBackground();
        myGrad3.setColor(ContextCompat.getColor(getActivity(),R.color.yellow));

        Button mbtn_room4=getActivity().findViewById(R.id.btn_room4);
        GradientDrawable myGrad4 = (GradientDrawable)mbtn_room4.getBackground();
        myGrad4.setColor(ContextCompat.getColor(getActivity(),R.color.green));

        Button mbtn_room5=getActivity().findViewById(R.id.btn_room5);
        GradientDrawable myGrad5 = (GradientDrawable)mbtn_room5.getBackground();
        myGrad5.setColor(ContextCompat.getColor(getActivity(),R.color.green2));

        Button mbtn_room6=getActivity().findViewById(R.id.btn_room6);
        GradientDrawable myGrad6 = (GradientDrawable)mbtn_room6.getBackground();
        myGrad6.setColor(ContextCompat.getColor(getActivity(),R.color.blue));

        Button mbtn_room7=getActivity().findViewById(R.id.btn_room7);
        GradientDrawable myGrad7 = (GradientDrawable)mbtn_room7.getBackground();
        myGrad7.setColor(ContextCompat.getColor(getActivity(),R.color.blue2));

        Button mbtn_room8=getActivity().findViewById(R.id.btn_room8);
        GradientDrawable myGrad8 = (GradientDrawable)mbtn_room8.getBackground();
        myGrad8.setColor(ContextCompat.getColor(getActivity(),R.color.purple_200));

        //添加房间按钮监听
        btn_addroom=getActivity().findViewById(R.id.btn_addroom);
        btn_addroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), addroom.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
        //recyclerview初始化
        initData();
        initView();
    }
    //
    private void initData() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new RoomListAdapter(getData());
    }

    private void initView() {
        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        //绘制间隔样式
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("暑假特价房,102,12345678921,2022-04-09,60,已入住,https://img.alicdn.com/bao/uploaded/i2/1749466481/O1CN017xsaIs1xkLcR2RaCo_!!1749466481.jpg_300x300q90.jpg");
        data.add("暑假出游套房,106,,,200,空闲中,https://www.tokyo.grandnikko.com/cn/stay/executive/images/img_aut2.jpg");
        data.add("商务单间,108,12345678922,20220410,170,已预定,https://www.gardenhotels.co.jp/images/kyobashi/room/deluxe-twin/deluxe-twin.jpg");
        data.add("普通标间,109,12345678923,20220410,180,待打扫,https://www.hotelmonterey.co.jp/upload_file/monhim/stay/_D2A9652.JPG");
        return data;
    }
}
