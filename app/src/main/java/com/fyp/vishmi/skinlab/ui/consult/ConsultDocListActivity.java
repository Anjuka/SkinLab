package com.fyp.vishmi.skinlab.ui.consult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.vishmi.skinlab.ui.diary.DiaryActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.adapater.DoctorListAdapter;
import com.fyp.vishmi.skinlab.adapater.DoctorLocationsListAdapter;
import com.fyp.vishmi.skinlab.model.DocData;
import com.fyp.vishmi.skinlab.model.DocDataList;
import com.fyp.vishmi.skinlab.model.ShelfItemList;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.shelf.ShelfActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsultDocListActivity extends AppCompatActivity {

    private Button btn_add;
    private Button btn_req_consultation;
    private ConstraintLayout cl_doc_details;
    private RecyclerView rv_doctors;
    private ImageView iv_close;
    private ImageView iv_back;
    private TextView tv_doc_name;
    private TextView tv_god_name;
    private TextView tv_mbs;
    private TextView tv_start;
    private RecyclerView rv_available_hospital;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_scan;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ShelfItemList shelfItemList;
    ProgressDialog progressDialog;

    String userID = "";
    int posi;
    private GridLayoutManager layoutManager;
    private GridLayoutManager layoutManager2;

    private ArrayList<DocData> docDataLists = new ArrayList<>();
    private ArrayList<Map<String, String>> docData = new ArrayList<>();
    private ArrayList<Map<String, ArrayList<DocDataList>>> docDataaval = new ArrayList<>();
    private DoctorListAdapter doctorListAdapter;
    private DoctorLocationsListAdapter doctorLocationsListAdapter;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_doc_list);

        progressDialog = new ProgressDialog(this);

        cl_home = findViewById(R.id.cl_home);
        cl_shelf = findViewById(R.id.cl_shelf);
          cl_diary = findViewById(R.id.cl_diary);
          cl_scan = findViewById(R.id.cl_scan);
        btn_add = findViewById(R.id.btn_add);
        btn_req_consultation = findViewById(R.id.btn_req_consultation);
        cl_doc_details = findViewById(R.id.cl_doc_details);
        rv_doctors = findViewById(R.id.rv_doctors);
        iv_close = findViewById(R.id.iv_close);
        tv_doc_name = findViewById(R.id.tv_doc_name);
        tv_god_name = findViewById(R.id.tv_god_name);
        tv_mbs = findViewById(R.id.tv_mbs);
        tv_start = findViewById(R.id.tv_start);
        rv_available_hospital = findViewById(R.id.rv_available_hospital);
        iv_back = findViewById(R.id.iv_back);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        layoutManager = new GridLayoutManager(this, 1);
        layoutManager2 = new GridLayoutManager(this, 1);
        rv_doctors.setHasFixedSize(true);
        rv_doctors.setLayoutManager(layoutManager);

        rv_available_hospital.setHasFixedSize(true);
        rv_available_hospital.setLayoutManager(layoutManager2);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_doc_details.setVisibility(View.GONE);
            }
        });


          progressDialog.setMessage("Loading");
          progressDialog.show();

       firebaseFirestore.collection("doctorsData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocData> types = queryDocumentSnapshots.toObjects(DocData.class);
                        docDataLists.addAll(types);
                        Log.d("TAG", "onSuccess: "  + types);
                        doctorListAdapter = new DoctorListAdapter(ConsultDocListActivity.this, docDataLists.get(0).getDocDataLists(), new DoctorListAdapter.itemClickListner() {
                            @Override
                            public void onItemClick(int postion) {
                                cl_doc_details.setVisibility(View.VISIBLE);

                                posi = postion;

                                tv_doc_name.setText(docDataLists.get(0).getDocDataLists().get(postion).getDocName());
                                tv_god_name.setText(docDataLists.get(0).getDocDataLists().get(postion).getField());
                                tv_mbs.setText(docDataLists.get(0).getDocDataLists().get(postion).getDesignation());
                                tv_start.setText(String.valueOf(docDataLists.get(0).getDocDataLists().get(postion).getRating()));

                                doctorLocationsListAdapter = new DoctorLocationsListAdapter(ConsultDocListActivity.this, docDataLists.get(0).getDocDataLists().get(postion).getAvailableHospital());
                                rv_available_hospital.setAdapter(doctorLocationsListAdapter);
                            }
                        });
                        rv_doctors.setAdapter(doctorListAdapter);
                        progressDialog.cancel();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("TAG", "onFailure: " + e);
                        progressDialog.cancel();
                    }
                });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MySpecialistActivity.class);
                startActivity(intent);
            }
        });

        btn_req_consultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_doc_details.setVisibility(View.GONE);
                Intent intentMakeRequest = new Intent(getApplicationContext(), ConsultationSelectActivity.class);
                intentMakeRequest.putExtra("doc", posi);
                startActivity(intentMakeRequest);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intentBack);
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

    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }
}