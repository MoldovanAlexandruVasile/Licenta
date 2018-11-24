package com.example.alexandrumoldovan.utilities;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentHomeUser extends Fragment {

    private View user;
    private Integer noOfEvents = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Home");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        user = inflater.inflate(R.layout.layout_home_user, container, false);
        ListView listView = user.findViewById(R.id.eventsListView);
        CustomAdapter customAdapter = new CustomAdapter() {
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        listView.setAdapter(customAdapter);
        return user;
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return noOfEvents;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final View eventInfoCVUser, optionsCVU;
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View viewEvent = layoutInflater.inflate(R.layout.custom_event_notification_user, null);

            eventInfoCVUser = viewEvent.findViewById(R.id.eventInfoCardViewUser);
            optionsCVU = viewEvent.findViewById(R.id.optionsCardView);

            final LinearLayout confirmParticipation = optionsCVU.findViewById(R.id.confirmParticipationTextViewUser);
            LinearLayout declineParticipation = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);

            confirmParticipation.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    TextView participationTV = eventInfoCVUser.findViewById(R.id.eventParticipationTextViewUser);
                    participationTV.setText("Participating");
                    participationTV.setTypeface(null, Typeface.BOLD);
                    LinearLayout linearLayout = optionsCVU.findViewById(R.id.confirmParticipationTextViewUser);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.green));
                    linearLayout = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                }
            });

            declineParticipation.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    TextView participationTV = eventInfoCVUser.findViewById(R.id.eventParticipationTextViewUser);
                    participationTV.setText("Declined");
                    participationTV.setTypeface(null, Typeface.BOLD);
                    LinearLayout linearLayout = optionsCVU.findViewById(R.id.confirmParticipationTextViewUser);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    linearLayout = optionsCVU.findViewById(R.id.declineParticipationTextViewUser);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.red));
                }
            });
            return viewEvent;
        }
    }
}
