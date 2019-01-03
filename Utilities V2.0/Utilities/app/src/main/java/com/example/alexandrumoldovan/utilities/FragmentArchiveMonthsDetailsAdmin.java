package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexandrumoldovan.utilities.Domain.Report;

import java.util.ArrayList;
import java.util.List;

public class FragmentArchiveMonthsDetailsAdmin extends Fragment {
    private String value;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        value = getArguments().getString("Month");
        getActivity().setTitle(value);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View historyInfo = inflater.inflate(R.layout.layout_archive_month_details_admin, container, false);
        ListView listView = historyInfo.findViewById(R.id.archiveListView);
        CustomAdapter customAdapter = new CustomAdapter() {
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        listView.setAdapter(customAdapter);
        return historyInfo;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return FragmentArchiveMonthsAdmin.myReports.size();
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
            date.setText(FragmentArchiveMonthsAdmin.myReports.get(position).getDate());
            TextView utility = viewEvent.findViewById(R.id.utilityTypeTV);
            utility.setText(FragmentArchiveMonthsAdmin.myReports.get(position).getUtility());
            TextView quantity = viewEvent.findViewById(R.id.quantityTV);
            quantity.setText(String.valueOf(FragmentArchiveMonthsAdmin.myReports.get(position).getQuantity()));
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
