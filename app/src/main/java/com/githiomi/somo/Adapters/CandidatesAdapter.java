package com.githiomi.somo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.githiomi.somo.R;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CandidatesAdapter extends RecyclerView.Adapter<CandidatesAdapter.CandidatesAdapterVH> {

    // Local variables
    private String[] contestants;

    // Constructor
    public CandidatesAdapter(String[] contestantsFromFragment) {
        this.contestants = contestantsFromFragment;
    }

    @NonNull
    @NotNull
    @Override
    public CandidatesAdapterVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View contestantView = LayoutInflater.from(parent.getContext()).inflate(R.layout.voting_item, parent, false);

        return new CandidatesAdapterVH(contestantView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CandidatesAdapterVH holder, int position) {
        holder.bindToView(contestants[position]);
    }

    @Override
    public int getItemCount() {
        return contestants.length;
    }

    public static class CandidatesAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Widgets
        @BindView(R.id.contestantName) TextView wContestantsName;
        @BindView(R.id.addVoteButton) ImageButton wAddVoteButton;
        @BindView(R.id.votesCount) TextView wVotesCount;

        public CandidatesAdapterVH(@NonNull @NotNull View itemView) {
            super(itemView);

            // Binding Views
            ButterKnife.bind(this, itemView);

            // On click listener for the add vote button
            wAddVoteButton.setOnClickListener(this);
            wAddVoteButton.setOnClickListener(this);
        }

        // Method to bind data to view
        private void bindToView(String contestant) {
            wContestantsName.setText(contestant);
        }

        // Methods to assign click function
        @Override
        public void onClick(View v) {

            if (v == wAddVoteButton) {
                Toast.makeText(v.getContext(), "Vote Added", Toast.LENGTH_SHORT).show();
                int votes = Integer.parseInt(wVotesCount.getText().toString());
                int newVotes = votes + 1;

                wVotesCount.setText(String.valueOf(newVotes));
            }
        }
    }
}
