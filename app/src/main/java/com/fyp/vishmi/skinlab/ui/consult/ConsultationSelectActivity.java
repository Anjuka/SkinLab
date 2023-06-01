package com.fyp.vishmi.skinlab.ui.consult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fyp.vishmi.skinlab.adapater.DoctorListAdapter;
import com.fyp.vishmi.skinlab.adapater.DoctorLocationsListAdapter;
import com.fyp.vishmi.skinlab.model.DocData;
import com.fyp.vishmi.skinlab.model.DocDataList;
import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.diary.DiaryActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.RequestData;
import com.fyp.vishmi.skinlab.model.RequestList;
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

public class ConsultationSelectActivity extends AppCompatActivity {

    private TextView tv_doc_name;
    private TextView tv_god_name;
    private TextView tv_mbs;
    private TextView tv_start;
    private RadioGroup rb_grp;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_scan;
    private Button btn_pay;
    private int pay_type;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    String userID = "";
    String name = "";
    String txt = "";
    int posi;

    private ArrayList<DocData> docData = new ArrayList<>();
    private ArrayList<DocDataList> docDataLists = new ArrayList<>();
    ArrayList<RequestList> requestLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_select);

        tv_doc_name = findViewById(R.id.tv_doc_name);
        tv_god_name = findViewById(R.id.tv_god_name);
        tv_mbs = findViewById(R.id.tv_mbs);
        tv_start = findViewById(R.id.tv_start);
        rb_grp = findViewById(R.id.rb_grp);
        btn_pay = findViewById(R.id.btn_pay);
        cl_home = findViewById(R.id.cl_home);
        cl_shelf = findViewById(R.id.cl_shelf);
        cl_diary = findViewById(R.id.cl_diary);
        cl_scan = findViewById(R.id.cl_scan);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        posi = getIntent().getIntExtra("doc", 0);

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                name =  documentSnapshot.getString("fName");
            }
        });

        firebaseFirestore.collection("doctorsData").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocData> types = queryDocumentSnapshots.toObjects(DocData.class);
                        docData.addAll(types);
                        docDataLists = docData.get(0).getDocDataLists();
                        Log.d("TAG", "onSuccess: "  + types);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: " + e);
            }
        });

        rb_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);

                txt = (String) radioButton.getText();
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestList requestList = new RequestList(userID, name, "28", txt);

                requestLists =  docDataLists.get(posi).getRequestLists();

                requestLists.add(requestList);

                docDataLists.get(posi).setRequestLists(requestLists);
                DocData docData = new DocData(userID, docDataLists);

                DocumentReference documentReferenceDoc = firebaseFirestore.collection("doctorsData").document("doctors");
                documentReferenceDoc.set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("TAG", "onFailure: "+e.getMessage());
                    }
                });

                Intent intentPay = new Intent(getApplicationContext(), ConsultDocListActivity.class);
                startActivity(intentPay);
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
}