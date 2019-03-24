package com.example.alexandrumoldovan.utilities.Admin.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Admin.Activities.ActivityAdmin;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Charges;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.User.Activities.ActivityUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentMonth;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentYear;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.setSpinnerCurrentDate;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.CHARGES_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_CHARGES_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_CHARGE_URL;

public class FragmentChargesAdmin extends Fragment {
    View charges;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Charges");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        populateCharges();
        charges = inflater.inflate(R.layout.layout_charges_admin, container, false);

        String[] values2 = {"January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        final Spinner spinner = charges.findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values2);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        setSpinnerCurrentDate(adapter, spinner);

        final CardView save = charges.findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText garageET = charges.findViewById(R.id.garageFee);
                String garage = garageET.getText().toString();
                EditText reparationsET = charges.findViewById(R.id.reparationsFee);
                String reparations = reparationsET.getText().toString();
                EditText cleaningET = charges.findViewById(R.id.cleaningFee);
                String cleaning = cleaningET.getText().toString();
                String spinnerText = spinner.getSelectedItem().toString();
                spinnerText = spinnerText + " " + getCurrentYear();
                if (garage.isEmpty() || reparations.isEmpty() || cleaning.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    Charges charges = isAlreadyCharged(spinnerText);
                    if (charges == null)
                        insertCharges(garage, reparations, cleaning, spinnerText);
                    else updateCharge(charges.getID(), garage, reparations, cleaning, spinnerText);
                }
            }
        });
        return charges;
    }

    private void insertCharges(final String garage, final String reparations, final String cleaning, final String spinnerText) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_CHARGES_URL, new Response.Listener<String>() {
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
                parameters.put("garage", garage);
                parameters.put("reparations", reparations);
                parameters.put("cleaning", cleaning);
                parameters.put("month", spinnerText);
                parameters.put("address", ActivityLogIn.admin.getAddress());
                return parameters;
            }

        };
        requestQueue.add(request);
        Toast.makeText(getActivity(), "Charges sent.", Toast.LENGTH_SHORT).show();
        goBackToHomeAdmin();
    }

    private void updateCharge(final Integer ID, final String garage, final String reparations,
                              final String cleaning, final String spinnerText) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_CHARGE_URL, new Response.Listener<String>() {
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
                parameters.put("ID", String.valueOf(ID));
                parameters.put("garage", garage);
                parameters.put("reparations", reparations);
                parameters.put("cleaning", cleaning);
                parameters.put("month", spinnerText);
                parameters.put("address", ActivityLogIn.admin.getAddress());
                return parameters;
            }

        };
        requestQueue.add(request);
        Toast.makeText(getActivity(), "Charges updated.", Toast.LENGTH_SHORT).show();
        goBackToHomeAdmin();
    }

    private void goBackToHomeAdmin() {
        getActivity().getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentHomeAdmin())
                .commit();
    }

    private Charges isAlreadyCharged(String spinnerText) {
        for (Charges charges : ActivityLogIn.charges)
            if (charges.getMonth().equals(spinnerText)
                    && charges.getAddress().equals(ActivityLogIn.admin.getAddress()))
                return charges;
        return null;
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
