package com.githiomi.somo.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.githiomi.somo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        // To send user to next activity to select role
        selectRole();
    }

    // Method to take user to select role activity
    private void selectRole() {

        Handler progressBarHandler = new Handler();

        progressBarHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // To open new activity
                startSelectRole();
            }
        }, 2500);
    }

    // Method to take user to the select role activity
    private void startSelectRole() {

        Intent toSelectRole = new Intent(this, LoginActivity.class);
        toSelectRole.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toSelectRole);
        finish();

    }
}