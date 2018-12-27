package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Report;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.REPORT_URL;

public class FragmentResourcesUser extends Fragment {

    View grid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        populateReports();
        grid = inflater.inflate(R.layout.layout_resources_main_user, container, false);
        ((ActivityUser) getActivity()).setActionBarTitle("Resources report");
        return grid;
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
