package com.example.jeevansarthigujarat;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ToastIntentClass {
    ToastIntentClass(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    ToastIntentClass(Context context,Class<?> nextClass){
        context.startActivity(new Intent(context,nextClass));
    }
}
