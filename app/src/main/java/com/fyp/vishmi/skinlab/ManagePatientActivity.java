package com.fyp.vishmi.skinlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.vishmi.skinlab.adapater.DoctorReqListAdapter;
import com.fyp.vishmi.skinlab.adapater.ManagerPatientAdapter;
import com.fyp.vishmi.skinlab.model.DocData;
import com.fyp.vishmi.skinlab.model.DocDataList;
import com.fyp.vishmi.skinlab.model.PatientData;
import com.fyp.vishmi.skinlab.model.PatientDataList;
import com.fyp.vishmi.skinlab.model.ShelfItemList;
import com.fyp.vishmi.skinlab.ui.doctor.docdashboard.DoctorDashboardActivity;
import com.fyp.vishmi.skinlab.ui.doctor.patientrequest.PatientRequestActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ManagePatientActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ImageView btn_home;
    ImageView iv_back;
    TextView tv_pait_name;
    TextView tv_age;
    TextView tv_address;
    TextView tv_sl_packg;
    TextView et_date;
    TextView et_time;
    ConstraintLayout pait_details;
    ConstraintLayout pait_logs;
    Button btn_logs;
    TextView et_log_saved;
    Button btn_logs_save;
    EditText et_log;
    int posi;

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ManagerPatientAdapter managerPatientAdapter;

    private ArrayList<PatientData> patientData = new ArrayList<>();
    private ArrayList<PatientDataList> patientDataLists = new ArrayList<>();

    String userID = "";
    String user_token = "";
    int select_req_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patient);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        btn_home = findViewById(R.id.imageView9);
        iv_back = findViewById(R.id.iv_back);
        tv_pait_name = findViewById(R.id.tv_pait_name);
        tv_address = findViewById(R.id.textView9);
        tv_age = findViewById(R.id.tv_ag);
        tv_sl_packg = findViewById(R.id.tv_sl_packg);
        et_date = findViewById(R.id.et_date);
        et_time = findViewById(R.id.et_time);
        pait_details = findViewById(R.id.pait_details);
        pait_logs = findViewById(R.id.pait_logs);
        btn_logs = findViewById(R.id.btn_logs);
        et_log_saved = findViewById(R.id.et_log_saved);
        btn_logs_save = findViewById(R.id.btn_logs_save);
        et_log = findViewById(R.id.et_log);

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        firebaseFirestore.collection("managePatientData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<PatientData> types = queryDocumentSnapshots.toObjects(PatientData.class);
                        patientData.addAll(types);
                        Log.d("TAG", "onSuccess: " + patientData);

                        managerPatientAdapter = new ManagerPatientAdapter(ManagePatientActivity.this, patientData.get(0).getPatientDataLists(), new ManagerPatientAdapter.itemClickListner() {
                            @Override
                            public void onItemClick(int postion) {


                                pait_details.setVisibility(View.VISIBLE);

                                tv_pait_name.setText(patientData.get(0).getPatientDataLists().get(postion).getRequestData().get(0).getName());
                                tv_age.setText(patientData.get(0).getPatientDataLists().get(postion).getRequestData().get(0).getAge() + " Years");
                                tv_address.setText("MILD Acne");
                                tv_sl_packg.setText(patientData.get(0).getPatientDataLists().get(postion).getRequestData().get(0).getPack());
                                String[] splited = patientData.get(0).getPatientDataLists().get(postion).getNextConsultation().get(0).split("\\s+");
                                String Date = splited[0];
                                String Time = splited[1];
                                et_date.setText(Date);
                                et_time.setText(Time);
                                posi = postion;
                            }
                        });
                        recyclerView.setAdapter(managerPatientAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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

                if (pait_details.getVisibility() == View.VISIBLE) {
                    pait_details.setVisibility(View.GONE);
                    et_log.setText("");
                } else if (pait_logs.getVisibility() == View.VISIBLE) {
                    pait_logs.setVisibility(View.GONE);
                    et_log.setText("");
                } else {
                    Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pait_logs.setVisibility(View.VISIBLE);
                et_log_saved.setText(patientData.get(0).getPatientDataLists().get(posi).getNote());

            }
        });

        btn_logs_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = patientData.get(0).getPatientDataLists().get(posi).getNote();
                note = note + "\n"+et_log.getText().toString().trim();

                patientData.get(0).getPatientDataLists().get(posi).setNote(note);

                PatientData patientData_ = new PatientData(userID, patientData.get(0).getPatientDataLists());

                DocumentReference documentReference = firebaseFirestore.collection("managePatientData").document(userID);
                documentReference.set(patientData_).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        pait_logs.setVisibility(View.GONE);
                        et_log.setText("");

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("TAG", "onFailure: "+e.getMessage());
                    }
                });
            }
        });
    }
}