package com.example.alexandrumoldovan.utilities.Common;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexandrumoldovan.utilities.User.Activities.ActivityUser;
import com.example.alexandrumoldovan.utilities.R;

public class FragmentInfoAllUsers extends Fragment {

    View info;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Information");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        info = inflater.inflate(R.layout.layout_information_users, container, false);
        return info;
    }
}
