package com.githiomi.somo.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.githiomi.somo.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

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
    @BindView(R.id.loginProgressBar) ProgressBar wLoginProgressBar;

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

        // Get data from input fields
        String userEmail = Objects.requireNonNull(wUserEmail.getText()).toString().trim();
        String userPassword = Objects.requireNonNull(wUserPassword.getText()).toString().trim();

        // Data validation
        boolean emailValid = emailValidity(userEmail);
        boolean passwordValid = passwordValidity(userPassword);

        if ( !(emailValid) || !(passwordValid) ) return;

        // Show progress bar
        wLoginButton.setVisibility(View.GONE);
        wLoginProgressBar.setVisibility(View.VISIBLE);

    }

    // Input data validation
    // Email
    private boolean emailValidity(String email) {

        if (email.isEmpty()) {
            String error = "This field must not be left blank";
            wUserEmail.setError(error);
            return false;
        } else {

            boolean isEmailGood = Patterns.EMAIL_ADDRESS.matcher(email).matches();

            if (!(isEmailGood)) {
                wUserEmail.setError("Email Address is not valid. Try again");
                return false;
            }
        }

        return true;
    }

    // For password
    private boolean passwordValidity(String password) {

        if (password.isEmpty()) {
            String error = "This field must not be left blank";
            wUserPassword.setError(error);
            return false;
        } else {
            if (password.length() < 8) {
                wUserPassword.setError("Minimum of 8 characters required");
                return false;
            }
        }

        return true;
    }

    // Method to open sign up activity
    private void signUpUser() {
        Intent toSignUp = new Intent(this, SignupActivity.class);
        startActivity(toSignUp, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}