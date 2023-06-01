package com.fyp.vishmi.skinlab.ui.routing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.diary.DiaryActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.ShelfItemList;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.shelf.ShelfActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class RoutingActivity extends AppCompatActivity {

    private TextView tv_user_name;
    private ConstraintLayout cl_eve_hide;
    private ConstraintLayout cl_eve;
    private ConstraintLayout cl_night;
    private ConstraintLayout cl_nig_hide;
    private Button btn_complete_day;
    private Button btn_routing_calender;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ShelfItemList shelfItemList;

    String userID = "";

    private boolean isEveningOpen= false;
    private boolean isNightOpen= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing);

        cl_shelf = findViewById(R.id.cl_shelf);
        cl_scan = findViewById(R.id.cl_scan);
        cl_diary = findViewById(R.id.cl_diary);
        cl_consult = findViewById(R.id.cl_consult);
        cl_home = findViewById(R.id.cl_home);

        tv_user_name = findViewById(R.id.tv_user_name);
        cl_eve = findViewById(R.id.cl_eve);
        cl_eve_hide = findViewById(R.id.cl_eve_hide);
        cl_night = findViewById(R.id.cl_night);
        cl_nig_hide = findViewById(R.id.cl_nig_hide);
        btn_complete_day = findViewById(R.id.btn_complete_day);
        btn_routing_calender = findViewById(R.id.btn_routing_calender);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                tv_user_name.setText("Hello " + documentSnapshot.getString("fName") + "!");
            }
        });

        cl_eve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEveningOpen == false) {
                    cl_eve_hide.setVisibility(View.VISIBLE);
                    isEveningOpen = true;
                }
                else {
                    cl_eve_hide.setVisibility(View.GONE);
                    isEveningOpen = false;
                }
            }
        });

        cl_night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNightOpen == false) {
                    cl_nig_hide.setVisibility(View.VISIBLE);
                    isNightOpen = true;
                }
                else {
                    cl_nig_hide.setVisibility(View.GONE);
                    isNightOpen = false;
                }
            }
        });

        btn_complete_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RoutingActivity.this, "The day not still complete...", Toast.LENGTH_SHORT).show();
            }
        });

        btn_routing_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RoutingCalenderActivity.class);
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

        cl_shelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShelf = new Intent(getApplicationContext(), ShelfActivity.class);
                startActivity(intentShelf);
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
    }
}