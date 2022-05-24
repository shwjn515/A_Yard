package com.example.a_yard.ui.home;

import android.content.SharedPreferences;
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
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.JacksonMsgConvertor;
import com.ejlchina.okhttps.OkHttps;
import com.example.a_yard.R;
import com.example.a_yard.data.Apartment;
import com.example.a_yard.ui.notifications.IndentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder>{

    public ArrayList<HashMap<String,Object>> rooms;
    private IndentAdapter.OnItemClickListener onItemClickListener;
    public RoomListAdapter(ArrayList<HashMap<String,Object>> data) {
        this.rooms = data;
    }

    public void updateData(ArrayList<HashMap<String,Object>> data) {
        this.rooms = data;
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
    /**
     * 添加新的Item
     */
    public void addNewItem(HashMap apartment) {
        if(rooms == null) {
            rooms = new ArrayList<>();
        }
        rooms.add(apartment);
        notifyItemInserted(rooms.size() - 1);
    }

    /**
     * 删除Item
     */
//    public void deleteItem() {
//        if(rooms == null || rooms.isEmpty()) {
//            return;
//        }
//        rooms.remove(0);
//        notifyItemRemoved(0);
//    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String,Object> room = rooms.get(position);
        //数据绑定
        holder.roomname.setText(String.format("%s—%s", room.get("a_name"), room.get("a_roomname")));
        //holder.indent_id.setText(arr[2]);
        //holder.live_time.setText(arr[3]);
        holder.roomprice.setText(String.format("￥%s", room.get("price")));
        String type = "";
        if (room.get("a_status").equals(Integer.valueOf(0))) {
            type = "空闲中";
        }else if(room.get("a_status").toString().equals("1")) {
            type = "已预定";
        }else if(room.get("a_status").toString().equals("2")) {
            type = "已入住";
        }else if(room.get("a_status").toString().equals("3")) {
            type = "待打扫";
        }
        if(type.equals("空闲中")){
            holder.room_type.setTextColor(Color.rgb(118,238,0));
        }else if(type.equals("已入住") || type.equals("已预定")){
            holder.room_type.setTextColor(Color.rgb(255,48,48));
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
            HashMap<String,String> data = http.async("/apartments_newest_indent")
                    .bind(this)
                    .bodyType(OkHttps.JSON)
                    .setBodyPara(room.get("a_id"))
                    .post()
                    .getResult()
                    .getBody()
                    .toBean(HashMap.class);
            holder.indent_id.setText(data.get("i_id"));
            holder.live_time.setText(data.get("i_time"));
        }else if(type.equals("待打扫")){
            holder.room_type.setTextColor(Color.rgb(0,191,255));
        }
        holder.room_type.setText(type);
        //网络图片资源
        Glide.with(holder.roomimage)
                .load(room.get("a_photo"))
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.icon_downloading))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .into(holder.roomimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }
    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(IndentAdapter.OnItemClickListener listener) {
        this.onItemClickListener = (IndentAdapter.OnItemClickListener) listener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    @Override
    public int getItemCount() {
        return rooms == null ? 0 : rooms.size();
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