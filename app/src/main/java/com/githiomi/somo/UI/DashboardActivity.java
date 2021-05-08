package com.githiomi.somo.UI;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.githiomi.somo.Adapters.DashboardAdapter;
import com.githiomi.somo.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private static final String TAG = DashboardActivity.class.getSimpleName();

    // Widgets
    @BindView(R.id.ivOpenDrawer) ImageView wOpenDrawer;
    @BindView(R.id.dashboardHeader) TextView wDashboardHeader;
    @BindView(R.id.userProfilePicture) ImageView wUserProfilePicture;
    @BindView(R.id.dashboardProgressBar) ProgressBar wDashboardProgressBar;
    @BindView(R.id.cvInstructions) CardView wInstructions;
    @BindView(R.id.tvInstructions) TextView wInstructionsText;
    @BindView(R.id.dashboardRecyclerView) RecyclerView wDashboardRecyclerView;
    @BindView(R.id.tvErrorMessage) TextView wErrorMessage;

    // Local variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    // Database
    private DatabaseReference databaseReference;
    // Dashboard strings
    private String[] dashboardStrings;

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

                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    // Get username and display it
                    String username = currentUser.getDisplayName();

                    // Get the role
                    // Database reference init
                    databaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference("Users");

                    assert username != null;
                    Query getRoleQuery = databaseReference.orderByChild("username").equalTo(username);

                    getRoleQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Check if data exists
                            if ( snapshot.exists() ){
                                String role = snapshot.child(username).child("role").getValue(String.class);

                                // Final header text
                                String header = role + ": " + username;
                                wDashboardHeader.setText(header);

                                // Show Instructions
                                wInstructions.setVisibility(View.VISIBLE);
                                wInstructionsText.setVisibility(View.VISIBLE);

                                // Change dashboard depending on the role
                                assert role != null;
                                if ( role.equals("An Admin") ){
                                    dashboardStrings = getResources().getStringArray(R.array.admin_dashboard);
                                    String adminInstructions = "Control the voting process from here:";
                                    wInstructionsText.setText(adminInstructions);
                                }

                                if ( role.equals("A Voter") ){
                                    dashboardStrings = getResources().getStringArray(R.array.voter_dashboard);
                                    String voterInstructions = "Vote from here:";
                                    wInstructionsText.setText(voterInstructions);
                                }

                                // Hide progress bar and show recycler view
                                wDashboardProgressBar.setVisibility(View.GONE);
                                wDashboardRecyclerView.setVisibility(View.VISIBLE);

                                // Pass the string array to an adapter
                                passToAdapter(dashboardStrings);

                            }else {

                                // Hide progress bar and show error
                                wDashboardProgressBar.setVisibility(View.GONE);
                                wErrorMessage.setVisibility(View.VISIBLE);

                                String header = "Welcome: " + username;

                                wDashboardHeader.setText(header);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Hide progress bar and show error
                            wDashboardProgressBar.setVisibility(View.GONE);
                            wErrorMessage.setVisibility(View.VISIBLE);
                        }
                    });

                    // Get profile picture and display it
                    Uri userUri = currentUser.getPhotoUrl();

                    if (userUri != null) {
                        Picasso.get()
                                .load(userUri)
                                .placeholder(R.drawable.profile_picture_placeholder)
                                .error(R.drawable.profile_picture_placeholder)
                                .into(wUserProfilePicture);
                    } else {
                        Picasso.get()
                                .load(R.drawable.profile_picture_placeholder)
                                .into(wUserProfilePicture);
                    }
                }
            }
        };

        // Setting on click listeners
        wOpenDrawer.setOnClickListener(this);
        wUserProfilePicture.setOnClickListener(this);

    }

    // Method to place the string array into a recycler view adapter
    private void passToAdapter(String[] dashboardStringArray){

        // Init the adapter
        DashboardAdapter dashboardStringAdapter = new DashboardAdapter(dashboardStringArray, this, this);

        wDashboardRecyclerView.setLayoutManager( new LinearLayoutManager(this) );
        wDashboardRecyclerView.setAdapter(dashboardStringAdapter);

        wDashboardRecyclerView.setHasFixedSize(true);

    }

    // Method to implement the click listener
    @Override
    public void onClick(View v) {

        // Temp method to log user out
        if (v == wUserProfilePicture) {
            logout();
        }

        // Method to open the side drawer
        if (v == wOpenDrawer) {
            openDrawer(v);
        }

    }

    // Method to log out the user
    private void logout() {

        // Check if user is logged in
        if (mFirebaseAuth.getCurrentUser() != null) {
            mFirebaseAuth.signOut();
        }

        // Send user back to login page
        Intent toLoginPage = new Intent(this, LoginActivity.class);
        toLoginPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toLoginPage, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }

    // Method to open drawer after image is clicked
    private void openDrawer(View view) {
        Snackbar.make(view, "Drawer will open when created", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.vote_logo_color))
                .setTextColor(getResources().getColor(R.color.white))
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setAction("Action", null).show();
    }

    // Methods for the drop down on profile picture
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_profile_picture, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int option = item.getItemId();

        if (option == R.id.toProfileNav) {
            Toast.makeText(this, "To profile", Toast.LENGTH_SHORT).show();
        }

        if (option == R.id.toLogoutNav) {
            logout();
        }

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
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

}