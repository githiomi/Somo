package com.githiomi.somo.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.githiomi.somo.Fragments.AdminsFragment;
import com.githiomi.somo.Fragments.VotersFragment;
import com.githiomi.somo.R;
import com.githiomi.somo.Utils.Constants;

import butterknife.BindView;

public class SecondaryActivity extends AppCompatActivity {

    // TAG
    private static final String TAG = SecondaryActivity.class.getSimpleName();

    // Widgets
    @BindView(R.id.secondaryFrameLayout) FrameLayout wFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        // Get intent from dashboard
        Intent fromDashboard = getIntent();
        String selected = fromDashboard.getStringExtra(Constants.SECONDARY_INTENT_STRING);

        // Change frame layout depending on the selected
        if ( !(selected.isEmpty()) ) {
            changeLayout(selected);
        }
    }

    // Method to change the layout
    private void changeLayout( String userSelectedOption ){

        // Voters
        if ( userSelectedOption.equals("School President") ){
            VotersFragment schoolPresident = VotersFragment.newInstance(userSelectedOption);
            replaceView(schoolPresident);
        }

        if ( userSelectedOption.equals("Vice President") ){
            VotersFragment vicePresident = VotersFragment.newInstance(userSelectedOption);
            replaceView(vicePresident);
        }

        if ( userSelectedOption.equals("Treasurer") ){
            VotersFragment treasurer = VotersFragment.newInstance(userSelectedOption);
            replaceView(treasurer);
        }

        if ( userSelectedOption.equals("Secretary") ){
            VotersFragment secretary = VotersFragment.newInstance(userSelectedOption);
            replaceView(secretary);
        }

        if ( userSelectedOption.equals("Sports Secretary") ){
            VotersFragment sportsSecretary = VotersFragment.newInstance(userSelectedOption);
            replaceView(sportsSecretary);
        }

        if ( userSelectedOption.equals("ICT & Innovation Secretary") ){
            VotersFragment ictSecretary = VotersFragment.newInstance(userSelectedOption);
            replaceView(ictSecretary);
        }

        if ( userSelectedOption.equals("Education Secretary") ){
            VotersFragment educationSecretary = VotersFragment.newInstance(userSelectedOption);
            replaceView(educationSecretary);
        }

        if ( userSelectedOption.equals("Residential Life Representative") ){
            VotersFragment resLifeSecretary = VotersFragment.newInstance(userSelectedOption);
            replaceView(resLifeSecretary);
        }

        if ( userSelectedOption.equals("Wellness Secretary") ){
            VotersFragment wellnessSecretary = VotersFragment.newInstance(userSelectedOption);
            replaceView(wellnessSecretary);
        }

        if ( userSelectedOption.equals("Medical and Environmental Secretary") ){
            VotersFragment medicalSecretary = VotersFragment.newInstance(userSelectedOption);
            replaceView(medicalSecretary);
        }

        // Admins
        if ( userSelectedOption.equals("All Candidates") ){
            AdminsFragment allCandidates = AdminsFragment.newInstance(userSelectedOption);
            replaceView(allCandidates);
        }

        if ( userSelectedOption.equals("Positions Viable") ){
            AdminsFragment positionsViable = AdminsFragment.newInstance(userSelectedOption);
            replaceView(positionsViable);
        }

        if ( userSelectedOption.equals("Candidates Per Position") ){
            AdminsFragment candidatesPerPosition = AdminsFragment.newInstance(userSelectedOption);
            replaceView(candidatesPerPosition);
        }

        if ( userSelectedOption.equals("Specific Candidate") ){
            AdminsFragment specificCandidate = AdminsFragment.newInstance(userSelectedOption);
            replaceView(specificCandidate);
        }

        if ( userSelectedOption.equals("Votes") ){
            AdminsFragment votes = AdminsFragment.newInstance(userSelectedOption);
            replaceView(votes);
        }
    }

    // Method to replace the actual Frame Layout
    private void replaceView(Fragment fragment){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.secondaryFrameLayout, fragment);
        ft.commit();
    }
}