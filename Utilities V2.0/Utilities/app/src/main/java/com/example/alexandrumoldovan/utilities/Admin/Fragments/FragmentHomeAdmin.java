package com.example.alexandrumoldovan.utilities.Admin.Fragments;

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
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Charges;
import com.example.alexandrumoldovan.utilities.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.CHARGES_URL;

public class FragmentHomeAdmin extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        populateCharges();
        return inflater.inflate(R.layout.layout_home_admin, container, false);
    }

    private void populateCharges() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, CHARGES_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST CHARGES", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("charges");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Charges localCharge = new Gson().fromJson(resultsArray.get(i).toString(), Charges.class);
                                ActivityLogIn.charges.add(localCharge);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST CHARGES Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }
}
