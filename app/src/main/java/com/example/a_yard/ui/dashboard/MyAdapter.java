package com.example.a_yard.ui.dashboard;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.a_yard.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private ArrayList<String> mData;
    private static ArrayList<String> data2 = new ArrayList<>();


    public MyAdapter(ArrayList<String> data) {
        this.mData = data;
    }
    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.informationlistlayout, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        String[] arr = mData.get(position).split(",", 3);
        holder.mfn.setText(arr[0]);
        holder.mfi.setText(arr[1]);
        Glide.with(holder.mft)
                .load(arr[2])
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.icon_downloading))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mft);
        //监听事件
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

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mfn;
        TextView mfi;
        ImageView mft;

        public ViewHolder(View itemView) {
            super(itemView);
            mfn = (TextView) itemView.findViewById(R.id.friend_name);
            mfi = (TextView) itemView.findViewById(R.id.friend_information);
            mft = (ImageView) itemView.findViewById(R.id.friend_touxiang);
        }
    }
    private MyAdapter.OnItemClickListener onItemClickListener;


    /**
     * 添加新的Item
     */
    public void addNewItem() {
        if(mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(0, "new Item");
        notifyItemInserted(0);
    }

    /**
     * 删除Item
     */
    public void deleteItem() {
        if(mData == null || mData.isEmpty()) {
            return;
        }
        mData.remove(0);
        notifyItemRemoved(0);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public static void initArray() {
        data2.add("188****2090,请问有烧烤吗？,https://rs1.huanqiucdn.cn/dp/api/files/imageDir/0751f342b104d1f8b7829bbb83cc189d.jpg");
        data2.add("182****9693,今天的晚饭很好吃。,https://pic.52112.com/180606/JPG-180606_71/PWt14sx7UY_small.jpg");
    }
    public static void addData2(String data){
        data2.add(data);
    }
    public static void clearData2(){
        data2.clear();
    }
    public static ArrayList<String> getData() {
        return data2;
    }

}