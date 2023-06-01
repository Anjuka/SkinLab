package com.fyp.vishmi.skinlab.ui.routing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fyp.vishmi.skinlab.adapater.DiaryAdapter;
import com.fyp.vishmi.skinlab.model.DiaryData;
import com.fyp.vishmi.skinlab.model.RoutingData;
import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.diary.DiaryActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.shelf.ShelfActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuestionerActivity extends AppCompatActivity {

    private Button btn_cancel;
    private Button btn_proceed;
    private ImageView iv_back;
    private ViewPager2 viewPager;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;

    private ArrayList<Qu_PageViewModel> qu_pageViewModels_list = new ArrayList<>();
    private ArrayList<String> q1_list = new ArrayList<>();
    private ArrayList<String> q2_list = new ArrayList<>();
    private ArrayList<String> q3_list = new ArrayList<>();
    private ArrayList<String> q4_list = new ArrayList<>();
    private ArrayList<String> q5_list = new ArrayList<>();
    private ArrayList<String> selected_radios = new ArrayList<>();

    private QuesVPAdapter quesVPAdapter;
    private boolean isRoutingHave = false;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questioner);

        cl_shelf = findViewById(R.id.cl_shelf);
        cl_scan = findViewById(R.id.cl_scan);
        cl_diary = findViewById(R.id.cl_diary);
        cl_home = findViewById(R.id.cl_home);
        btn_proceed = findViewById(R.id.btn_proceed);
        btn_cancel = findViewById(R.id.btn_cancel);
        iv_back = findViewById(R.id.iv_back);
        viewPager = findViewById(R.id.viewPager);
        cl_consult = findViewById(R.id.cl_consult);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        q1_list.add("1-2l");
        q1_list.add("3-4l");
        q1_list.add("More than 5l");

        q2_list.add("Morning to evening");
        q2_list.add("Morning to night");
        q2_list.add("Overnight");

        q3_list.add("Spicy");
        q3_list.add("Fat and carbs");
        q3_list.add("Sugary");

        q4_list.add("Very often");
        q4_list.add("Moderately often");
        q4_list.add("Rarely");

        q5_list.add("Yes");
        q5_list.add("No");
        q5_list.add("");

        qu_pageViewModels_list.add(new Qu_PageViewModel("Question 1", "How many litres of water do you drink per day?", q1_list));
        qu_pageViewModels_list.add(new Qu_PageViewModel("Question 2", "What are your working hours?", q2_list));
        qu_pageViewModels_list.add(new Qu_PageViewModel("Question 3", "Which food category do you prefer the most?", q3_list));
        qu_pageViewModels_list.add(new Qu_PageViewModel("Question 4", "How often do you get exposed to the sun?", q4_list));
        qu_pageViewModels_list.add(new Qu_PageViewModel("Question 5", "Do you use sunscreen?", q5_list));

        quesVPAdapter = new QuesVPAdapter(qu_pageViewModels_list, new QuesVPAdapter.OnRadioGroupSelectedListener() {
            @Override
            public void onButtonSelected(String value) {
                Log.d("TAG", "onButtonSelected: " + value);
                selected_radios.add(value);
            }
        });
        viewPager.setAdapter(quesVPAdapter);




        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG", "onClick: " + selected_radios);
                if (selected_radios.size() == 5){

                    RoutingData routingData = new RoutingData(true, userID);

                    DocumentReference documentReference = firebaseFirestore.collection("routing").document(userID);
                    documentReference.set(routingData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d("TAG", "onFailure: "+e.getMessage());
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), CreatingRoutingLoadingActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(QuestionerActivity.this, "Complete Questioner...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
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

    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }
}