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

    // Local Variables
    private Context context;
    private String[] contestantsForPost;

    // Constructor
    public CandidatesAdapter(Context contextFromFragment, String[] contestants) {
        this.context = contextFromFragment;
        this.contestantsForPost = contestants;
    }

    @NotNull
    @Override
    public CandidatesAdapterVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View contestantsItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.voting_item, parent, false);
        return new CandidatesAdapterVH(contestantsItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CandidatesAdapterVH holder, int position) {
        // Bind data to view
        holder.bindToView(contestantsForPost[position]);
    }

    @Override
    public int getItemCount() {
        return contestantsForPost.length;
    }

    public class CandidatesAdapterVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Widgets
        @BindView(R.id.contestantName) TextView wContestantsName;
        @BindView(R.id.addVoteButton) ImageButton wAddVoteButton;

        public CandidatesAdapterVH(@NonNull @NotNull View itemView) {
            super(itemView);

            // Bind widgets
            ButterKnife.bind(this, itemView);

            // On Click listener for the add vote button
            wAddVoteButton.setOnClickListener(this);
        }

        // Method to bind data to view
        private void bindToView(String contestantName){
            wContestantsName.setText(contestantName);
        }

        // Method when the add button is clicked
        @Override
        public void onClick(View v) {

            int clicked = getAbsoluteAdapterPosition();

            if ( v == wAddVoteButton ){
                String toast = "Vote added for: " + contestantsForPost[clicked];
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
