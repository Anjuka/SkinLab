package com.fyp.vishmi.skinlab.ui.doctor.patientrequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fyp.vishmi.skinlab.ManagePatientActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.adapater.DoctorListAdapter;
import com.fyp.vishmi.skinlab.adapater.DoctorLocationsListAdapter;
import com.fyp.vishmi.skinlab.adapater.DoctorReqListAdapter;
import com.fyp.vishmi.skinlab.adapater.ManagerPatientAdapter;
import com.fyp.vishmi.skinlab.model.AvailableHospital;
import com.fyp.vishmi.skinlab.model.DocData;
import com.fyp.vishmi.skinlab.model.DocDataList;
import com.fyp.vishmi.skinlab.model.MyDocData;
import com.fyp.vishmi.skinlab.model.MyDocDataList;
import com.fyp.vishmi.skinlab.model.PatientData;
import com.fyp.vishmi.skinlab.model.PatientDataList;
import com.fyp.vishmi.skinlab.model.RequestData;
import com.fyp.vishmi.skinlab.model.RequestList;
import com.fyp.vishmi.skinlab.model.ShelfItemList;
import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.doctor.docdashboard.DoctorDashboardActivity;
import com.fyp.vishmi.skinlab.ui.shelf.AddProductToShelfActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PatientRequestActivity extends AppCompatActivity implements DoctorReqListAdapter.itemClickListnerAccept, DoctorReqListAdapter.itemClickListnerReject {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ShelfItemList shelfItemList;
    ImageView btn_home;
    ConstraintLayout pait_details;
    TextView tv_pait_name;
    TextView tv_age;
    TextView tv_address;
    TextView tv_sl_packg;
    EditText et_date;
    EditText et_time;
    ImageView imageView14;
    Button btn_accept;
    Button btn_reject;
    String ref_id = "";
    String doc_name = "";
    String doc_field = "";
    String doc_designation = "";
    String doc_rating = "";
    String package_select = "";
    ArrayList<AvailableHospital> availableHospitals = new ArrayList<>();
    private ArrayList<String> next_consultationlist = new ArrayList<>();
    ArrayList<MyDocDataList> myDocDataLists = new ArrayList<>();
    ArrayList<PatientData>  patientData = new ArrayList<>();

    ArrayList<RequestData> dataArrayList = new ArrayList<>();
    ArrayList<RequestList> requestLists = new ArrayList<>();
    private ArrayList<DocData> docDataLists = new ArrayList<>();
    private ArrayList<MyDocData> myDocData = new ArrayList<>();

    private RecyclerView recyclerView;

    private GridLayoutManager layoutManager;
    DoctorReqListAdapter doctorReqListAdapter;

    ArrayList<PatientDataList> patientDataLists = new ArrayList<>();

    String userID = "";
    String user_token = "";
    int select_req_position;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_request);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        btn_home = findViewById(R.id.imageView9);
        pait_details = findViewById(R.id.pait_details);
        tv_pait_name = findViewById(R.id.tv_pait_name);
        tv_address = findViewById(R.id.textView9);
        btn_reject = findViewById(R.id.btn_reject);
        tv_age = findViewById(R.id.tv_ag);
        tv_sl_packg = findViewById(R.id.tv_sl_packg);
        et_date = findViewById(R.id.et_date);
        et_time = findViewById(R.id.et_time);
        imageView14 = findViewById(R.id.imageView14);
        btn_accept = findViewById(R.id.btn_accept);


        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ref_id = getIntent().getStringExtra("ref_id");
        doc_name = getIntent().getStringExtra("doc_name");
        Log.d("TAG", "onCreate: " + ref_id);
        gtPP();

        getManagePati();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                String myFormat = "MM/dd/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);

                et_date.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PatientRequestActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(PatientRequestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        et_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        imageView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_date.getText().toString().isEmpty() && et_time.getText().toString().isEmpty()) {

                } else {
                    String next_consultation = et_date.getText().toString() + " " + et_time.getText().toString();
                    next_consultationlist.add(next_consultation);

                    et_date.setText("");
                    et_time.setText("");
                }

                Log.d("TAG", "onClick: " + next_consultationlist);
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (next_consultationlist.size() ==0){
                    if (!et_date.getText().toString().isEmpty() && !et_time.getText().toString().isEmpty()) {
                        String next_consultation = et_date.getText().toString() + " " + et_time.getText().toString();
                        next_consultationlist.add(next_consultation);
                    }
                }

                MyDocDataList myDocDataList = new MyDocDataList(doc_name, doc_field, doc_designation, Integer.parseInt(doc_rating), availableHospitals, next_consultationlist, "N/A", package_select);
                myDocDataLists.add(myDocDataList);
                MyDocData myDocData = new MyDocData(user_token, myDocDataLists);


                DocumentReference documentReference = firebaseFirestore.collection("myDoctorsData").document(user_token);
                documentReference.set(myDocData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        saveToPatientManager(requestLists.get(select_req_position), next_consultationlist);

                        requestLists.remove(select_req_position);
                        upadteDocDetails(requestLists);


                        Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("TAG", "onFailure: "+e.getMessage());
                    }
                });
            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLists.remove(select_req_position);
                upadteDocDetails(requestLists);

                Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getManagePati() {
        firebaseFirestore.collection("managePatientData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<PatientData> types = queryDocumentSnapshots.toObjects(PatientData.class);
                        patientData.addAll(types);
                        patientDataLists = patientData.get(0).getPatientDataLists();
                        Log.d("TAG", "onSuccess: " + patientData);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void saveToPatientManager(RequestList docDataList, ArrayList<String> next_consultationlist) {

        ArrayList<RequestList> requestLists_save = new ArrayList<>();
        requestLists_save.add(docDataList);

        PatientDataList patientDataList = new PatientDataList(requestLists_save ,next_consultationlist,"");
        patientDataLists.add(patientDataList);

        PatientData patientData = new PatientData(userID, patientDataLists);

        DocumentReference documentReference = firebaseFirestore.collection("managePatientData").document(userID);
        documentReference.set(patientData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("TAG", "onFailure: "+e.getMessage());
            }
        });
    }

    private void upadteDocDetails(ArrayList<RequestList> requestLists) {

        for (DocDataList docData : docDataLists.get(0).getDocDataLists()) {
            if (docData.getRef_id().equals(ref_id)) {
                docData.setRequestLists(requestLists);

                DocData docData_ = new DocData(userID, docDataLists.get(0).getDocDataLists());
                DocumentReference documentReferenceDoc = firebaseFirestore.collection("doctorsData").document("doctors");
                documentReferenceDoc.set(docData_).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("TAG", "onFailure: "+e.getMessage());
                    }
                });
                }
            }
    }

    private void gtPP() {
        firebaseFirestore.collection("doctorsData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocData> types = queryDocumentSnapshots.toObjects(DocData.class);
                        docDataLists.addAll(types);
                        Log.d("TAG", "onSuccess: " + docDataLists);

                        for (DocDataList docData : docDataLists.get(0).getDocDataLists()) {
                            if (docData.getRef_id().equals(ref_id)) {
                                requestLists.clear();
                                requestLists.addAll(docData.getRequestLists());

                                doctorReqListAdapter = new DoctorReqListAdapter(PatientRequestActivity.this, requestLists, new DoctorReqListAdapter.itemClickListner() {
                                    @Override
                                    public void onItemClick(int postion) {

                                        pait_details.setVisibility(View.VISIBLE);

                                        tv_pait_name.setText(requestLists.get(postion).getName());
                                        tv_age.setText(requestLists.get(postion).getAge() + " Years");
                                        tv_address.setText("MILD Acne");
                                        tv_sl_packg.setText(requestLists.get(postion).getPack());

                                        doc_field = docData.getField();
                                        doc_designation = docData.getDesignation();
                                        doc_rating = String.valueOf(docData.getRating());
                                        availableHospitals = docData.getAvailableHospital();
                                        package_select = requestLists.get(postion).getPack();
                                        user_token = requestLists.get(postion).getId();
                                        select_req_position = postion;
                                        getUser();

                                    }
                                }, PatientRequestActivity.this:: onItemClickAcc, PatientRequestActivity.this::onItemClickRej);
                                recyclerView.setAdapter(doctorReqListAdapter);
                            }
                        }
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
    }

    private void getUser() {
        firebaseFirestore.collection("myDoctorsData").whereEqualTo("user_id", user_token)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<MyDocData> types = queryDocumentSnapshots.toObjects(MyDocData.class);
                        myDocData.addAll(types);
                        Log.d("TAG", "onSuccess: "  + types);
                        if (myDocData.size() ==0){
                            myDocDataLists = new ArrayList<>();
                        }
                        else {
                            myDocDataLists = myDocData.get(0).getDocDataLists();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("TAG", "onFailure: " + e);
            }
        });
    }

    @Override
    public void onItemClickAcc(int postion) {
        pait_details.setVisibility(View.VISIBLE);

        for (DocDataList docData : docDataLists.get(0).getDocDataLists()) {
            if (docData.getRef_id().equals(ref_id)) {
                requestLists.clear();
                requestLists.addAll(docData.getRequestLists());
                tv_pait_name.setText(requestLists.get(postion).getName());
                tv_age.setText(requestLists.get(postion).getAge() + " Years");
                tv_address.setText("MILD Acne");
                tv_sl_packg.setText(requestLists.get(postion).getPack());

                doc_field = docData.getField();
                doc_designation = docData.getDesignation();
                doc_rating = String.valueOf(docData.getRating());
                availableHospitals = docData.getAvailableHospital();
                package_select = requestLists.get(postion).getPack();
                user_token = requestLists.get(postion).getId();
                select_req_position = postion;
                getUser();
            }
        }
    }

    @Override
    public void onItemClickRej(int postion) {

        for (DocDataList docData : docDataLists.get(0).getDocDataLists()) {
            if (docData.getRef_id().equals(ref_id)) {
                requestLists.clear();
                requestLists.addAll(docData.getRequestLists());
                tv_pait_name.setText(requestLists.get(postion).getName());
                tv_age.setText(requestLists.get(postion).getAge() + " Years");
                tv_address.setText("MILD Acne");
                tv_sl_packg.setText(requestLists.get(postion).getPack());

                doc_field = docData.getField();
                doc_designation = docData.getDesignation();
                doc_rating = String.valueOf(docData.getRating());
                availableHospitals = docData.getAvailableHospital();
                package_select = requestLists.get(postion).getPack();
                user_token = requestLists.get(postion).getId();
                select_req_position = postion;
                getUser();
            }
        }

        requestLists.remove(select_req_position);
        upadteDocDetails(requestLists);

        Intent intent = new Intent(getApplicationContext(), DoctorDashboardActivity.class);
        startActivity(intent);
        finish();
    }
}