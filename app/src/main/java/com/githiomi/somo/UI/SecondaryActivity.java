package com.githiomi.somo.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

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
    }

    // Method to replace the actual Frame Layout
    private void replaceView(Fragment fragment){
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.secondaryFrameLayout, fragment);
        ft.commit();
    }
}