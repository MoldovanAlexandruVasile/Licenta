package com.example.alexandrumoldovan.utilities.User.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Report;
import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.User.Activities.ActivityUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.REPORT_URL;

public class FragmentArchiveUser extends Fragment {

    View history;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Archive");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        populateReports();
        history = inflater.inflate(R.layout.layout_archive_user, container, false);
        final ListView listView = history.findViewById(R.id.monthsList);
        String[] values = new String[]{
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        List<String> monthsList = new ArrayList<>(Arrays.asList(values));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, monthsList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    sendToTheInfoFragment("January");
                } else if (i == 1) {
                    sendToTheInfoFragment("February");
                } else if (i == 2) {
                    sendToTheInfoFragment("March");
                } else if (i == 3) {
                    sendToTheInfoFragment("April");
                } else if (i == 4) {
                    sendToTheInfoFragment("May");
                } else if (i == 5) {
                    sendToTheInfoFragment("June");
                } else if (i == 6) {
                    sendToTheInfoFragment("July");
                } else if (i == 7) {
                    sendToTheInfoFragment("August");
                } else if (i == 8) {
                    sendToTheInfoFragment("September");
                } else if (i == 9) {
                    sendToTheInfoFragment("October");
                } else if (i == 10) {
                    sendToTheInfoFragment("November");
                } else if (i == 11) {
                    sendToTheInfoFragment("December");
                }
            }
        });
        return history;
    }

    private void sendToTheInfoFragment(String month) {
        FragmentArchiveDetailsUser fragmentArchiveDetailsUser = new FragmentArchiveDetailsUser();
        Bundle args = new Bundle();
        args.putString("Month", month);
        fragmentArchiveDetailsUser.setArguments(args);
        getActivity().getFragmentManager().
                beginTransaction().
                setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right).
                add(R.id.content_frame, fragmentArchiveDetailsUser).
                replace(R.id.content_frame, fragmentArchiveDetailsUser).
                commit();
    }

    private void populateReports() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, REPORT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST REPORT", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("report");
                            ActivityLogIn.reports = new ArrayList<>();
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Report localReport = new Gson().fromJson(resultsArray.get(i).toString(), Report.class);
                                ActivityLogIn.reports.add(localReport);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST REPORT Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }
}
