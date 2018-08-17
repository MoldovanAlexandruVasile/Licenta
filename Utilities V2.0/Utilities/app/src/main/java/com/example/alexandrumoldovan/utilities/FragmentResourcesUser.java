package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentResourcesUser extends Fragment {

    View grid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Resources report");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        grid = inflater.inflate(R.layout.layout_resources_main_user, container, false);
        return grid;
    }
}
