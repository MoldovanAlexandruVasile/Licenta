package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Event;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.wasInPast;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.EVENT_URL;

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
        final ListView listView = eventsManagement.findViewById(R.id.eventsList);
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

        ImageView refreshBtn = eventsManagement.findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show();
                listView.setAdapter(new CustomAdapter());
            }
        });
        return eventsManagement;
    }

    private void populateEvents() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, EVENT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("event");
                            ActivityLogIn.events = new ArrayList<>();
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Event localEvent = new Gson().fromJson(resultsArray.get(i).toString(), Event.class);
                                ActivityLogIn.events.add(localEvent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST EVENT Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
        fillMyEvents();
    }

    private void fillMyEvents() {
        ActivityAdmin.myEvents = new ArrayList<>();
        for (Event event : ActivityLogIn.events)
            if (event.getAddress().equals(ActivityLogIn.admin.getAddress())
                    && !ActivityAdmin.myEvents.contains(event))
                if (!wasInPast(event.getDate()))
                    ActivityAdmin.myEvents.add(event);
    }

    class CustomAdapter extends BaseAdapter {
        CustomAdapter() {
            populateEvents();
        }

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
