package com.example.alexandrumoldovan.utilities.Common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Admin.Activities.ActivityAdmin;
import com.example.alexandrumoldovan.utilities.Models.Charges;
import com.example.alexandrumoldovan.utilities.Resident.Activities.ActivityUser;
import com.example.alexandrumoldovan.utilities.AppUtils.ConnectionDetector;
import com.example.alexandrumoldovan.utilities.Models.Admin;
import com.example.alexandrumoldovan.utilities.Models.Contract;
import com.example.alexandrumoldovan.utilities.Models.Event;
import com.example.alexandrumoldovan.utilities.Models.Event_User;
import com.example.alexandrumoldovan.utilities.Models.Report;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.ADMIN_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.CHARGES_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.CONTRACT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.EVENT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.EVENT_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.REPORT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.USER_URL;

public class ActivityLogIn extends AppCompatActivity {

    public static Admin admin;
    public static User user;
    public static List<Admin> admins;
    public static List<User> users;
    public static List<Event> events;
    public static List<Event_User> events_users;
    public static List<Report> reports;
    public static List<Contract> contracts;
    public static List<Charges> charges;
    public static List<Integer> apartment;
    private RequestQueue requestQueue;
    private RelativeLayout rellay1;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        populateListsWithDBData();
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
        rellay1 = findViewById(R.id.rellay1);
        handler.postDelayed(runnable, 1500);

        TextView textView = findViewById(R.id.signUpHere);
        SpannableString content = new SpannableString("HERE");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
    }

    private void populateListsWithDBData() {
        admins = new ArrayList<>();
        this.populateAdmins();
        users = new ArrayList<>();
        this.populateUsers();
        events = new ArrayList<>();
        this.populateEvents();
        events_users = new ArrayList<>();
        this.populateEventsUsers();
        reports = new ArrayList<>();
        this.populateReports();
        contracts = new ArrayList<>();
        this.populateContracts();
        charges = new ArrayList<>();
        this.populateCharges();
        admin = null;
        user = null;
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityLogIn.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to exit?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    public void hideKeyboardLogIn(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }

    public void checkLogInCredentials(View view) {
        EditText emailET = findViewById(R.id.emailEditTextLogIn);
        EditText passET = findViewById(R.id.passwordEditTextLogIn);
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();
        if (isAdmin(email, pass)) {
            goToAdmin();
        } else if (isUser(email, pass) && !isUserWithoutAddress(email, pass)) {
            goToUser();
        } else if (isUser(email, pass) && isUserWithoutAddress(email, pass)) {
            goToPickAddress();
        } else {
            showOkPopUp("Invalid email or password. Please try again.");
        }
    }

    private void goToPickAddress() {
        Intent intent = new Intent(getApplicationContext(), ActivityAddressList.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private Boolean isAdmin(String email, String pass) {
        for (Admin localAdmin : admins) {
            if (localAdmin.getEmail().equals(email) && localAdmin.getPassword().equals(pass)) {
                admin = localAdmin;
                Log.e("REST ADMIN FOUND ", admin.toString());
                return true;
            }
        }
        return false;
    }

    private Boolean isUser(String email, String pass) {
        for (User localUser : users) {
            if (localUser.getEmail().equals(email) && localUser.getPassword().equals(pass)) {
                user = localUser;
                Log.e("REST USER FOUND ", user.toString());
                return true;
            }
        }
        return false;
    }

    private Boolean isUserWithoutAddress(String email, String pass) {
        for (User localUser : users) {
            if (localUser.getEmail().equals(email) && localUser.getPassword().equals(pass))
                if (localUser.getAddress().isEmpty()) {
                    return true;
                }
        }
        return false;
    }

    private void goToAdmin() {
        Intent intent = new Intent(getApplicationContext(), ActivityAdmin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void goToUser() {
        Intent intent = new Intent(getApplicationContext(), ActivityUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivitySignUp.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void showOkPopUp(String message) {
        final Dialog customDialog = new Dialog(ActivityLogIn.this);
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

    private void populateAdmins() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ADMIN_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST ADMIN", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("admin");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Admin localAdmin = new Gson().fromJson(resultsArray.get(i).toString(), Admin.class);
                                admins.add(localAdmin);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST ADMIN Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateUsers() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USER_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST USER", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("user");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                User localUser = new Gson().fromJson(resultsArray.get(i).toString(), User.class);
                                users.add(localUser);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST USER Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateEvents() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, EVENT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST EVENT USER", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("event");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Event localEvent = new Gson().fromJson(resultsArray.get(i).toString(), Event.class);
                                events.add(localEvent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST EVENT Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateEventsUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, EVENT_USER_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST EVENT_USER", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("event_user");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Event_User localEventUser = new Gson().fromJson(resultsArray.get(i).toString(), Event_User.class);
                                events_users.add(localEventUser);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST EVENT_USER Resp", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateReports() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, REPORT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST REPORT", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("report");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Report localReport = new Gson().fromJson(resultsArray.get(i).toString(), Report.class);
                                reports.add(localReport);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST REPORT Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateContracts() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, CONTRACT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST CONTRACT", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("contract");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Contract localContract = new Gson().fromJson(resultsArray.get(i).toString(), Contract.class);
                                contracts.add(localContract);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST CONTRACT Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void populateCharges() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, CHARGES_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST CHARGES", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("charges");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Charges localCharge = new Gson().fromJson(resultsArray.get(i).toString(), Charges.class);
                                charges.add(localCharge);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST CHARGES Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }
}
