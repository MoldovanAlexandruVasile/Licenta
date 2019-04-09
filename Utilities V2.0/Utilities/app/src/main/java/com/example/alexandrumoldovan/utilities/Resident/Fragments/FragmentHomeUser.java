package com.example.alexandrumoldovan.utilities.Resident.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Contract;
import com.example.alexandrumoldovan.utilities.Models.Event;
import com.example.alexandrumoldovan.utilities.Models.Event_User;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.Resident.Activities.ActivityUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.CONTRACT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.EVENT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.EVENT_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_EVENT_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_EVENT_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.USER_URL;

public class FragmentHomeUser extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Home");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        populateEventsUsers();
        populateUsers();
        populateContracts();

        if (ActivityLogIn.events.size() == 0) {
            View user = inflater.inflate(R.layout.layout_empty_stuff, container, false);
            return user;
        } else {
            View user = inflater.inflate(R.layout.layout_home_user, container, false);
            ListView listView = user.findViewById(R.id.eventsListView);
            CustomAdapter customAdapter = new CustomAdapter() {
                @Override
                public boolean isEnabled(int position) {
                    return false;
                }
            };
            listView.setAdapter(customAdapter);
            return user;
        }
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ActivityLogIn.events.size();
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
            final View eventInfoCVUser, optionsCVU;
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View viewEvent = layoutInflater.inflate(R.layout.custom_event_notification_user, null);

            TextView title = viewEvent.findViewById(R.id.eventTitle);
            title.setText(ActivityLogIn.events.get(position).getTitle());
            TextView details = viewEvent.findViewById(R.id.eventTextViewUser);
            details.setText(ActivityLogIn.events.get(position).getDetails());
            TextView date = viewEvent.findViewById(R.id.date);
            date.setText(ActivityLogIn.events.get(position).getDate());
            TextView time = viewEvent.findViewById(R.id.time);
            String timeComputed = ActivityLogIn.events.get(position).getHour() + ":" + ActivityLogIn.events.get(position).getMinute();
            time.setText(timeComputed);

            String status = null;
            for (Event_User event_user : ActivityLogIn.events_users) {
                if (event_user.getUser().equals(ActivityLogIn.user.getID()) &&
                        event_user.getEvent().equals(ActivityLogIn.events.get(position).getID())) {
                    status = event_user.getStatus();
                    break;
                }
            }

            eventInfoCVUser = viewEvent.findViewById(R.id.eventInfoCardViewUser);
            optionsCVU = viewEvent.findViewById(R.id.optionsCardView);

            final LinearLayout confirmParticipation = optionsCVU.findViewById(R.id.confirmParticipationTextViewUser);
            LinearLayout declineParticipation = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);

            if (status != null) {
                if (status.equals("Participate")) {
                    TextView participationTV = eventInfoCVUser.findViewById(R.id.eventParticipationTextViewUser);
                    participationTV.setText("Participate");
                    participationTV.setTypeface(null, Typeface.BOLD);
                    LinearLayout myLinearLayout = confirmParticipation;
                    myLinearLayout.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    TextView participationTV = eventInfoCVUser.findViewById(R.id.eventParticipationTextViewUser);
                    participationTV.setText("Decline");
                    participationTV.setTypeface(null, Typeface.BOLD);
                    LinearLayout linearLayout = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }

            confirmParticipation.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    TextView participationTV = eventInfoCVUser.findViewById(R.id.eventParticipationTextViewUser);
                    if (participationTV.getText().equals("Waiting...")) {
                        insertEventUserInDB("Participate", position);
                        participationTV.setText("Participate");
                        participationTV.setTypeface(null, Typeface.BOLD);
                        LinearLayout linearLayout = optionsCVU.findViewById(R.id.confirmParticipationTextViewUser);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.green));
                    } else if (participationTV.getText().equals("Decline")) {
                        updateEventUserInDB("Participate", position);
                        participationTV.setText("Participate");
                        participationTV.setTypeface(null, Typeface.BOLD);
                        LinearLayout linearLayout = optionsCVU.findViewById(R.id.confirmParticipationTextViewUser);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.green));
                        linearLayout = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            });

            declineParticipation.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    TextView participationTV = eventInfoCVUser.findViewById(R.id.eventParticipationTextViewUser);
                    if (participationTV.getText().equals("Waiting...")) {
                        insertEventUserInDB("Decline", position);
                        participationTV.setText("Decline");
                        participationTV.setTypeface(null, Typeface.BOLD);
                        LinearLayout linearLayout = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.red));
                    } else if (participationTV.getText().equals("Participate")) {
                        updateEventUserInDB("Decline", position);
                        participationTV.setText("Decline");
                        participationTV.setTypeface(null, Typeface.BOLD);
                        LinearLayout linearLayout = optionsCVU.findViewById(R.id.confirmParticipationTextViewUser);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        linearLayout = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);
                        linearLayout.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
            });
            return viewEvent;
        }
    }

    public void insertEventUserInDB(final String participation, final Integer position) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_EVENT_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("user", String.valueOf(ActivityLogIn.user.getID()));
                parameters.put("event", String.valueOf(ActivityLogIn.events.get(position).getID()));
                parameters.put("status", participation);
                return parameters;
            }
        };
        requestQueue.add(request);
        populateEventsUsers();
    }

    public void updateEventUserInDB(final String participation, final Integer position) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_EVENT_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Integer ID = null;
                Map<String, String> parameters = new HashMap<>();
                for (Event_User event_user : ActivityLogIn.events_users) {
                    if (event_user.getUser().equals(ActivityLogIn.user.getID()) &&
                            event_user.getEvent().equals(ActivityLogIn.events.get(position).getID())) {
                        ID = event_user.getID();
                        Log.e("REST ID", String.valueOf(ID));
                        break;
                    }
                }
                parameters.put("ID", String.valueOf(ID));
                parameters.put("user", String.valueOf(ActivityLogIn.user.getID()));
                parameters.put("event", String.valueOf(ActivityLogIn.events.get(position).getID()));
                parameters.put("status", participation);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void populateEvents() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, EVENT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST EVENT USER", response.toString());
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
    }

    private void populateEventsUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, EVENT_USER_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST EVENT_USER", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("event_user");
                            ActivityLogIn.events_users = new ArrayList<>();
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Event_User localEventUser = new Gson().fromJson(resultsArray.get(i).toString(), Event_User.class);
                                ActivityLogIn.events_users.add(localEventUser);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST EVENT_USER Resp", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USER_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST USER", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("user");
                            ActivityLogIn.users = new ArrayList<>();
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                User localUser = new Gson().fromJson(resultsArray.get(i).toString(), User.class);
                                ActivityLogIn.users.add(localUser);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST USER Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateContracts() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, CONTRACT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST CONTRACT", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("contract");
                            ActivityLogIn.contracts = new ArrayList<>();
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Contract localContract = new Gson().fromJson(resultsArray.get(i).toString(), Contract.class);
                                ActivityLogIn.contracts.add(localContract);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST CONTRACT Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }
}
