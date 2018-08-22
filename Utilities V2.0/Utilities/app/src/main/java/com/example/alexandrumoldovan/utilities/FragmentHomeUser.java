package com.example.alexandrumoldovan.utilities;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

public class FragmentHomeUser extends Fragment {

    private Integer countDown = 1;
    private View user;
    private HashMap<String, Boolean> isDropped = new HashMap<>();
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

            final View myView1, myView2, myView3;

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View viewEvent = layoutInflater.inflate(R.layout.layout_event_user, null);

            final String eventID = "eventID" + String.valueOf(countDown);
            isDropped.put(eventID, false);
            countDown++;

            myView1 = viewEvent.findViewById(R.id.my_view1);
            myView2 = viewEvent.findViewById(R.id.my_view2);
            myView3 = viewEvent.findViewById(R.id.my_view3);

            myView2.setVisibility(View.GONE);
            myView3.setVisibility(View.GONE);


            ImageView imageView = myView1.findViewById(R.id.action_image_menu);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSlideViewButtonClick(myView1, myView2, myView3, eventID);
                }
            });

            LinearLayout confirmParticipation = myView3.findViewById(R.id.confirmParticipationTextViewUser);
            LinearLayout declineParticipation = myView3.findViewById(R.id.declineParticipationTextViewUser);

            confirmParticipation.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    TextView participationTV = myView1.findViewById(R.id.eventParticipationTextViewUser);
                    participationTV.setText("Participating");
                    participationTV.setTypeface(null, Typeface.BOLD);
                    onSlideViewButtonClick(myView1, myView2, myView3, eventID);
                }
            });

            declineParticipation.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    TextView participationTV = myView1.findViewById(R.id.eventParticipationTextViewUser);
                    participationTV.setText("Declined");
                    participationTV.setTypeface(null, Typeface.BOLD);
                    onSlideViewButtonClick(myView1, myView2, myView3, eventID);
                }
            });

            return viewEvent;
        }
    }

    public void slideUp(View myView1, View myView2, View myView3) {
        TranslateAnimation animate = new TranslateAnimation(
                0,               // fromXDelta
                0,                 // toXDelta
                140,          // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        ImageView imageView = myView1.findViewById(R.id.action_image_menu);
        imageView.setImageResource(R.drawable.ic_arrow_drop_down);
        myView2.startAnimation(animate);
        myView3.setVisibility(View.GONE);
        myView2.setVisibility(View.GONE);
    }


    public void slideDown(View myView1, View myView2, final View myView3) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                140); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        ImageView imageView = myView1.findViewById(R.id.action_image_menu);
        imageView.setImageResource(R.drawable.ic_arrow_drop_up);
        myView2.startAnimation(animate);
        myView2.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        myView3.setVisibility(View.VISIBLE);
                    }
                }, 500);
    }

    public void onSlideViewButtonClick(View myView1, View myView2, View myView3, String key) {
        if (isDropped.get(key)) {
            slideUp(myView1, myView2, myView3);
        } else {
            slideDown(myView1, myView2, myView3);
        }
        isDropped.put(key, !isDropped.get(key));
    }

}
