package com.fyp.vishmi.skinlab.ui.registerlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email;
    private EditText et_password;
    private TextView tv_forgot;
    private Button btn_login;
    private ConstraintLayout cl_main;
    private ImageView iv_back;

    private String email;
    private String pass;
    String userID = "";
    String type = "";

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        cl_main = findViewById(R.id.cl_main);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        tv_forgot = findViewById(R.id.tv_forgot);
        btn_login = findViewById(R.id.btn_login);
        iv_back = findViewById(R.id.iv_back);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = et_email.getText().toString().trim();
                pass = et_password.getText().toString().trim();

                if (!email.isEmpty() || !pass.isEmpty()){

                    showProgressDialog("Login...");
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            hideProgressDialog();

                            showSnackMSG(cl_main,"Login Success...");
                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hideProgressDialog();
                            showSnackMSG(cl_main, "Login Failed...");
                        }
                    });
                }
                else {
                    showSnackMSG(cl_main, "Required fields are empty...");
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginSlectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showSnackMSG(ConstraintLayout cv_main, String msg) {

        Snackbar snackbar = Snackbar.make(cv_main, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    //Progress Bar
    public void showProgressDialog(String message) {

        if (null != progressDialog) {

            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    //Hide Progress Bar
    public void hideProgressDialog() {

        if (null != progressDialog)
            progressDialog.dismiss();
    }
}