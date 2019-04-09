package com.example.alexandrumoldovan.utilities.Resident.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Contract;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.Resident.Activities.ActivityUser;

import java.util.HashMap;
import java.util.Map;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_CONTRACT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_USER_NAME_URL;

public class FragmentSettingsUser extends Fragment {
    View settings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Settings");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        settings = inflater.inflate(R.layout.layout_settings_user, container, false);

        EditText name = settings.findViewById(R.id.editNameSettingsUser);
        name.setText(ActivityLogIn.user.getName());

        Switch garage = settings.findViewById(R.id.garageUsageUserSwitch);
        Switch water = settings.findViewById(R.id.waterStatusUserSwitch);
        Switch gas = settings.findViewById(R.id.gasStatusUserSwitch);
        Switch electricity = settings.findViewById(R.id.electricityStatusUserSwitch);

        Contract contract = getUserContract();
        if (contract.getGarage().equals("false"))
            garage.setChecked(false);
        else garage.setChecked(true);

        if (contract.getWater().equals("false"))
            water.setChecked(false);
        else water.setChecked(true);

        if (contract.getGas().equals("false"))
            gas.setChecked(false);
        else gas.setChecked(true);

        if (contract.getElectricity().equals("false"))
            electricity.setChecked(false);
        else electricity.setChecked(true);

        CardView saveBtn = settings.findViewById(R.id.saveCardView);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText name = settings.findViewById(R.id.editNameSettingsUser);
                final Switch garage = settings.findViewById(R.id.garageUsageUserSwitch);
                final Switch water = settings.findViewById(R.id.waterStatusUserSwitch);
                final Switch gas = settings.findViewById(R.id.gasStatusUserSwitch);
                final Switch electricity = settings.findViewById(R.id.electricityStatusUserSwitch);

                if (!name.getText().toString().equals(ActivityLogIn.user.getName()))
                    updateUserNameInDB(name.getText().toString());
                updateContractsInDB(garage, water, gas, electricity);
                Toast.makeText(getActivity(), "Data updated.", Toast.LENGTH_SHORT).show();
            }
        });

        return settings;
    }

    private void updateUserNameInDB(final String name) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_USER_NAME_URL, new Response.Listener<String>() {
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
                User user = ActivityLogIn.user;
                Map<String, String> parameters = new HashMap<>();
                setInLocalListName(name);
                parameters.put("ID", String.valueOf(user.getID()));
                parameters.put("email", user.getEmail());
                parameters.put("password", user.getPassword());
                parameters.put("name", name);
                parameters.put("address", user.getAddress());
                parameters.put("apartment", String.valueOf(user.getApartment()));
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void setInLocalListName(String name) {
        ActivityLogIn.user.setName(name);
        User user = ActivityLogIn.user;
        for (User localUser : ActivityLogIn.users)
            if (localUser.getEmail().equals(user.getEmail())
                    && localUser.getPassword().equals(user.getPassword()))
                localUser.setName(name);
    }

    private void updateContractsInDB(final Switch garage, final Switch water, final Switch gas, final Switch electricity) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_CONTRACT_URL, new Response.Listener<String>() {
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
                Boolean gasCheck = gas.isChecked();
                Boolean waterCheck = water.isChecked();
                Boolean electricityCheck = electricity.isChecked();
                Boolean garageCheck = garage.isChecked();
                setContractsStatus(gasCheck, waterCheck, electricityCheck, garageCheck);
                parameters.put("user", String.valueOf(ActivityLogIn.user.getID()));
                parameters.put("gas", gasCheck.toString());
                parameters.put("water", waterCheck.toString());
                parameters.put("electricity", electricityCheck.toString());
                parameters.put("garage", garageCheck.toString());
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void setContractsStatus(Boolean gasCheck, Boolean waterCheck, Boolean electricityCheck, Boolean garageCheck) {
        for (Contract contract : ActivityLogIn.contracts)
            if (contract.getUser().equals(ActivityLogIn.user.getID())) {
                contract.setElectricity(electricityCheck.toString());
                contract.setGarage(garageCheck.toString());
                contract.setGas(gasCheck.toString());
                contract.setWater(waterCheck.toString());
            }
    }

    private Contract getUserContract() {
        for (Contract contract : ActivityLogIn.contracts)
            if (contract.getUser().equals(ActivityLogIn.user.getID()))
                return contract;
        return null;
    }
}
