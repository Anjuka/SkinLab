package com.fyp.vishmi.skinlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import com.fyp.vishmi.skinlab.ui.doctor.docdashboard.DoctorDashboardActivity;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity {

    CalendarView calendarView;
    ConstraintLayout cl_manage_patient;
    ImageView btn_home;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        calendarView = findViewById(R.id.calendarView);
        cl_manage_patient = findViewById(R.id.cl_manage_patient);
        btn_home = findViewById(R.id.imageView9);
        iv_back = findViewById(R.id.iv_back);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                if (i2 == day){
                    Toast.makeText(getApplicationContext(), "You have appointment today 7.00pm", Toast.LENGTH_SHORT).show();
                }
                else if (i2 == day +1){
                    Toast.makeText(getApplicationContext(), "You have appointment today 5.20pm", Toast.LENGTH_SHORT).show();
                }

                else if (i2 == day +2){
                    Toast.makeText(getApplicationContext(), "You have appointment today 8.40pm", Toast.LENGTH_SHORT).show();
                }

                else if (i2 == day -1){
                    Toast.makeText(getApplicationContext(), "You have appointment today 4.00pm", Toast.LENGTH_SHORT).show();
                }

                else if (i2 == day -2){
                    Toast.makeText(getApplicationContext(), "You have appointment today 7.20pm", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "You don't have any appointment for today", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cl_manage_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagePatientActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
                    startActivity(intent);
                    finish();
            }
        });
    }
}