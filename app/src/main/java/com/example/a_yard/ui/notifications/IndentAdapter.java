package com.example.a_yard.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a_yard.R;
import com.example.a_yard.ui.dashboard.MyAdapter;

import java.util.ArrayList;

public class IndentAdapter extends RecyclerView.Adapter<IndentAdapter.ViewHolder>{

    private ArrayList<String> mData;
    private MyAdapter.OnItemClickListener onItemClickListener;
    public IndentAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.indentlist, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 按逗号分隔
        String[] arr = mData.get(position).split(",", 4);
        // 绑定数据
        holder.indent2_id.setText(arr[0]);
        holder.indent2_time.setText(arr[1]);
        holder.indent2_price.setText(arr[2]);
        holder.indent2_person.setText(arr[3]);
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
    public void setOnItemClickListener(MyAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView indent2_id;
        TextView indent2_time;
        TextView indent2_price;
        TextView indent2_person;

        public ViewHolder(View itemView) {
            super(itemView);
            indent2_id = (TextView) itemView.findViewById(R.id.indent2_id);
            indent2_time = (TextView) itemView.findViewById(R.id.indent2_time);
            indent2_price = (TextView) itemView.findViewById(R.id.indent2_price);
            indent2_person = (TextView) itemView.findViewById(R.id.indent2_person);
        }
    }
}