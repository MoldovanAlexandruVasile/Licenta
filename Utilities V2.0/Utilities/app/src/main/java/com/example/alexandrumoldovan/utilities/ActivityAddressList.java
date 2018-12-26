package com.example.alexandrumoldovan.utilities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Address;
import com.example.alexandrumoldovan.utilities.Domain.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.ADDRESS_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.USER_URL;

public class ActivityAddressList extends AppCompatActivity {
    private static List<Address> addressList;
    private static String fromSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addressList = new ArrayList<>();
        populateAddresses();
        fromSignUp = getIntent().getStringExtra("SignUp");
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_address_list);
        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityAddressList.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText(R.string.cancel_sign_up);
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    private void populateAddresses() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ADDRESS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST ADDRESS", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("address");
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Address localAddress = new Gson().fromJson(resultsArray.get(i).toString(), Address.class);
                                addressList.add(localAddress);
                            }
                            ListView listView = findViewById(R.id.addressList);
                            listView.setAdapter(new CustomAdapter());
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (fromSignUp.equals("YES")) {
                                        Intent intent = new Intent(getApplicationContext(), ActivityUser.class);
                                        ActivitySignUp.user.setAddress(addressList.get(position).getAddress());
                                        ActivityLogIn.user = ActivitySignUp.user;
                                        insertUserInDB();
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        finish();
                                    } else if (fromSignUp.equals("NO")) {
                                        Intent intent = new Intent(getApplicationContext(), ActivityUser.class);
                                        ActivityLogIn.user.setAddress(addressList.get(position).getAddress());
                                        updateUserInDB();
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        finish();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST ADDRESS Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void updateUserInDB() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_USER_URL, new Response.Listener<String>() {
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
                User user = ActivityLogIn.user;
                Map<String, String> parameters = new HashMap<>();
                parameters.put("ID", String.valueOf(user.getID()));
                parameters.put("email", user.getEmail());
                parameters.put("password", user.getPassword());
                parameters.put("name", user.getName());
                parameters.put("address", user.getAddress());
                parameters.put("apartment", String.valueOf(user.getApartment()));
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void insertUserInDB() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                User user = ActivitySignUp.user;
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
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return addressList.size();
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
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View viewEvent = layoutInflater.inflate(R.layout.custom_row_address, null);
            try {
                Address address = addressList.get(position);
                TextView titleTV = viewEvent.findViewById(R.id.addressTextView);
                titleTV.setText(address.getAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return viewEvent;
        }
    }
}