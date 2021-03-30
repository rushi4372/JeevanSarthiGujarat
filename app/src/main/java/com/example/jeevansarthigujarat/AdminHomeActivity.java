package com.example.jeevansarthigujarat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    TextView manageHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        manageHospital = findViewById(R.id.admin_home_hospital);

        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        manageHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ToastIntentClass(AdminHomeActivity.this, GetHospitalActivity.class);
            }
        });
    }
}