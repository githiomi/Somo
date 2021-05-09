package com.githiomi.somo.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.githiomi.somo.R;
import com.githiomi.somo.UI.SecondaryActivity;
import com.githiomi.somo.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    // Local variables
    private final String[] dashboardStrings;
    private final Context context;
    private final Activity activity;

    // Adapter constructor
    public DashboardAdapter(String[] adapterStrings, Context contextFromActivity, Activity activityFromDashboard) {
        this.dashboardStrings = adapterStrings;
        this.context = contextFromActivity;
        this.activity = activityFromDashboard;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create the layout
        View dashboardItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_item, parent, false);
        return new DashboardViewHolder(dashboardItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        // Get the specific string and bind it to the text view
        holder.bindToView(dashboardStrings[position]);
    }

    @Override
    public int getItemCount() {
        return dashboardStrings.length;
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Text view widget
        @BindView(R.id.dashboardItemText) TextView dashboardTextItem;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind the widgets
            ButterKnife.bind(this, itemView);

            // On click listener
            itemView.setOnClickListener(this);

        }

        // Method to bind text to view
        private void bindToView(String dashboardString) {
            dashboardTextItem.setText(dashboardString);
        }

        // Method to assign on click functions
        @Override
        public void onClick(View v) {
            int clicked = getAbsoluteAdapterPosition();
            String clickedItem = dashboardStrings[clicked];

            Intent toSecondaryActivity = new Intent(context, SecondaryActivity.class);
            toSecondaryActivity.putExtra(Constants.SECONDARY_INTENT_STRING, clickedItem);
            context.startActivity(toSecondaryActivity, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }
    }
}
