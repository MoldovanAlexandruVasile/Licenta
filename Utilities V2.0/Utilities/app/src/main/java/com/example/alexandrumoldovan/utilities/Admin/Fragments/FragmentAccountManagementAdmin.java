package com.example.alexandrumoldovan.utilities.Admin.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_USER_URL;

public class FragmentAccountManagementAdmin extends Fragment {
    View accManagement;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Accounts management");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        accManagement = inflater.inflate(R.layout.layout_account_management_admin, container, false);
        CardView createAccount = accManagement.findViewById(R.id.createAccountAdmin);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    EditText emailET = accManagement.findViewById(R.id.usernameTextInputAdmin);
                    String email = emailET.getText().toString();
                    EditText passET = accManagement.findViewById(R.id.newPasswordTextInputAdmin);
                    String pass = passET.getText().toString();
                    EditText confPassET = accManagement.findViewById(R.id.confirmPasswordTextInputUser);
                    String confPass = confPassET.getText().toString();
                    EditText apartmentET = accManagement.findViewById(R.id.apartmentNumberTextInputUser);
                    Integer apartment = Integer.valueOf(apartmentET.getText().toString());
                    EditText nameET = accManagement.findViewById(R.id.nameTextInputUser);
                    String name = nameET.getText().toString();
                    User user = new User(email, pass, name, ActivityLogIn.admin.getAddress(), apartment);
                    if (pass.equals(confPass) && !email.isEmpty() && !String.valueOf(apartment).isEmpty() && !name.isEmpty()) {
                        if (isEmailValid(user))
                            if (isAddressValid(user))
                                createUser(user);
                            else
                                showOkPopUp("This apartment number is already registered for this address." +
                                        "Please register with valid data.");
                        else showOkPopUp("Email already in use.");
                    } else showOkPopUp("Check all fields to be filled correctly.");
                } catch (Exception ex) {
                    showOkPopUp("Check all fields to be filled correctly.");
                }
            }
        });
        return accManagement;
    }

    private Boolean isAddressValid(User user) {
        for (User localUser : ActivityLogIn.users) {
            if (localUser.getAddress().equals(user.getAddress())
                    && localUser.getApartment().equals(user.getApartment()))
                return false;
        }
        return true;
    }

    private Boolean isEmailValid(User user) {
        for (User localUser : ActivityLogIn.users) {
            if (localUser.getEmail().equals(user.getEmail()))
                return false;
        }
        return true;
    }

    private void createUser(final User user) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_USER_URL, new Response.Listener<String>() {
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
                parameters.put("email", user.getEmail());
                parameters.put("password", user.getPassword());
                parameters.put("name", user.getName());
                parameters.put("address", user.getAddress());
                parameters.put("apartment", String.valueOf(user.getApartment()));
                return parameters;
            }

        };
        requestQueue.add(request);
        showOkPopUp("User created with success !");
    }

    private void showOkPopUp(String message) {
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
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }
}
