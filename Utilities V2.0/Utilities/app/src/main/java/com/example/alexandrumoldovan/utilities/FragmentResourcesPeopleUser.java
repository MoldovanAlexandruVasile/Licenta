package com.example.alexandrumoldovan.utilities;

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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Report;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentMonth;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getDate;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.setSpinnerCurrentDate;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_REPORT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_QUANTITY_REPORT_URL;

public class FragmentResourcesPeopleUser extends Fragment {

    View resources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("People in apartment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        resources = inflater.inflate(R.layout.layout_resources_people_user, container, false);
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        final Spinner spinner = resources.findViewById(R.id.numberPersonsSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        String[] values2 = {"January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        final Spinner spinner2 = resources.findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values2);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);
        setSpinnerCurrentDate(adapter2, spinner2);

        CardView cardView = resources.findViewById(R.id.sendReportBtn);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = spinner.getSelectedItem().toString();
                String month = spinner2.getSelectedItem().toString();
                openConfirmPopUpForResources("People", number, "-", month);
            }
        });

        if (isAlreadyReported()) {
            showOkPopUp("You have already reported the number of people ! But you can update your report by sending it again.");
        }

        return resources;
    }

    private Boolean isAlreadyReported() {
        for (Report report : ActivityLogIn.reports) {
            if (report.getUser().equals(ActivityLogIn.user.getID())
                    && report.getUtility().equals("People")
                    && report.getMonth().equals(getCurrentMonth(true)))
                return true;
        }
        return false;
    }

    private void openConfirmPopUpForResources(final String utility, final String quantity, String meter, final String spinnerText) {
        final Dialog customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_confirm_resources_pop_up);

        TextView consumption = customDialog.findViewById(R.id.consumptionTV);
        consumption.setText("Number:");
        TextView resource = customDialog.findViewById(R.id.resourceTypeConfirmSendingPopUpTextViewUser);
        resource.setText(utility);
        TextView quantityTW = customDialog.findViewById(R.id.resourceMetterPopUpTextViewUser);
        quantityTW.setText(quantity);
        TextView meterTW = customDialog.findViewById(R.id.resourceMetterUnitPopUpTextViewUser);
        meterTW.setVisibility(View.GONE);
        TextView monthTW = customDialog.findViewById(R.id.monthPopUpTextViewUser);
        monthTW.setText(spinnerText);

        CardView yesCardView = customDialog.findViewById(R.id.yesConfirmPopUpCardView);
        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAlreadyReported())
                    insertReportInDB(utility, quantity, spinnerText);
                else updateReportInDB(utility, quantity, spinnerText);
                customDialog.dismiss();
            }
        });

        CardView noCardView = customDialog.findViewById(R.id.noConfirmPopUpCardView);
        noCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void insertReportInDB(final String utility, final String quantity, final String spinnerMonth) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_REPORT_URL, new Response.Listener<String>() {
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
                parameters.put("user", String.valueOf(ActivityLogIn.user.getID()));
                parameters.put("utility", utility);
                parameters.put("quantity", quantity);
                parameters.put("month", spinnerMonth);
                parameters.put("date", getDate());
                return parameters;
            }
        };
        requestQueue.add(request);
        Toast.makeText(getActivity(), "Month report send.", Toast.LENGTH_SHORT).show();
    }

    private void updateReportInDB(final String utility, final String quantity, final String spinnerMonth) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_QUANTITY_REPORT_URL, new Response.Listener<String>() {
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
                parameters.put("user", String.valueOf(ActivityLogIn.user.getID()));
                parameters.put("utility", utility);
                parameters.put("quantity", quantity);
                parameters.put("month", spinnerMonth);
                parameters.put("date", getDate());
                return parameters;
            }
        };
        requestQueue.add(request);
        Toast.makeText(getActivity(), "Month report updated.", Toast.LENGTH_SHORT).show();
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
