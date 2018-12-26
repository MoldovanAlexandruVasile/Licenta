package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FragmentResourcesElectricityUser extends Fragment {

    View resources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Electricity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        resources = inflater.inflate(R.layout.layout_resources_electricity_user, container, false);
        String[] values = {"January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        Spinner spinner = resources.findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        return resources;
    }
}
