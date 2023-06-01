package com.fyp.vishmi.skinlab.ui.doctor.docdashboard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.vishmi.skinlab.AppointmentActivity;
import com.fyp.vishmi.skinlab.ManagePatientActivity;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.doctor.patientrequest.PatientRequestActivity;
import com.fyp.vishmi.skinlab.ui.registerlogin.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DoctorDashboardActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    private TextView tv_user_name;
    private ConstraintLayout cl_patient_request;
    private ImageView iv_setting;
    private ImageView iv_manage_pait;
    private ImageView iv_manage_appoint;

    String userID = "";
    String ref_id = "";
    String doc_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        tv_user_name = findViewById(R.id.tv_user_name);
        cl_patient_request = findViewById(R.id.cl_patient_request);
        iv_setting = findViewById(R.id.iv_setting);
        iv_manage_pait = findViewById(R.id.imageView8);
        iv_manage_appoint = findViewById(R.id.imageView6);

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
                ref_id = documentSnapshot.getString("id");
                doc_name = documentSnapshot.getString("fName");
            }
        });

        cl_patient_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientRequestActivity.class);
                intent.putExtra("ref_id", ref_id);
                intent.putExtra("doc_name", doc_name);
                startActivity(intent);
                finish();
            }
        });

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorDashboardActivity.this);

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

        iv_manage_pait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagePatientActivity.class);
                startActivity(intent);
                finish();
            }
        });

        iv_manage_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppointmentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}