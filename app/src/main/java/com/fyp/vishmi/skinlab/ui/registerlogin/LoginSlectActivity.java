package com.fyp.vishmi.skinlab.ui.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fyp.vishmi.skinlab.DocLoginActivity;
import com.fyp.vishmi.skinlab.R;

public class LoginSlectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_log_as_user;
    private Button btn_log_as_specialist;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_slect);

        btn_log_as_user = findViewById(R.id.btn_log_as_user);
        btn_log_as_specialist = findViewById(R.id.btn_log_as_specialist);
        iv_back = findViewById(R.id.iv_back);

        btn_log_as_user.setOnClickListener(this);
        btn_log_as_specialist.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.btn_log_as_user:
                Intent intentUser = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentUser);
                finish();
                break;

            case R.id.btn_log_as_specialist:
                Intent intentSpecialist = new Intent(getApplicationContext(), DocLoginActivity.class);
                startActivity(intentSpecialist);
                finish();
                break;

            case R.id.iv_back:
                Intent intentBack = new Intent(getApplicationContext(), SplashActivity.class);
                startActivity(intentBack);
                finish();
                break;
        }
    }
}