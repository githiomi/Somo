package com.githiomi.somo.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.githiomi.somo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    // Local variables
    private final String[] dashboardStrings;

    // Adapter constructor
    public DashboardAdapter( String[] dashboardStrings) {
        this.dashboardStrings = dashboardStrings;
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

    public static class DashboardViewHolder extends RecyclerView.ViewHolder {

        // Text view widget
        @BindView(R.id.dashboardItemText) TextView dashboardTextItem;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind the widgets
            ButterKnife.bind(this, itemView);

        }

        // Method to bind text to view
        private void bindToView(String dashboardString){
            dashboardTextItem.setText(dashboardString);
        }

    }
}
