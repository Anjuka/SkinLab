package com.fyp.vishmi.skinlab.ui.registerlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.fyp.vishmi.skinlab.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterAsSpecialistActivity extends AppCompatActivity {

    private EditText et_full_name;
    private EditText et_email;
    private EditText et_dob;
    private EditText et_c_number;
    private EditText et_reg_num;
    private EditText et_av_hospital;
    private EditText et_pw;
    private CheckBox cb_terms;
    private Button btn_reg;
    private ConstraintLayout cl_main;
    private ImageView iv_back;

    private String fullName;
    private String email;
    private String pw;
    private String dob;
    private String contact_num;
    private String reg_number;
    private String hospital;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_specialist);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        et_full_name = findViewById(R.id.et_full_name);
        et_email = findViewById(R.id.et_email);
        et_dob = findViewById(R.id.et_dob);
        et_pw = findViewById(R.id.et_pw);
        et_c_number = findViewById(R.id.et_c_number);
        et_reg_num = findViewById(R.id.et_reg_num);
        et_av_hospital = findViewById(R.id.et_av_hospital);
        btn_reg = findViewById(R.id.btn_reg);
        cl_main = findViewById(R.id.cl_main);
        iv_back = findViewById(R.id.iv_back);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                String myFormat="MM/dd/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);

                et_dob.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterAsSpecialistActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fullName = et_full_name.getText().toString().trim();
                email = et_email.getText().toString().trim();
                pw = et_pw.getText().toString().trim();
                dob = et_dob.getText().toString().trim();
                contact_num = et_c_number.getText().toString().trim();
                reg_number = et_reg_num.getText().toString().trim();
                hospital = et_av_hospital.getText().toString().trim();

                if (!fullName.isEmpty() && !email.isEmpty() || !dob.isEmpty() || !contact_num.isEmpty() || !reg_number.isEmpty() || !hospital.isEmpty()) {

                    //specialist registration

                    showProgressDialog("Registering...");

                    firebaseAuth.createUserWithEmailAndPassword(email, pw).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            hideProgressDialog();
                            showSnackMSG(cl_main, "User Account is created...");

                            Map<String, Object> user = new HashMap<>();
                            user.put("userId", firebaseAuth.getCurrentUser().getUid());
                            user.put("fName", fullName);
                            user.put("dob", dob);
                            user.put("contact", contact_num);
                            user.put("reg_num", reg_number);
                            user.put("hospital", hospital);
                            user.put("type", "doc");

                            DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Intent intent = new Intent(getApplicationContext(), SpecialistConfrimationActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Log.d("TAG", "onFailure: " + e.getMessage());
                                    showSnackMSG(cl_main, "User Account is not created...");
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hideProgressDialog();
                            showSnackMSG(cl_main, e.getMessage());
                        }
                    });

                } else {

                    showSnackMSG(cl_main, "Required fields are empty...");
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrationSelectActivity.class);
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