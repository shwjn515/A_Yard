package com.example.a_yard.ui.notifications;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Button btn;
    public Dialog onCreateDialog(Bundle savedInstanceState, Button btn) {
        // 使用当前日期作为日期选择对话框的默认值
        String[] btntext = btn.getText().toString().split("-");
        int year = Integer.parseInt(btntext[0]);
        int month = Integer.parseInt(btntext[1]);
        int day = Integer.parseInt(btntext[2]);
        // 创建日期选择对话框的一个实例并返回它
        return new DatePickerDialog(getActivity(), this, year, month-1, day);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return onCreateDialog(savedInstanceState,btn);
    }

    public Button getBtn() {
        return btn;
    }

    public DatePickerFragment setBtn(Button btn) {
        this.btn = btn;
        return this;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // 当用户设置日期时执行
        btn.setText(String.format("%d-%02d-%02d",year,month+1,day));
    }
}