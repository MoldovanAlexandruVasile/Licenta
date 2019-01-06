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

import com.example.alexandrumoldovan.utilities.Domain.Event;
import com.example.alexandrumoldovan.utilities.Domain.Event_User;
import com.example.alexandrumoldovan.utilities.Domain.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getEventByID;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getUserByID;

public class FragmentEventsReportsDetailsAdmin extends Fragment {
    private View eventsReports;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(FragmentEventsReportsAdmin.pickedEvent.getTitle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        eventsReports = inflater.inflate(R.layout.layout_event_reports_admin, container, false);
        ListView listView = eventsReports.findViewById(R.id.eventsReportsListEventsReports);
        CustomAdapter customAdapter = new CustomAdapter() {
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        listView.setAdapter(customAdapter);
        return eventsReports;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return FragmentEventsReportsAdmin.myEventReports.size();
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
            Event_User event_user = FragmentEventsReportsAdmin.myEventReports.get(position);
            User user = getUserByID(event_user.getUser());
            Event event = getEventByID(event_user.getEvent());
            assert user != null;
            assert event != null;
            TextView apartment = viewEvent.findViewById(R.id.apartmentNumberEventsReportsCustomLayoutTextViewAdmin);
            apartment.setText(String.valueOf(user.getApartment()));
            TextView owner = viewEvent.findViewById(R.id.apartmentOwnerEventsReportsCustomLayoutTextViewAdmin);
            owner.setText(user.getName());
            TextView participation = viewEvent.findViewById(R.id.apartmentParticipationEventsReportsCustomLayoutTextViewAdmin);
            participation.setText(event_user.getStatus());
            return viewEvent;
        }
    }
}
