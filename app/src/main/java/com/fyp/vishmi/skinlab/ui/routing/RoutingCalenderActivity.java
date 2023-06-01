package com.fyp.vishmi.skinlab.ui.routing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.diary.DiaryActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.shelf.ShelfActivity;

public class RoutingCalenderActivity extends AppCompatActivity {

    private ConstraintLayout cl_eve;
    private ConstraintLayout cl_3;
    private ImageView iv_back;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing_calender);

        cl_shelf = findViewById(R.id.cl_shelf);
        cl_scan = findViewById(R.id.cl_scan);
        cl_diary = findViewById(R.id.cl_diary);
        cl_consult = findViewById(R.id.cl_consult);
        cl_home = findViewById(R.id.cl_home);
        cl_eve = findViewById(R.id.cl_eve);
        cl_3 = findViewById(R.id.cl_3);
        iv_back = findViewById(R.id.iv_back);

        cl_eve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RoutingCalenderActivity.this, "On-going Week Not Complete...", Toast.LENGTH_SHORT).show();
            }
        });

        cl_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RoutingCalenderActivity.this, "You have to update subscription to get this feature...", Toast.LENGTH_SHORT).show();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intentHome);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cl_consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMyDoc = new Intent(getApplicationContext(), ConsultDocListActivity.class);
                startActivity(intentMyDoc);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        cl_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cl_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDiary = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(intentDiary);
                overridePendingTransition(0, 0);
                finish();

            }
        });

        cl_shelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShelf = new Intent(getApplicationContext(), ShelfActivity.class);
                startActivity(intentShelf);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}