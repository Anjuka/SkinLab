package com.fyp.vishmi.skinlab.ui.routing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.ui.routing.QuestionerActivity;
import com.fyp.vishmi.skinlab.ui.routing.RoutingActivity;

public class CreatingRoutingLoadingActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 4000;
    Button btn_cancel;
    ImageView imageView15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_routing_loading);

        imageView15 = findViewById(R.id.imageView15);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), RoutingActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onBackPressed() {
// super.onBackPressed();
// Not calling **super**, disables back button in current screen.
    }
}