package com.example.alexandrumoldovan.utilities.Common;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexandrumoldovan.utilities.R;

public class FragmentAboutAllUsers extends Fragment {

    View about;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("About");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        about = inflater.inflate(R.layout.layout_about_users, container, false);
        return about;
    }
}
