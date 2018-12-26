package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentProfileUser extends Fragment {
    View profile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Profile");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        profile = inflater.inflate(R.layout.layout_profile_user, container, false);
        TextView name = profile.findViewById(R.id.familyNameHomeTextViewUser);
        name.setText(ActivityLogIn.user.getName());
        TextView apartment = profile.findViewById(R.id.apartmentHomeTextViewUser);
        apartment.setText(String.valueOf(ActivityLogIn.user.getApartment()));
        TextView address = profile.findViewById(R.id.addressHomeTextViewUser);
        address.setText(ActivityLogIn.user.getAddress());

        return profile;
    }
}
