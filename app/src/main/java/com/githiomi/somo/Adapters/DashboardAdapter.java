package com.githiomi.somo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.githiomi.somo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    // Local variables
    private final String[] dashboardStrings;
    private final Context context;

    // Adapter constructor
    public DashboardAdapter(String[] adapterStrings, Context contextFromActivity) {
        this.dashboardStrings = adapterStrings;
        this.context = contextFromActivity;
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
        private void bindToView(String dashboardString){
            dashboardTextItem.setText(dashboardString);
        }

        // Method to assign on click functions
        @Override
        public void onClick(View v) {
            int clicked = getAdapterPosition();

            if ( v == itemView ){
                String clickedItem = dashboardStrings[clicked];

                Toast.makeText(context, clickedItem, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
