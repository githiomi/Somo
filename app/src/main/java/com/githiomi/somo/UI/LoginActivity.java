package com.githiomi.somo.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.githiomi.somo.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private static final String TAG = LoginActivity.class.getSimpleName();

    // Widgets
    @BindView(R.id.edEmail) TextInputEditText wUserEmail;
    @BindView(R.id.edPassword) TextInputEditText wUserPassword;
    @BindView(R.id.btnLogin) Button wLoginButton;
    @BindView(R.id.tvToSignUp) TextView wToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Init Butter Knife
        ButterKnife.bind(this);

        // Setting in click listeners
        wLoginButton.setOnClickListener(this);
        wToSignUp.setOnClickListener(this);
    }

    // On click methods
    @Override
    public void onClick(View v) {

        if (v == wLoginButton){
            loginUser();
        }

        if (v == wToSignUp){
            signUpUser();
        }
    }

    // Method to login in user
    private void loginUser() {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    // Method to open sign up activity
    private void signUpUser() {
        Intent toSignUp = new Intent(this, SignupActivity.class);
        startActivity(toSignUp, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}