package com.githiomi.somo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.githiomi.somo.R;
import com.githiomi.somo.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminsFragment extends Fragment {

    // Widgets
    @BindView(R.id.adminsTextView) TextView textView;

    // Local variables
    private String adminsOption;

    public AdminsFragment() {
        // Required empty public constructor
    }

    public static AdminsFragment newInstance(String adminOption) {
        AdminsFragment fragment = new AdminsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ADMIN_SELECTION_KEY, adminOption);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            adminsOption = getArguments().getString(Constants.ADMIN_SELECTION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View votingView = inflater.inflate(R.layout.fragment_admins, container, false);

        // Binding widgets
        ButterKnife.bind(this, votingView);

        textView.setText(adminsOption);

        return votingView;
    }
}