package com.example.alexandrumoldovan.utilities.Resident.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Report;
import com.example.alexandrumoldovan.utilities.R;
import com.example.alexandrumoldovan.utilities.Resident.Activities.ActivityUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentMonth;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentYear;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getDate;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getLastReport;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getMonthNumber;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.setSpinnerCurrentDate;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_REPORT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_QUANTITY_REPORT_URL;

public class FragmentResourcesWaterUser extends Fragment {
    View resources;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("Water");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        resources = inflater.inflate(R.layout.layout_resources_water_user, container, false);
        String[] values = {"January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"};
        final Spinner spinner = resources.findViewById(R.id.monthSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        setSpinnerCurrentDate(adapter, spinner);

        TextView lastMonth = resources.findViewById(R.id.lastMonthWaterTextView);
        final Report lastWaterReport = getLastReport(ActivityLogIn.reports, "Water");
        if (lastWaterReport != null) {
            Integer quantityLastMonth = lastWaterReport.getQuantity();
            lastMonth.setText(String.valueOf(quantityLastMonth));
        } else lastMonth.setText("N/A");

        final EditText thisReport = resources.findViewById(R.id.waterUserTextInput);
        thisReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                    usedNow.setText(String.valueOf(lastWaterReport.getQuantity().toString()));
                } catch (NullPointerException ex) {
                    TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                    usedNow.setText(String.valueOf("0"));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Integer quantityLastMonth = lastWaterReport.getQuantity();
                    Integer currentMonth = Integer.valueOf(thisReport.getText().toString());
                    Integer minus = currentMonth - quantityLastMonth;
                    TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                    usedNow.setText(String.valueOf(minus));
                } catch (NullPointerException ex) {
                    try {
                        Integer currentMonth = Integer.valueOf(thisReport.getText().toString());
                        TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                        usedNow.setText(String.valueOf(currentMonth));
                    } catch (NumberFormatException ex2) {
                    }
                } catch (NumberFormatException ex) {
                    TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                    usedNow.setText(String.valueOf("0"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Integer quantityLastMonth = lastWaterReport.getQuantity();
                    Integer currentMonth = Integer.valueOf(thisReport.getText().toString());
                    Integer minus = currentMonth - quantityLastMonth;

                    TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                    usedNow.setText(String.valueOf(minus));
                } catch (NullPointerException ex) {
                    try {
                        Integer currentMonth = Integer.valueOf(thisReport.getText().toString());
                        TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                        usedNow.setText(String.valueOf(currentMonth));
                    } catch (NumberFormatException ex2) {
                    }
                } catch (NumberFormatException ex) {
                    TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                    usedNow.setText(String.valueOf("0"));
                }
            }
        });

        CardView sendReport = resources.findViewById(R.id.sendReportBtn);
        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usedNow = resources.findViewById(R.id.waterUsedThisMonth);
                EditText editText = resources.findViewById(R.id.waterUserTextInput);
                if (!editText.getText().toString().isEmpty() && Integer.valueOf(usedNow.getText().toString()) >= 0)
                    openConfirmPopUpForResources("Water", editText.getText().toString(), "m³", spinner.getSelectedItem().toString());
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
            Integer monthNumber = Integer.valueOf(getMonthNumber(report.getMonth()));
            Integer dateMonth = Integer.valueOf(report.getDate().substring(4, 6));
            if (report.getUser().equals(ActivityLogIn.user.getID())
                    && report.getUtility().equals("Water")
                    && report.getMonth().equals(getCurrentMonth(true))
                    && report.getDate().startsWith(getCurrentYear())
                    && monthNumber >= dateMonth)
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
