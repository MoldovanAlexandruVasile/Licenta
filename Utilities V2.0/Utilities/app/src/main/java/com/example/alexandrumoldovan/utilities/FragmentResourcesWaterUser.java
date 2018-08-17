package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FragmentResourcesWaterUser extends Fragment {

    View resources;

    public void FragmentResourcesWaterUser(String s) {

            EditText editText = getActivity().findViewById(R.id.waterUserTextInput);
            editText.setText(s);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Water");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        resources = inflater.inflate(R.layout.layout_resources_water_user, container, false);
        return resources;
    }

}
