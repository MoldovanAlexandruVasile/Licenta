package com.example.alexandrumoldovan.utilities;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Report;
import com.example.alexandrumoldovan.utilities.Domain.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getUserByID;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.REPORT_URL;

public class FragmentArchiveMonthsAdmin extends Fragment {

    private View months;
    private User user;
    public static List<Report> myReports;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getUserByID(FragmentArchiveAdmin.userID);
        getActivity().setTitle("Apartment " + user.getApartment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        populateReports();
        months = inflater.inflate(R.layout.layout_archive_months_admin, container, false);
        final ListView listView = months.findViewById(R.id.monthsList);
        String[] values = new String[]{
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        List<String> monthsList = new ArrayList<>(Arrays.asList(values));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, monthsList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    sendToTheInfoFragment("January");
                } else if (i == 1) {
                    sendToTheInfoFragment("February");
                } else if (i == 2) {
                    sendToTheInfoFragment("March");
                } else if (i == 3) {
                    sendToTheInfoFragment("April");
                } else if (i == 4) {
                    sendToTheInfoFragment("May");
                } else if (i == 5) {
                    sendToTheInfoFragment("June");
                } else if (i == 6) {
                    sendToTheInfoFragment("July");
                } else if (i == 7) {
                    sendToTheInfoFragment("August");
                } else if (i == 8) {
                    sendToTheInfoFragment("September");
                } else if (i == 9) {
                    sendToTheInfoFragment("October");
                } else if (i == 10) {
                    sendToTheInfoFragment("November");
                } else if (i == 11) {
                    sendToTheInfoFragment("December");
                }
            }
        });
        return months;
    }

    private List<Report> getUserReports(String value) {
        List<Report> aux = new ArrayList<>();
        for (Report report : ActivityLogIn.reports) {
            if (report.getUser().equals(user.getID())
                    && report.getMonth().equals(value)) {
                aux.add(report);
            }
        }
        return aux;
    }

    private void sendToTheInfoFragment(String month) {
        FragmentArchiveMonthsDetailsAdmin fragment = new FragmentArchiveMonthsDetailsAdmin();
        Bundle args = new Bundle();
        args.putString("Month", month);
        fragment.setArguments(args);
        myReports = getUserReports(month);
        if (myReports.size() > 0)
            getActivity().getFragmentManager().
                    beginTransaction().
                    setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right).
                    add(R.id.content_frame, fragment).
                    replace(R.id.content_frame, fragment).
                    commit();
        else showOkPopUp("The user has no reports in this month.");
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

    private void populateReports() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, REPORT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST REPORT", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("report");
                            ActivityLogIn.reports = new ArrayList<>();
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                Report localReport = new Gson().fromJson(resultsArray.get(i).toString(), Report.class);
                                ActivityLogIn.reports.add(localReport);
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
}
