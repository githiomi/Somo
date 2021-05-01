package com.githiomi.somo.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.githiomi.somo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private static final String TAG = SignupActivity.class.getSimpleName();

    // Widgets
    @BindView(R.id.ivBackToLogin) ImageView wArrowBackToLogin;
    @BindView(R.id.edUsername) TextInputEditText wNewUsername;
    @BindView(R.id.edEmail) TextInputEditText wNewEmail;
    @BindView(R.id.edRoleSpinner) AutoCompleteTextView wRoleSpinner;
    @BindView(R.id.edPassword) TextInputEditText wNewPassword;
    @BindView(R.id.edConfirmPassword) TextInputEditText wNewConfirmPassword;
    @BindView(R.id.signUpButton) Button wSignUpButton;
    @BindView(R.id.signUpProgressBar) ProgressBar wSignProgressBar;
    @BindView(R.id.tvBackToLogin) TextView wTextBackToLogin;

    // Local variables
    // Firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    // Database
    private DatabaseReference firebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Firebase init
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        // Butter Knife init
        ButterKnife.bind(this);

        // On click listeners
        wArrowBackToLogin.setOnClickListener(this);
        wSignUpButton.setOnClickListener(this);
        wTextBackToLogin.setOnClickListener(this);

        // To make sure the spinner doesn't loose options
        String[] roles = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> rolesAdapter = new ArrayAdapter<>( this, R.layout.roles_spinner_item, roles);
        wRoleSpinner.setAdapter(rolesAdapter);

    }

    // Method to implement on click methods
    @Override
    public void onClick(View v) {

        // Send user back to login
        if ( v == wArrowBackToLogin || v == wTextBackToLogin ){
            backToLogin();
        }

        // Create new account
        if ( v == wSignUpButton ){
            createAccount(v);
        }
    }

    // Method to return user to login screen
    private void backToLogin(){
        onBackPressed();
    }

    // Method to create account for new user
    private void createAccount( View view){

        // Get user data
        String username = Objects.requireNonNull(wNewUsername.getText()).toString().trim();
        String email = Objects.requireNonNull(wNewEmail.getText()).toString().trim();
        String role = Objects.requireNonNull(wRoleSpinner.getText().toString().trim());
        String password = Objects.requireNonNull(wNewPassword.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(wNewConfirmPassword.getText()).toString().trim();

        // Validate new data
        boolean usernameValid = isUsernameValid(username);
        boolean emailValid = isEmailValid(email);
        boolean passwordsValid = isPasswordsValid(password, confirmPassword);

        // Assert they meet criteria
        if ( !(usernameValid) || !(emailValid) || !(passwordsValid) ) return;

        // Show progress bar
        wSignUpButton.setVisibility(View.GONE);
        wSignProgressBar.setVisibility(View.VISIBLE);

        // Show the role
        Toast.makeText(this, role, Toast.LENGTH_SHORT).show();

        // Activity
        Activity activity = this;

        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    // Add the username to the account created
                    FirebaseUser firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                    assert firebaseUser != null;
                    addUsernameToAccount(firebaseUser, username);

                    // With the user created, send their role to the database
                    if ( FirebaseAuth.getInstance().getCurrentUser() != null ) {

                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                        assert user != null;
                        String userName = user.getDisplayName();

                        assert userName != null;

                        firebaseDatabaseReference = FirebaseDatabase.getInstance()
                                .getReference("User Role")
                                .child(userName);

                        firebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                // Check if item is in recents
                                if ( !(snapshot.exists()) ){
                                    firebaseDatabaseReference.setValue(recentSearch);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    // Go to the search activity
                    Intent toDashboardActivity = new Intent( view.getContext(), DashboardActivity.class );
                    toDashboardActivity.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                    startActivity(toDashboardActivity, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                    finish();

                }else {

                    // Exchange button with progress bar
                    wSignProgressBar.setVisibility(View.GONE);
                    wSignUpButton.setVisibility(View.VISIBLE);

                    Snackbar.make( view,"Couldn't create your account. Try again.", Snackbar.LENGTH_SHORT )
                            .setBackgroundTint(getResources().getColor(R.color.vote_logo_color))
                            .setTextColor(getResources().getColor(R.color.white))
                            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                            .setAction("Action", null).show();

                }
            }
        });
    }

    // Method to add username to account
    private void addUsernameToAccount( FirebaseUser firebaseUser, String username ){

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest);
    }

    // Validation methods
    // Validate Username
    private boolean isUsernameValid( String username ){
        if ( username.isEmpty() ){
            wNewUsername.setError("This field cannot be left empty");
            return false;
        }
        return true;
    }

    // Validate Email
    private boolean isEmailValid( String email ){
        boolean emailGood = ( email != null &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() );

        if ( !(emailGood) ){
            wNewEmail.setError("Enter a valid email address");
            return false;
        }
        return true;
    }

    // Validate Passwords
    private boolean isPasswordsValid( String password, String confirmPassword ){
        if (password.isEmpty()) {
            String error = "This field cannot be left blank";
            wNewPassword.setError(error);
            return false;
        }

        if (confirmPassword.isEmpty()) {
            String error = "This field cannot be left blank";
            wNewConfirmPassword.setError(error);
            return false;
        }

        if (password.length() < 8 ) {
            String error = "Password must contain at least 8 characters";
            wNewPassword.setError(error);
            return false;
        }

        if ( confirmPassword.length() < 8 ){
            String error = "Password must contain at least 8 characters";
            wNewConfirmPassword.setError(error);
            return false;
        }

        if ( !password.equals(confirmPassword) ) {
            wNewPassword.setError("Passwords do not match");
            wNewConfirmPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // To make sure the spinner doesn't loose options
        String[] roles = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> rolesAdapter = new ArrayAdapter<>( this, R.layout.roles_spinner_item, roles);
        wRoleSpinner.setAdapter(rolesAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Check if auth state listeners exists and remove
        if ( mAuthStateListener != null ){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

}