package com.fyp.vishmi.skinlab.ui.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.DiaryData;
import com.fyp.vishmi.skinlab.model.DiaryDataList;
import com.fyp.vishmi.skinlab.model.DiaryFeedbackData;
import com.fyp.vishmi.skinlab.model.DiaryFeedbackDataList;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DiaryCompareActivity extends AppCompatActivity {

    private ImageView iv_back;
    private ImageView iv_first;
    private ImageView iv_second;
    private TextView tv_d1;
    private TextView tv_d2;
    private EditText tv_txt;
    private Button btn_compare_save;
    private EditText et_txt;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private ArrayList<DiaryDataList> diaryDataLists = new ArrayList<>();
    private ArrayList<DiaryData> diaryData = new ArrayList<>();
    ArrayList<DiaryFeedbackDataList> diaryFeedbackDataListArr = new ArrayList<>();
    ArrayList<DiaryFeedbackData> diaryFeedbackData_from = new ArrayList<>();

    int first_position;
    int second_position;

    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_copmare);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        first_position = getIntent().getIntExtra("first", 0);
        second_position = getIntent().getIntExtra("second", 0);

        iv_back = findViewById(R.id.iv_back);
        iv_first = findViewById(R.id.iv_first);
        iv_second = findViewById(R.id.iv_scond);
        tv_d1 = findViewById(R.id.tv_d1);
        tv_d2 = findViewById(R.id.tv_d2);
        tv_txt = findViewById(R.id.tv_txt);
        btn_compare_save = findViewById(R.id.btn_compare_save);
        et_txt = findViewById(R.id.tv_txt);
        cl_shelf = findViewById(R.id.cl_shelf);
        cl_scan = findViewById(R.id.cl_scan);
        cl_diary = findViewById(R.id.cl_diary);
        cl_consult = findViewById(R.id.cl_consult);
        cl_home = findViewById(R.id.cl_home);

        progressDialog.setMessage("Loading");
        progressDialog.show();

        firebaseFirestore.collection("diaryFeedback").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DiaryFeedbackData> diary = queryDocumentSnapshots.toObjects(DiaryFeedbackData.class);
                        diaryFeedbackData_from.addAll(diary);
                        diaryFeedbackDataListArr.addAll(diaryFeedbackData_from.get(0).getDiaryFeedbackDataLists());
                        Log.d("TAG", "onSuccess: "  + diary);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("TAG", "onFailure: " + e);
                progressDialog.cancel();
            }
        });

        firebaseFirestore.collection("diaryImageUploads").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DiaryData> diary = queryDocumentSnapshots.toObjects(DiaryData.class);
                        diaryData.addAll(diary);
                        diaryDataLists.clear();
                        diaryDataLists = diaryData.get(0).getDiaryDataLists();
                        Log.d("TAG", "onSuccess: "  + diary);

                        Glide.with(getApplicationContext())
                                .load(diaryDataLists.get(first_position).getImg_url())
                                .into(iv_first);

                        Glide.with(getApplicationContext())
                                .load(diaryDataLists.get(second_position).getImg_url())
                                .into(iv_second);

                        tv_d1.setText(diaryDataLists.get(first_position).getCapture_date());
                        tv_d2.setText(diaryDataLists.get(second_position).getCapture_date());


                        progressDialog.cancel();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("TAG", "onFailure: " + e);
                progressDialog.cancel();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btn_compare_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!et_txt.getText().toString().isEmpty()){

                   progressDialog.setMessage("Saving...");
                   progressDialog.show();
                   ArrayList<DiaryDataList> diaryDataLists_ = new ArrayList<>();
                   diaryDataLists_.add(diaryDataLists.get(first_position));
                   diaryDataLists_.add(diaryDataLists.get(second_position));

                   DiaryFeedbackDataList diaryFeedbackDataList = new DiaryFeedbackDataList(diaryDataLists_, et_txt.getText().toString().trim());
                   diaryFeedbackDataListArr.add(diaryFeedbackDataList);
                   DiaryFeedbackData diaryFeedbackData = new DiaryFeedbackData(diaryFeedbackDataListArr);


                   DocumentReference documentReference = firebaseFirestore.collection("diaryFeedback").document(firebaseAuth.getCurrentUser().getUid());
                   documentReference.set(diaryFeedbackData).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                           startActivity(intent);
                           finish();
                           progressDialog.cancel();
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           progressDialog.cancel();
                       }
                   });
               }
               else {
                   Toast.makeText(DiaryCompareActivity.this, "Feedback is empty...", Toast.LENGTH_SHORT).show();
               }
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
    }
}