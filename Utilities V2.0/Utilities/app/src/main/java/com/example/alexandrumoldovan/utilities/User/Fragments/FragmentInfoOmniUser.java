package com.example.alexandrumoldovan.utilities.User.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.User.Activities.ActivityUser;

public class FragmentInfoOmniUser extends Fragment {

    View omni;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Omni");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        omni = inflater.inflate(R.layout.layout_information_omni_user, container, false);
        return omni;
    }
}
