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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FragmentHomeUser extends Fragment {

    private View user, myView, myView2, myView3;
    private boolean isDropped = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Home");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        user = inflater.inflate(R.layout.layout_home_user, container, false);

        myView = user.findViewById(R.id.my_view);
        myView2 = user.findViewById(R.id.my_view2);
        myView3 = user.findViewById(R.id.my_view3);

        myView2.setVisibility(View.GONE);
        myView3.setVisibility(View.GONE);


        ImageView imageView = myView.findViewById(R.id.action_image_menu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSlideViewButtonClick();
            }
        });

        LinearLayout confirmParticipation = myView3.findViewById(R.id.confirmParticipationTextViewUser);
        LinearLayout declineParticipation = myView3.findViewById(R.id.declineParticipationTextViewUser);

        confirmParticipation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                TextView participationTV = myView.findViewById(R.id.eventParticipationTextViewUser);
                participationTV.setText("Participating");
                participationTV.setTypeface(null, Typeface.BOLD);
                onSlideViewButtonClick();
            }
        });

        declineParticipation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                TextView participationTV = myView.findViewById(R.id.eventParticipationTextViewUser);
                participationTV.setText("Declined");
                participationTV.setTypeface(null, Typeface.BOLD);
                onSlideViewButtonClick();
            }
        });

        return user;
    }

    public void slideUp(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,               // fromXDelta
                0,                 // toXDelta
                140,          // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        ImageView imageView = myView.findViewById(R.id.action_image_menu);
        imageView.setImageResource(R.drawable.ic_arrow_drop_down);
        view.startAnimation(animate);
        myView3.setVisibility(View.GONE);
        myView2.setVisibility(View.GONE);
    }


    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                140); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        ImageView imageView = myView.findViewById(R.id.action_image_menu);
        imageView.setImageResource(R.drawable.ic_arrow_drop_up);
        view.startAnimation(animate);
        myView2.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                myView3.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    public void onSlideViewButtonClick() {
        if (isDropped) {
            slideUp(myView2);
        } else {
            slideDown(myView2);
        }
        isDropped = !isDropped;
    }

}
