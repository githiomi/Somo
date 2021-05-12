package com.githiomi.somo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.githiomi.somo.Adapters.CandidatesAdapter;
import com.githiomi.somo.R;
import com.githiomi.somo.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VotersFragment extends Fragment {

    // Widgets
    @BindView(R.id.tvCandidatesHeader) TextView wCandidatesHeader;
    @BindView(R.id.votersRecyclerView) RecyclerView wVotersRecyclerView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View votingView = inflater.inflate(R.layout.fragment_voters, container, false);

        // Binding widgets
        ButterKnife.bind(this, votingView);

        // Set header
        wCandidatesHeader.setText(votersOption);

        // Get data according to header
        getData(votersOption);

        return votingView;

    }

    // Method to get string data based on option selected
    private void getData(String option){

        assert option != null;
        if ( option.equals("School President") ){
            String[] candidates = getResources().getStringArray(R.array.school_president);
            passToAdapter(candidates);
        }
    }

    // Method to get data to the adapter
    private void passToAdapter(String[] candidatesGotten){

        CandidatesAdapter candidatesAdapter = new CandidatesAdapter(candidatesGotten);

        wVotersRecyclerView.setAdapter(candidatesAdapter);
        wVotersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wVotersRecyclerView.setHasFixedSize(true);

    }

}