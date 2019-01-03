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
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Admin;
import com.example.alexandrumoldovan.utilities.Domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_ADMIN_PASS_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_USER_PASS_URL;

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

        CardView saveBtn = changePass.findViewById(R.id.changePassBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText oldPass = changePass.findViewById(R.id.oldPasswordTextInputAdmin);
                EditText newPass = changePass.findViewById(R.id.newPasswordTextInputAdmin);
                EditText confirmNewPass = changePass.findViewById(R.id.confirmPasswordTextInputAdmin);

                String oldP = oldPass.getText().toString();
                String newP = newPass.getText().toString();
                String confirmNewP = confirmNewPass.getText().toString();

                if (oldP.isEmpty() || newP.isEmpty() || confirmNewP.isEmpty())
                    showOkPopUp("Please fill all the fields !", false);
                else if (!oldP.equals(ActivityLogIn.admin.getPassword()))
                    showOkPopUp("Incorrect old password !", false);
                else if (!newP.equals(confirmNewP))
                    showOkPopUp("The new passwords does not match !", false);
                else if (oldP.equals(newP))
                    showOkPopUp("Your new password cannot be the same as the old one.", false);
                else updatePasswordInDB(newP);
            }
        });

        return changePass;
    }

    private void updatePasswordInDB(final String pass) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_ADMIN_PASS_URL, new Response.Listener<String>() {
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
                Admin admin = ActivityLogIn.admin;
                Map<String, String> parameters = new HashMap<>();
                Log.e("REST CHANGE ID PASS", String.valueOf(admin.getID()));
                parameters.put("ID", String.valueOf(admin.getID()));
                parameters.put("email", admin.getEmail());
                parameters.put("password", pass);
                parameters.put("name", admin.getName());
                parameters.put("address", admin.getAddress());
                return parameters;
            }
        };
        requestQueue.add(request);
        showOkPopUp("You have to reconnect using your new password.", true);
    }

    private void goToLogIn() {
        Intent intent = new Intent(getActivity(), ActivityLogIn.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        getActivity().finish();
    }

    private void showOkPopUp(String message, final Boolean logOut) {
        final Dialog customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText(message);
        CardView yesCardView = customDialog.findViewById(R.id.okButton);
        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                if (logOut)
                    goToLogIn();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }
}
