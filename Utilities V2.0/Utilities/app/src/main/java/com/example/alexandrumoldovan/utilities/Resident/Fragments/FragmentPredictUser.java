package com.example.alexandrumoldovan.utilities.Resident.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.Resident.Activities.ActivityUser;

public class FragmentPredictUser extends Fragment {

    View predict;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Predict");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        predict = inflater.inflate(R.layout.layout_predict_user, container, false);
        return predict;
    }
}
