package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentProfileAdmin extends Fragment{
    View profile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Profile");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        profile = inflater.inflate(R.layout.layout_profile_admin, container, false);
        TextView name = profile.findViewById(R.id.familyNameHomeTextViewAdmin);
        name.setText(ActivityLogIn.admin.getName());
        TextView address = profile.findViewById(R.id.addressHomeTextViewAdmin);
        address.setText(ActivityLogIn.admin.getAddress());
        return profile;
    }
}
