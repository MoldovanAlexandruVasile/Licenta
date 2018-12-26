package com.example.alexandrumoldovan.utilities;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.DELETE_USER_URL;

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
        TextView apartment = profile.findViewById(R.id.apartmentHomeTextViewUser);
        TextView address = profile.findViewById(R.id.addressHomeTextViewUser);

        name.setText(ActivityLogIn.user.getName());
        apartment.setText(String.valueOf(ActivityLogIn.user.getApartment()));
        address.setText(ActivityLogIn.user.getAddress());

        CardView delete = profile.findViewById(R.id.deleteAccountCardView);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccountPopUp();
            }
        });
        return profile;
    }

    private void deleteUserFromDB() {
        RequestQueue requestDeleteQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                String ID = String.valueOf(ActivityLogIn.user.getID());
                Log.e("REST DELETE USER ID ", ID);
                parameters.put("ID", ID);
                return parameters;
            }
        };
        requestDeleteQueue.add(request);
        Toast.makeText(getActivity(), "Your account has been deleted.", Toast.LENGTH_SHORT).show();
        performLogOut();
    }

    private void performLogOut() {
        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityLogIn.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        getActivity().finish();
    }

    private void deleteAccountPopUp() {
        final Dialog customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to delete this account?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserFromDB();
            }
        });

        CardView noCardView = customDialog.findViewById(R.id.noPopUpCardView);
        noCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }
}
