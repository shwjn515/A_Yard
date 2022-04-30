package com.example.a_yard.ui.notifications;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.Toast;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a_yard.Login;
import com.example.a_yard.MainActivity;
import com.example.a_yard.Photo;
import com.example.a_yard.R;

public class NotificationsFragment extends Fragment {
    private Button btn_name,btn_person,btn_indent;
    public static ImageButton btn_phpto;
    private static final String DEFAULT_KEY_NAME = "default_key";

    KeyStore keyStore;

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
        //个人信息
        btn_person=(Button) getActivity().findViewById(R.id.person);
        btn_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFingerprint();
            }
        });
        //订单管理
        btn_indent=(Button) getActivity().findViewById(R.id.btn_indent);
        btn_indent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Indent.class);
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

                    //Log.i(TAG, "onAuthenticationSucceeded " + result.toString());
                    Intent intent = new Intent(getActivity(), Person.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
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

    @TargetApi(23)
    private void initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onAuthenticated() {
        Intent intent = new Intent(getActivity(), Person.class);
        startActivity(intent);
    }
}