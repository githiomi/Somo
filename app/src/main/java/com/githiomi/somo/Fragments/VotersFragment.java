package com.githiomi.somo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.githiomi.somo.Adapters.CandidatesAdapter;
import com.githiomi.somo.R;
import com.githiomi.somo.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VotersFragment extends Fragment {

    // Widgets
    @BindView(R.id.votersTextView) TextView textView;
    @BindView(R.id.votingItemsRecyclerView) RecyclerView wVotingRecyclerView;

    // Local variables
    private String votersOption;

    public VotersFragment() {
        // Required empty public constructor
    }

    public static VotersFragment newInstance(String votingOption) {
        VotersFragment fragment = new VotersFragment();
        Bundle args = new Bundle();
        args.putString(Constants.VOTER_SELECTION_KEY, votingOption);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            votersOption = getArguments().getString(Constants.VOTER_SELECTION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View votingView = inflater.inflate(R.layout.fragment_voters, container, false);

        // Binding widgets
        ButterKnife.bind(this, votingView);

        textView.setText(votersOption);

        // Get the relevant strings for the chosen position
        getCandidates(votersOption);

        return votingView;
    }

    // Method to get contestant names from the strings file
    private void getCandidates(String candidatesPost){

        // Check what candidates we are looking for
        String[] contestants = {};

        if ( candidatesPost.equals("") ){
            Toast.makeText(getContext(), "No Contestants", Toast.LENGTH_SHORT).show();
        }

        if ( candidatesPost.equals("School President") ){
            contestants = getResources().getStringArray(R.array.school_president);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Vice President") ){
            contestants = getResources().getStringArray(R.array.vice_president);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Treasurer") ){
            contestants = getResources().getStringArray(R.array.treasurer);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Secretary") ){
            contestants = getResources().getStringArray(R.array.secretary);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Sports Secretary") ){
            contestants = getResources().getStringArray(R.array.sports_president);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("ICT & Innovation Secretary") ){
            contestants = getResources().getStringArray(R.array.ict_and_innovation);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Education Secretary") ){
            contestants = getResources().getStringArray(R.array.education_secretary);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Residential Life Representative") ){
            contestants = getResources().getStringArray(R.array.resLifeSecretary);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Wellness Secretary") ){
            contestants = getResources().getStringArray(R.array.wellness_secretary);
            passToAdapter(contestants);
        }

        if ( candidatesPost.equals("Medical and Environmental Secretary") ){
            contestants = getResources().getStringArray(R.array.medSecretary);
            passToAdapter(contestants);
        }
    }

    // Once we have the contestants, we pass them on to an adapter
    private void passToAdapter(String[] positionContestants){

//        CandidatesAdapter candidatesAdapter = new CandidatesAdapter(getContext(), positionContestants);
//
//        // Set the adapter
//        wVotingRecyclerView.setAdapter(candidatesAdapter);
//        wVotingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        wVotingRecyclerView.setHasFixedSize(true);

    }
}