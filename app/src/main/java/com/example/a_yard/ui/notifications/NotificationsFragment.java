package com.example.a_yard.ui.notifications;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a_yard.Login;
import com.example.a_yard.MainActivity;
import com.example.a_yard.Photo;
import com.example.a_yard.R;

public class NotificationsFragment extends Fragment {
    private Button btn_name;
    private ImageButton btn_phpto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        btn_name=(Button) getActivity().findViewById(R.id.name);
        btn_name.setText("点击登录");
        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                btn_name.setText("156****8293");
            }
        });
        btn_phpto=(ImageButton) getActivity().findViewById(R.id.myphoto);
        btn_phpto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Photo.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
    }
}