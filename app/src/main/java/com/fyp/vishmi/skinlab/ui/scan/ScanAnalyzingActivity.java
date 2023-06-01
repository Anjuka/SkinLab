package com.fyp.vishmi.skinlab.ui.scan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.RoutingData;
import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.fyp.vishmi.skinlab.ui.routing.QuestionerActivity;
import com.fyp.vishmi.skinlab.ui.routing.RoutingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ScanAnalyzingActivity extends AppCompatActivity {

    private ImageView iv_back;
    private ImageView iv_image;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;
    private Button btn_routing;
    private TextView tv_skin_type;
    private Button btn_consult_doc;
    private String skin_type;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    String userID = "";
    private boolean isRoutingHave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_conirm);

        iv_back = findViewById(R.id.iv_back);
        iv_image = findViewById(R.id.iv_image);
        cl_shelf = findViewById(R.id.cl_shelf);
        cl_scan = findViewById(R.id.cl_scan);
        cl_diary = findViewById(R.id.cl_diary);
        cl_consult = findViewById(R.id.cl_consult);
        cl_home = findViewById(R.id.cl_home);
        btn_routing = findViewById(R.id.btn_routing);
        btn_consult_doc = findViewById(R.id.btn_consult_doc);
        tv_skin_type = findViewById(R.id.tv_skin_type);

        skin_type = getIntent().getStringExtra("skin_type");
        String file = getIntent().getStringExtra("path");
        tv_skin_type.setText(skin_type);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        iv_image.setRotation(180f);

        Log.d("TAG", "onCreate: ");
        Glide.with(getApplicationContext())
                .load(file)
                .into(iv_image);

        firebaseFirestore.collection("routing").whereEqualTo("id", userID).
                get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<RoutingData> routingData = queryDocumentSnapshots.toObjects(RoutingData.class);
                        Log.d("TAG", "onSuccess: " + routingData);

                        if (routingData.size() == 0){
                            isRoutingHave = false;
                        }
                        else {
                            if (routingData.get(0).isCreated() == true) {
                                isRoutingHave = true;
                            } else {
                                isRoutingHave = false;
                            }
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("TAG", "onFailure: " + e);

            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intentHome);
                finish();
            }
        });

        cl_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intentDiary = new Intent(getApplicationContext(), Diary)
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

        cl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intentHome);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        btn_routing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRoutingHave == true){
                    Intent intent = new Intent(getApplicationContext(), RoutingActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent routing = new Intent(getApplicationContext(), QuestionerActivity.class);
                    startActivity(routing);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });

        btn_consult_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentConsult = new Intent(getApplicationContext(), ConsultDocListActivity.class);
                startActivity(intentConsult);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}