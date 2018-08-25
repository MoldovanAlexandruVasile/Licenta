package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class FragmentEventsReportsAdmin extends Fragment {
    View eventsReports;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Events reports");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        eventsReports = inflater.inflate(R.layout.layout_event_reports_admin, container, false);


        ListView listView = eventsReports.findViewById(R.id.eventsReportsListEventsReports);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        return eventsReports;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 8;
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

            View viewEvent = layoutInflater.inflate(R.layout.custom_row_event_reports_admin, null);

            viewEvent.setMinimumHeight(100);

            return viewEvent;
        }
    }
}
