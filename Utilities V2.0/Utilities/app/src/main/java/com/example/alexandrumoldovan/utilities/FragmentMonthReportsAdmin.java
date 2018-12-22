package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


public class FragmentMonthReportsAdmin extends Fragment {
    View archive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Month reports");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        archive = inflater.inflate(R.layout.layout_months_reports_admin, container, false);
        ListView listView = archive.findViewById(R.id.apartmentsListCurrentMonth);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        return archive;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 9;
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
            View viewEvent = layoutInflater.inflate(R.layout.custom_row_apartment_admin, null);
            return viewEvent;
        }
    }
}
