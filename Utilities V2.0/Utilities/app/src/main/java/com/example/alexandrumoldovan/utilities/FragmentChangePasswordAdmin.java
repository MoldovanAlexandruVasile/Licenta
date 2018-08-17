package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentChangePasswordAdmin extends Fragment {

    View changePass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Change password");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        changePass = inflater.inflate(R.layout.layout_change_password_admin, container, false);
        return changePass;
    }
}
