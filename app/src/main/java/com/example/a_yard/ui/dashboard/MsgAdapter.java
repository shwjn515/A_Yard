package com.example.a_yard.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a_yard.R;

import java.util.List;

public class MsgAdapter extends ArrayAdapter<Msg> {

    private final int resourceID;

    public MsgAdapter(Context context, int resource, List<Msg> objects) {
        super(context, resource, objects);
        resourceID = resource;
    }
//绑定器
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //msgList
        Msg msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceID,  null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
            viewHolder.leftHead = (ImageView) view.findViewById(R.id.left_image);
            viewHolder.rightHead = (ImageView) view.findViewById(R.id.right_image);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(msg.getType() == Msg.MSG_RECEIVE) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getMessage());
            String url = Msg.getUrl();
            Glide.with(viewHolder.leftHead)
                    .load(Msg.getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.leftHead);
        }else {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getMessage());
            Glide.with(viewHolder.leftHead)
                    .load(Msg.getUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.leftHead);
        }
        return view;
    }

    static class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        ImageView leftHead;
        ImageView rightHead;
    }

}