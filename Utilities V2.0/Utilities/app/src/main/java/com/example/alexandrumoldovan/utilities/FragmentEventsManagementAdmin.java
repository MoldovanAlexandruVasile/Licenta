package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexandrumoldovan.utilities.Domain.Event;

public class FragmentEventsManagementAdmin extends Fragment {

    View eventsManagement;
    public static Event pickedEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Events management");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        eventsManagement = inflater.inflate(R.layout.layout_events_management_admin, container, false);
        ListView listView = eventsManagement.findViewById(R.id.eventsList);
        listView.setAdapter(new CustomAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pickedEvent = ActivityAdmin.myEvents.get(position);
                getActivity().getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                        .replace(R.id.content_frame, new FragmentEditEventAdmin())
                        .commit();
            }
        });
        return eventsManagement;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ActivityAdmin.myEvents.size();
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
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
            View viewEvent = layoutInflater.inflate(R.layout.custom_event_admin, null);
            Event event = ActivityAdmin.myEvents.get(position);
            TextView eventTitleTV = viewEvent.findViewById(R.id.eventTitle);
            eventTitleTV.setText(event.getTitle());
            TextView detailsTV = viewEvent.findViewById(R.id.eventTextViewAdmin);
            detailsTV.setText(event.getDetails());
            TextView dateTV = viewEvent.findViewById(R.id.date);
            dateTV.setText(event.getDate());
            TextView timeTV = viewEvent.findViewById(R.id.time);
            String time = event.getHour() + ":" + event.getMinute();
            timeTV.setText(time);
            return viewEvent;
        }
    }
}
