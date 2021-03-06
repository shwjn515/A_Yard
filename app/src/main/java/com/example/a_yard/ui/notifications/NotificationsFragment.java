package com.example.a_yard.ui.notifications;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.KeyStore;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.a_yard.Login;
import com.example.a_yard.Photo;
import com.example.a_yard.R;

public class NotificationsFragment extends Fragment {
    private Button btn_name,btn_person,btn_indent,btn_client,btn_bill;
    private  ImageButton btn_phpto;
    private boolean isFirstLoading = true;
    private static final String DEFAULT_KEY_NAME = "default_key";
    public static boolean successful = false;
    private KeyStore keyStore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences =
                getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        btn_name=(Button) getActivity().findViewById(R.id.name);
        btn_name.setText(sharedPreferences.getString("name","点击登录"));
        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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
        String photoUrl = sharedPreferences.getString("u_photo","default");
        if(photoUrl.contains("https")) {
            Glide.with(btn_phpto)
                    .load(photoUrl)
                    .thumbnail(Glide.with(btn_phpto).load(R.drawable.touxiang))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .into(btn_phpto);
        }
        //个人信息
        btn_person=(Button) getActivity().findViewById(R.id.person);
        btn_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFingerprint();
            }
        });
        //账单管理
        btn_bill=(Button) getActivity().findViewById(R.id.btn_bill);
        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Bill.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
        //订单管理
        btn_indent=(Button) getActivity().findViewById(R.id.btn_indent);
        btn_indent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IndentActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

            }
        });
        //顾客管理
        btn_client=(Button) getActivity().findViewById(R.id.btn_client);
        btn_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClientActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
    }
    @SuppressLint("MissingPermission")
    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(getActivity(), "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(getActivity())
                    .setTitle("请验证指纹信息")
                    .setDescription("")
                    .setNegativeButton("取消", getActivity().getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Log.i(TAG, "Cancel button clicked");
//                            Toast.makeText(getActivity(),"取消",Toast.LENGTH_LONG);
                        }
                    })
                    .build();
            CancellationSignal cancellationSignal = new CancellationSignal();
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                @Override
                public void onCancel() {
                    //handle cancel result
                    //Log.i(TAG, "Canceled");
                    Toast.makeText(getContext(),"取消",Toast.LENGTH_SHORT).show();
                }
            });
            BiometricPrompt.AuthenticationCallback mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    //Log.i(TAG, "onAuthenticationError " + errString);
                }

                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Intent intent = new Intent(getActivity(), Person.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                    //Log.i(TAG, "onAuthenticationSucceeded " + result.toString());
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    //Log.i(TAG, "onAuthenticationFailed ");
                }
            };
            biometricPrompt.authenticate(cancellationSignal, getActivity().getMainExecutor(), mAuthenticationCallback);
        }
        return true;
    }
    /**
     * 在fragment可见的时候，刷新数据
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            SharedPreferences sharedPreferences =
                    getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            String photoUrl = sharedPreferences.getString("u_photo","default");
            if(photoUrl.contains("https")) {
                Glide.with(btn_phpto)
                        .load(photoUrl)
                        .thumbnail(Glide.with(btn_phpto).load(R.drawable.touxiang))
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(btn_phpto);
            }
        }
        isFirstLoading = false;
    }
}