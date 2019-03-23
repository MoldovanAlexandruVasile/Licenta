package com.example.alexandrumoldovan.utilities.Admin.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Admin;
import com.example.alexandrumoldovan.utilities.R;

import java.util.HashMap;
import java.util.Map;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_ADMIN_NAME_URL;

public class FragmentSettingsAdmin extends Fragment {
    View settings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Settings");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        settings = inflater.inflate(R.layout.layout_settings_admin, container, false);

        EditText name = settings.findViewById(R.id.editNameSettingsAdmin);
        name.setText(ActivityLogIn.admin.getName());

        CardView saveBtn = settings.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText name = settings.findViewById(R.id.editNameSettingsAdmin);

                if (!name.getText().toString().equals(ActivityLogIn.admin.getName())) {
                    updateAdminNameInDB(name.getText().toString());
                    Toast.makeText(getActivity(), "Data updated.", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getActivity(), "Everything up to date.", Toast.LENGTH_SHORT).show();
            }
        });


        return settings;
    }

    private void updateAdminNameInDB(final String name) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_ADMIN_NAME_URL, new Response.Listener<String>() {
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
                Admin admin = ActivityLogIn.admin;
                Map<String, String> parameters = new HashMap<>();
                setInLocalListName(name);
                parameters.put("ID", String.valueOf(admin.getID()));
                parameters.put("email", admin.getEmail());
                parameters.put("password", admin.getPassword());
                parameters.put("name", name);
                parameters.put("address", admin.getAddress());
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void setInLocalListName(String name) {
        ActivityLogIn.admin.setName(name);
        Admin admin = ActivityLogIn.admin;
        for (Admin localAdmin : ActivityLogIn.admins)
            if (localAdmin.getEmail().equals(admin.getEmail())
                    && localAdmin.getPassword().equals(admin.getPassword()))
                localAdmin.setName(name);
    }
}
