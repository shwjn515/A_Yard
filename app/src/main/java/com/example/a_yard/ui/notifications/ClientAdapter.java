package com.example.a_yard.ui.notifications;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a_yard.R;
import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder>{

    private ArrayList<String> mData;
    private OnItemClickListener onItemClickListener;
    public ClientAdapter(ArrayList<String> data) {
        this.mData = data;
    }

    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clientlist, parent, false);
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
        String[] arr2 = mData.get(position).split(",", 2);
        // 绑定数据
        holder.client2_name.setText(arr2[0]);
        holder.client2_phone.setText(arr2[1]);
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
    public void setOnItemClickListener(ClientAdapter.OnItemClickListener listener) {
        this.onItemClickListener = (OnItemClickListener) listener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView client2_name;
        TextView client2_phone;

        public ViewHolder(View itemView) {
            super(itemView);
            client2_name= (TextView) itemView.findViewById(R.id.client2_name);
            client2_phone = (TextView) itemView.findViewById(R.id.client2_phone);
        }
    }
}