package com.fyp.vishmi.skinlab.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.vishmi.skinlab.model.AvailableHospital;
import com.fyp.vishmi.skinlab.model.DocData;
import com.fyp.vishmi.skinlab.model.DocDataList;
import com.fyp.vishmi.skinlab.model.MyDocData;
import com.fyp.vishmi.skinlab.model.MyDocDataList;
import com.fyp.vishmi.skinlab.model.RequestList;
import com.fyp.vishmi.skinlab.model.RoutingData;
import com.fyp.vishmi.skinlab.ui.diary.DiaryActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.routing.RoutingActivity;
import com.fyp.vishmi.skinlab.ui.scan.CameraActivity;
import com.fyp.vishmi.skinlab.ui.consult.ConsultDocListActivity;
import com.fyp.vishmi.skinlab.ui.routing.QuestionerActivity;
import com.fyp.vishmi.skinlab.ui.shelf.ShelfActivity;
import com.fyp.vishmi.skinlab.ui.registerlogin.SplashActivity;
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

public class DashboardActivity extends AppCompatActivity {

    private TextView tv_user_name;
    private TextView tv_pop_text;
    private ImageView btn_logout;
    private ImageView iv_next;
    private ConstraintLayout cl_pop;
    private ConstraintLayout cl_scan;
    private ConstraintLayout cl_turmeric;
    private Button btn_mang_my_routing;
    private Button btn_anl_my_skin;
    private ConstraintLayout cl_shelf;
    private ConstraintLayout cl_diary;
    private ConstraintLayout cl_consult;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    String userID = "";

    ArrayList<String> pop_txt = new ArrayList<>();
    private int selectPosition =0;
    private boolean isRoutingHave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tv_user_name = findViewById(R.id.tv_user_name);
        btn_logout = findViewById(R.id.iv_setting);
        cl_pop = findViewById(R.id.cl_pop);
        cl_scan = findViewById(R.id.cl_scan);
        cl_turmeric = findViewById(R.id.cl_turmeric);
        btn_mang_my_routing = findViewById(R.id.btn_mang_my_routing);
        cl_shelf = findViewById(R.id.cl_shelf);
        cl_diary = findViewById(R.id.cl_diary);
        cl_consult = findViewById(R.id.cl_consult);
        tv_pop_text = findViewById(R.id.tv_pop_text);
        iv_next = findViewById(R.id.iv_next);
        btn_anl_my_skin = findViewById(R.id.btn_anl_my_skin);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser().getUid() != null) {
            userID = firebaseAuth.getCurrentUser().getUid();
        }

        pop_txt.add("Turmeric benefits the skin by \nprotecting it from sun damage.");
        pop_txt.add("Turmeric acts as an \nanti-inflammatory property \nwhich helps in healing acne");

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                tv_user_name.setText("Hello " + documentSnapshot.getString("fName") + "!");
            }
        });

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

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectPosition == 0){
                    tv_pop_text.setText(pop_txt.get(0));
                    selectPosition = 1;
                }
                else {
                    tv_pop_text.setText(pop_txt.get(1));
                    selectPosition = 0;
                }
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

        btn_anl_my_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*ArrayList<AvailableHospital> availableHospital = new ArrayList<>();
        ArrayList<RequestList> requestLists = new ArrayList<>();
        availableHospital.add(new AvailableHospital("Asiri Hospital", "No 2, Narahenpita", "011786554"));
        availableHospital.add(new AvailableHospital("Nawaloka Hospital", "No 32, Colombo", "011799084"));
        availableHospital.add(new AvailableHospital("Odex Hospital", "No 12, Kotte", "011782254"));
        DocDataList docDataList = new DocDataList("Dr.Jhon Davis", "Skin Care", "MBBS Rg 224", 5, availableHospital, requestLists);
        DocDataList docDataList2 = new DocDataList("Dr.Hert E", "Skin Consult", "MBBS Rg 67", 5, availableHospital, requestLists);
        DocDataList docDataList3 = new DocDataList("Dr.Mika Loli", "Skin Care", "MBBS Rg 204", 5, availableHospital, requestLists);

        ArrayList<DocDataList> docDataLists = new ArrayList<>();
        docDataLists.add(docDataList);
        docDataLists.add(docDataList2);
        docDataLists.add(docDataList3);


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
        });*/

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);

                builder.setTitle("Logout...");
                builder.setMessage("Are you sure, you need to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        firebaseAuth.signOut();
                        Intent logout = new Intent(getApplicationContext(), SplashActivity.class);
                        startActivity(logout);
                        finish();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btn_mang_my_routing.setOnClickListener(new View.OnClickListener() {
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

        cl_turmeric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_pop.setVisibility(View.VISIBLE);
            }
        });

        cl_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl_pop.setVisibility(View.GONE);
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

        cl_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intentDiary = new Intent(getApplicationContext(), DiaryActivity.class);
               startActivity(intentDiary);
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

    }
}