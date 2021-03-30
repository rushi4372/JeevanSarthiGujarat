package com.example.jeevansarthigujarat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HospitalDetailActivity extends AppCompatActivity {

    ImageView iv;
    TextView hospName,address,mobileNo,docname,docname1;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        sp = getSharedPreferences(ConstantSp.PREF, Context.MODE_PRIVATE);
        iv= findViewById(R.id.hospital_detail_iv);
        hospName = findViewById(R.id.hsopital_detail_hospname);
        address = findViewById(R.id.hsopital_detail_address);
        mobileNo = findViewById(R.id.hsopital_detail_mobile);
        docname = findViewById(R.id.hsopital_detail_docname);
        docname1 = findViewById(R.id.hsopital_detail_docname1);

        getSupportActionBar().setTitle(sp.getString(ConstantSp.HNAME,""));

        hospName.setText(sp.getString(ConstantSp.HNAME,""));
        address.setText("ADDRESS :-"+sp.getString(ConstantSp.ADDRESS,""));
        mobileNo.setText("MOBILE NO :-"+sp.getString(ConstantSp.CONTACT_PERSON_MOBILE,""));
        docname.setText("DOCTOR NAME:-"+sp.getString(ConstantSp.DOCNAME,"")+", "+sp.getString(ConstantSp.DOCNAME1,""));
        /*docname1.setText(sp.getString(ConstantSp.DOCNAME1,""));*/

        Picasso.get().load(sp.getString(ConstantSp.IMAGE,"")).placeholder(R.drawable.placeholder).into(iv);

    }
}