package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.alexandrumoldovan.utilities.ActivityUser.acct;

public class FragmentProfileUser extends Fragment {
    View events;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Profile");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (acct != null) {
            events = inflater.inflate(R.layout.layout_profile_google_user, container, false);
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            ImageView imageView = events.findViewById(R.id.profilePicture);
            if (personPhoto != null)
                Picasso.with(getActivity()).load(personPhoto).into(imageView);
            else
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.profile_picture));
            TextView name = events.findViewById(R.id.familyNameHomeTextViewUser);
            name.setText(personName);
            TextView email = events.findViewById(R.id.emailAddressProfileUser);
            email.setText(personEmail);
        } else
            events = inflater.inflate(R.layout.layout_profile_user, container, false);
        return events;
    }
}
