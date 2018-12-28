package com.example.alexandrumoldovan.utilities;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Report;
import com.example.alexandrumoldovan.utilities.Domain.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentDay;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentMonth;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentYear;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getDate;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getLastReport;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getMonthNumber;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.setSpinnerCurrentDate;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_REPORT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.REPORT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_QUANTITY_REPORT_URL;

public class FragmentResourcesElectricityUser extends Fragment {
    private View resources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Electricity");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        resources = inflater.inflate(R.layout.layout_resources_electricity_user, container, false);

        String[] values = {"January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        final Spinner spinner = resources.findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        setSpinnerCurrentDate(adapter, spinner);

        TextView lastMonth = resources.findViewById(R.id.lastMonthElectricityTextView);
        final Report lastElectricityReport = getLastReport(ActivityLogIn.reports, "Electricity");
        if (lastElectricityReport != null) {
            Integer quantityLastMonth = lastElectricityReport.getQuantity();
            lastMonth.setText(String.valueOf(quantityLastMonth));
        } else lastMonth.setText("N/A");

        final EditText thisReport = resources.findViewById(R.id.electricityUserTextInput);
        thisReport.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_GO
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || actionId == EditorInfo.IME_ACTION_NEXT
                                || actionId == EditorInfo.IME_ACTION_SEND
                                || actionId == EditorInfo.IME_ACTION_SEARCH
                                || event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed()) {
                                try {
                                    Integer quantityLastMonth = lastElectricityReport.getQuantity();
                                    Integer currentMonth = Integer.valueOf(thisReport.getText().toString());
                                    Integer minus = currentMonth - quantityLastMonth;
                                    if (minus > 0) {
                                        TextView usedNow = resources.findViewById(R.id.electricityUsedThisMonth);
                                        usedNow.setText(String.valueOf(minus));
                                    } else
                                        Toast.makeText(getActivity(), "Please complete the consumption with valid data.",
                                                Toast.LENGTH_SHORT).show();
                                } catch (Exception ex) {
                                }
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        CardView sendReport = resources.findViewById(R.id.sendReportBtn);
        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = resources.findViewById(R.id.electricityUserTextInput);
                if (!editText.getText().toString().isEmpty())
                    openConfirmPopUpForResources("Electricity", editText.getText().toString(),
                            "kW", spinner.getSelectedItem().toString());
                else
                    Toast.makeText(getActivity(), "Please complete the consumption.", Toast.LENGTH_SHORT).show();
            }
        });

        if (isAlreadyReported()) {
            showOkPopUp("You have already reported your consumption for this utility ! But you can update your report by sending it again.");
        }

        return resources;
    }

    private Boolean isAlreadyReported() {
        for (Report report : ActivityLogIn.reports) {
            if (report.getUser().equals(ActivityLogIn.user.getID())
                    && report.getUtility().equals("Electricity")
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

        TextView resource = customDialog.findViewById(R.id.resourceTypeConfirmSendingPopUpTextViewUser);
        resource.setText(utility);
        TextView quantityTW = customDialog.findViewById(R.id.resourceMetterPopUpTextViewUser);
        quantityTW.setText(quantity);
        TextView meterTW = customDialog.findViewById(R.id.resourceMetterUnitPopUpTextViewUser);
        meterTW.setText(meter);
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
                getActivity().getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                        .replace(R.id.content_frame, new FragmentResourcesUser())
                        .commit();
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
