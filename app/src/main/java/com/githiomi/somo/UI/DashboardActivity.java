package com.githiomi.somo.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.githiomi.somo.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private static final String TAG = DashboardActivity.class.getSimpleName();

    // Widgets
    @BindView(R.id.ivOpenDrawer) ImageView wOpenDrawer;
    @BindView(R.id.userProfilePicture) ImageView wUserProfilePicture;

    // Local variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Butter knife init
        ButterKnife.bind(this);

        // Firebase init
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        // Setting on click listeners
        wOpenDrawer.setOnClickListener(this);
        wUserProfilePicture.setOnClickListener(this);

    }

    // Method to implement the click listener
    @Override
    public void onClick(View v) {

        // Temp method to log user out
        if ( v == wUserProfilePicture ){
            logout();
        }

        // Method to open the side drawer
        if ( v == wOpenDrawer ){
            openDrawer(v);
        }

    }

    // Method to log out the user
    private void logout(){

        // Check if user is logged in
        if ( mFirebaseAuth.getCurrentUser() != null ){
            mFirebaseAuth.signOut();
        }

        // Send user back to login page
        Intent toLoginPage = new Intent(this, LoginActivity.class);
        toLoginPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toLoginPage, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    // Method to open drawer after image is clicked
    private void openDrawer(View view){
        Snackbar.make(view, "Drawer will open when created", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.vote_logo_color))
                .setTextColor(getResources().getColor(R.color.white))
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setAction("Action", null).show();
    }

    // Methods for the drop down on profile picture
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Add auth state listener
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
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