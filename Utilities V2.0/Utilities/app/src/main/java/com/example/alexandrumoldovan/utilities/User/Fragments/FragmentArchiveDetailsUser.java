package com.example.alexandrumoldovan.utilities.User.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Report;
import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.User.Activities.ActivityUser;

import java.util.ArrayList;
import java.util.List;

public class FragmentArchiveDetailsUser extends Fragment {
    private String value;
    private List<Report> myReports;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        value = getArguments().getString("Month");
        ((ActivityUser) getActivity()).setActionBarTitle(value);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myReports = getMyReports();
        if (myReports.size() > 0) {
            View historyInfo = inflater.inflate(R.layout.layout_archive_information_user, container, false);
            ListView listView = historyInfo.findViewById(R.id.historyListView);
            CustomAdapter customAdapter = new CustomAdapter() {
                @Override
                public boolean isEnabled(int position) {
                    return false;
                }
            };
            listView.setAdapter(customAdapter);
            return historyInfo;
        } else {
            View historyInfo = inflater.inflate(R.layout.layout_empty_archive, container, false);
            return historyInfo;
        }
    }

    private List<Report> getMyReports() {
        List<Report> aux = new ArrayList<>();
        for (Report report : ActivityLogIn.reports) {
            if (report.getUser().equals(ActivityLogIn.user.getID())
                    && report.getMonth().equals(value)) {
                aux.add(report);
            }
        }
        return aux;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return myReports.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View viewEvent = layoutInflater.inflate(R.layout.custom_archive_resources_row_user, null);
            TextView date = viewEvent.findViewById(R.id.dateTV);
            date.setText(myReports.get(position).getDate());
            TextView utility = viewEvent.findViewById(R.id.utilityTypeTV);
            utility.setText(myReports.get(position).getUtility());
            TextView quantity = viewEvent.findViewById(R.id.quantityTV);
            quantity.setText(String.valueOf(myReports.get(position).getQuantity()));
            if (utility.getText().equals("People")) {
                TextView meter = viewEvent.findViewById(R.id.meterTV);
                meter.setVisibility(View.GONE);
                TextView consumption = viewEvent.findViewById(R.id.consumptionTV);
                consumption.setText("People:");
            } else if (utility.getText().equals("Water")) {
                TextView meter = viewEvent.findViewById(R.id.meterTV);
                meter.setText(R.string.cubicMetter);
            } else if (utility.getText().equals("Electricity") || utility.getText().equals("Gas")) {
                TextView meter = viewEvent.findViewById(R.id.meterTV);
                meter.setText(R.string.kw);
            }
            return viewEvent;
        }
    }
}
