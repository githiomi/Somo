package com.githiomi.somo.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.githiomi.somo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    // Local variables
    // Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Init firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

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
            loginUser(v);
        }

        if (v == wToSignUp){
            signUpUser();
        }
    }

    // Method to login in user
    private void loginUser(View view) {

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

        // Sign in
        mFirebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ) {

                    Intent toDashboardActivity = new Intent(LoginActivity.this, DashboardActivity.class);
                    toDashboardActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(toDashboardActivity);
                    finish();

                }else {

                    // Hide progress bar & return button
                    wLoginProgressBar.setVisibility(View.GONE);
                    wLoginButton.setVisibility(View.VISIBLE);

                    Snackbar.make(view, "Incorrect email or password. Try again", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getResources().getColor(R.color.vote_logo_color))
                            .setTextColor(getResources().getColor(R.color.white))
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .setAction("Action", null).show();

                }
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

        FirebaseUser loggedInUser = mFirebaseAuth.getCurrentUser();

        // Send user to dashboard if logged in
        if (loggedInUser != null){
            Intent toDashboard = new Intent(this, DashboardActivity.class);
            toDashboard.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(toDashboard);
            finish();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Check if auth state listener exists and remove it
        if ( mAuthStateListener != null ){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

}