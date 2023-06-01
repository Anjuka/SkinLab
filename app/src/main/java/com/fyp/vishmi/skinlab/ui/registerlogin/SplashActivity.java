package com.fyp.vishmi.skinlab.ui.registerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fyp.vishmi.skinlab.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_log_in;
    private Button btn_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btn_log_in = findViewById(R.id.btn_log_in);
        btn_reg = findViewById(R.id.btn_reg);

        btn_log_in.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id  = view.getId();

        switch (id){
            case R.id.btn_log_in:

                Intent intentLogin = new Intent(getApplicationContext(), LoginSlectActivity.class);
                startActivity(intentLogin);
                finish();
                break;

            case R.id.btn_reg:
                Intent intentReg = new Intent(getApplicationContext(), RegistrationSelectActivity.class);
                startActivity(intentReg);
                finish();
                break;
        }
    }
}