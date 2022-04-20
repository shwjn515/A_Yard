package com.example.a_yard.ui.home;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.a_yard.R;

import java.util.ArrayList;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder>{

    private ArrayList<String> mData;

    public RoomListAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 按逗号分隔
        String[] arr = mData.get(position).split(",", 7);
        //数据绑定
        holder.roomname.setText(String.format("%s—%s", arr[0], arr[1]));
        holder.indent_id.setText(arr[2]);
        holder.live_time.setText(arr[3]);
        holder.roomprice.setText(String.format("￥%s", arr[4]));
        if(arr[5].equals("空闲中") || arr[5].equals("已预定")){
            holder.room_type.setTextColor(Color.rgb(118,238,0));
        }else if(arr[5].equals("已入住")){
            holder.room_type.setTextColor(Color.rgb(255,48,48));
        }else if(arr[5].equals("待打扫")){
            holder.room_type.setTextColor(Color.rgb(0,191,255));
        }
        holder.room_type.setText(arr[5]);
        //网络图片资源
        Glide.with(holder.roomimage)
                .load(arr[6])
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.icon_downloading))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.roomimage);
    }
    @Override
    public int getItemCount() {
        return mData == null ? 1 : mData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomname;
        TextView indent_id;
        TextView live_time;
        TextView roomprice;
        TextView room_type;
        ImageView roomimage;
        public ViewHolder(View itemView) {
            super(itemView);
            roomname = (TextView) itemView.findViewById(R.id.roomname);
            indent_id = (TextView) itemView.findViewById(R.id.indent_id);
            live_time = (TextView) itemView.findViewById(R.id.live_time);
            roomprice = (TextView) itemView.findViewById(R.id.roomprice);
            room_type = (TextView) itemView.findViewById(R.id.room_type);
            roomimage =(ImageView) itemView.findViewById(R.id.roomimage);
        }
    }
}