package com.fyp.vishmi.skinlab.ui.splash;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.doctor.docdashboard.DoctorDashboardActivity;
import com.fyp.vishmi.skinlab.ui.registerlogin.SplashActivity;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class IntroActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 4000;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getUid());
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    type = documentSnapshot.getString("type");

                    if (type != null) {
                        if (type.equals("doc")) {
                            openDocDashboard();
                        } else {
                            openUserDashboard();
                        }
                    } else {
                        openSplashView();
                    }
                }
            });
        }else {
            openSplashView();
        }

    }

    private void openSplashView() {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent loginIntent = new Intent(IntroActivity.this, SplashActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void openUserDashboard() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
        Intent mainIntent = new Intent(IntroActivity.this, DashboardActivity.class);
        startActivity(mainIntent);
        finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void openDocDashboard() {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = new Intent(IntroActivity.this, DoctorDashboardActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private String checkUseer(String uid) {
        String typer = "";
        DocumentReference documentReference = firebaseFirestore.collection("users").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                //typer = documentSnapshot.getString("type");
            }
        });

        return typer;
    }

}