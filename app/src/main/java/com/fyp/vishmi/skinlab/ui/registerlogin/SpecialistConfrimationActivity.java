package com.fyp.vishmi.skinlab.ui.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.doctor.docdashboard.DoctorDashboardActivity;

public class SpecialistConfrimationActivity extends AppCompatActivity {

    private Button btn_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_confrimation);

        btn_reg = findViewById(R.id.btn_reg);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}