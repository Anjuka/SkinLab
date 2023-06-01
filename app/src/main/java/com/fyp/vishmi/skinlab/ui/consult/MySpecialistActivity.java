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

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.adapater.DoctorLocationsListAdapter;
import com.fyp.vishmi.skinlab.adapater.MyDoctorListAdapter;
import com.fyp.vishmi.skinlab.model.DocDataList;
import com.fyp.vishmi.skinlab.model.MyDocData;
import com.fyp.vishmi.skinlab.model.ShelfItemList;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.fyp.vishmi.skinlab.ui.shelf.ShelfActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySpecialistActivity extends AppCompatActivity {

    private Button btn_add;
    private Button btn_req_consultation;
    private ConstraintLayout cl_doc_details;
    private ConstraintLayout cl_home;
    private ConstraintLayout cl_shelf;
    private RecyclerView rv_doctors;
    private ImageView iv_close;
    private ImageView iv_back;
    private TextView tv_doc_name;
    private TextView tv_god_name;
    private TextView tv_mbs;
    private TextView tv_start;
    private TextView tv_available;
    private TextView tv_selected_pack;
    private TextView tv_prev_consult_date;
    private TextView tv_next_consult_date;
    private TextView tv_change_pack;
    private ConstraintLayout cl_package_details;
    private ImageView iv_pack_close;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ShelfItemList shelfItemList;

    String userID = "";
    private GridLayoutManager layoutManager;
    private GridLayoutManager layoutManager2;

    String show_val = "";

    private ArrayList<MyDocData> docDataLists = new ArrayList<>();
    private ArrayList<Map<String, String>> docData = new ArrayList<>();
    private ArrayList<Map<String, ArrayList<DocDataList>>> docDataaval = new ArrayList<>();
    private MyDoctorListAdapter doctorListAdapter;
    private DoctorLocationsListAdapter doctorLocationsListAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_specialist);

        progressDialog = new ProgressDialog(this);

        cl_home = findViewById(R.id.cl_home);
        btn_add = findViewById(R.id.btn_add);
        tv_change_pack = findViewById(R.id.tv_change_pack);
        cl_shelf = findViewById(R.id.cl_shelf);
        tv_available = findViewById(R.id.tv_available);
        btn_req_consultation = findViewById(R.id.btn_req_consultation);
        cl_doc_details = findViewById(R.id.cl_doc_details);
        rv_doctors = findViewById(R.id.rv_doctors);
        iv_close = findViewById(R.id.iv_close);
        tv_doc_name = findViewById(R.id.tv_doc_name);
        tv_god_name = findViewById(R.id.tv_god_name);
        tv_mbs = findViewById(R.id.tv_mbs);
        tv_start = findViewById(R.id.tv_start);
        iv_back = findViewById(R.id.iv_back);
        tv_selected_pack = findViewById(R.id.tv_selected_pack);
        iv_pack_close = findViewById(R.id.imageView17);
        tv_prev_consult_date = findViewById(R.id.tv_prev_consult_date);
        cl_package_details = findViewById(R.id.cl_package_details);
        tv_next_consult_date = findViewById(R.id.tv_next_consult_date);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        layoutManager = new GridLayoutManager(this, 1);
        layoutManager2 = new GridLayoutManager(this, 1);
        rv_doctors.setHasFixedSize(true);
        rv_doctors.setLayoutManager(layoutManager);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_doc_details.setVisibility(View.GONE);
            }
        });

        progressDialog.setMessage("Loading");
        progressDialog.show();
        firebaseFirestore.collection("myDoctorsData").whereEqualTo("user_id", userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<MyDocData> types = queryDocumentSnapshots.toObjects(MyDocData.class);
                        docDataLists.addAll(types);
                        Log.d("TAG", "onSuccess: "  + types);
                        if (docDataLists.size() != 0) {
                            doctorListAdapter = new MyDoctorListAdapter(MySpecialistActivity.this, docDataLists.get(0).getDocDataLists(), new MyDoctorListAdapter.itemClickListner() {
                                @Override
                                public void onItemClick(int postion) {
                                    cl_doc_details.setVisibility(View.VISIBLE);

                                    tv_doc_name.setText(docDataLists.get(0).getDocDataLists().get(postion).getDocName());
                                    tv_god_name.setText(docDataLists.get(0).getDocDataLists().get(postion).getField());
                                    tv_mbs.setText(docDataLists.get(0).getDocDataLists().get(postion).getDesignation());
                                    tv_start.setText(String.valueOf(docDataLists.get(0).getDocDataLists().get(postion).getRating()));
                                    tv_selected_pack.setText(docDataLists.get(0).getDocDataLists().get(postion).getPackage_select());
                                    tv_prev_consult_date.setText(docDataLists.get(0).getDocDataLists().get(postion).getPast_consultation());

                                    if (docDataLists.get(0).getDocDataLists().get(postion).getNext_consultation().size() == 1) {
                                        show_val = docDataLists.get(0).getDocDataLists().get(postion).getNext_consultation().get(0);
                                    } else {
                                        for (int x = 0; x < docDataLists.get(0).getDocDataLists().get(postion).getNext_consultation().size(); x++) {
                                            show_val = show_val + "\n" + docDataLists.get(0).getDocDataLists().get(postion).getNext_consultation().get(x);
                                        }
                                    }

                                    tv_next_consult_date.setText(show_val);

                                }
                            });
                            rv_doctors.setAdapter(doctorListAdapter);
                        }
                        progressDialog.cancel();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d("TAG", "onFailure: " + e);
                progressDialog.cancel();
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

        tv_change_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePackageActivity.class);
                startActivity(intent);
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

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConsultDocListActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        tv_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cl_package_details.setVisibility(View.VISIBLE);
            }
        });

        iv_pack_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cl_package_details.setVisibility(View.GONE);
            }
        });

        cl_package_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cl_package_details.setVisibility(View.GONE);
            }
        });
    }
}