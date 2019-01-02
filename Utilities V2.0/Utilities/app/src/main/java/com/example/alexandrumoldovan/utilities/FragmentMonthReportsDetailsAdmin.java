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


public class FragmentMonthReportsDetailsAdmin extends Fragment {
    View details;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Month reports details");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        details = inflater.inflate(R.layout.layout_months_reports_details_admin, container, false);
        ListView listView = details.findViewById(R.id.detailsList);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        return details;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return FragmentMonthReportsAdmin.reports.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View viewEvent = layoutInflater.inflate(R.layout.custom_month_report_details_row_admin, null);
            Report report = FragmentMonthReportsAdmin.reports.get(position);
            TextView date = viewEvent.findViewById(R.id.dateTV);
            date.setText(report.getDate());
            TextView utility = viewEvent.findViewById(R.id.utilityTypeTV);
            utility.setText(report.getUtility());
            TextView quantity = viewEvent.findViewById(R.id.quantityTV);
            quantity.setText(String.valueOf(report.getQuantity()));
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
