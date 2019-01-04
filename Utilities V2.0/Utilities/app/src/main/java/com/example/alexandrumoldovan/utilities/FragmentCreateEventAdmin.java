package com.example.alexandrumoldovan.utilities;

import android.app.Dialog;
import android.app.Fragment;
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
import com.example.alexandrumoldovan.utilities.Domain.Event;
import com.example.alexandrumoldovan.utilities.Domain.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentYear;
import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.wasInPast;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.EVENT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_EVENT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_USER_URL;

public class FragmentCreateEventAdmin extends Fragment {
    View createEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Create event");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        createEvent = inflater.inflate(R.layout.layout_create_event_admin, container, false);
        final Spinner yearSpinner = createEvent.findViewById(R.id.year);
        final Spinner monthSpinner = createEvent.findViewById(R.id.month);
        final Spinner daySpinner = createEvent.findViewById(R.id.day);
        final Spinner hourSpinner = createEvent.findViewById(R.id.hour);
        final Spinner minuteSpinner = createEvent.findViewById(R.id.minute);
        setSpinnersData(yearSpinner, monthSpinner, daySpinner, hourSpinner, minuteSpinner);

        final CardView createEventBtn = createEvent.findViewById(R.id.createEventCardView);
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleET = createEvent.findViewById(R.id.titleEditTextAdmin);
                String title = titleET.getText().toString();
                EditText detailsET = createEvent.findViewById(R.id.detailsEditTextAdmin);
                String details = detailsET.getText().toString();
                if (title.isEmpty() || details.isEmpty())
                    showOkPopUp("Make sure all the fields are completed.");
                else {
                    String date = yearSpinner.getSelectedItem() + "-"
                            + monthSpinner.getSelectedItem() + "-"
                            + daySpinner.getSelectedItem();
                    Event event = new Event(title, details, date, (String) hourSpinner.getSelectedItem(),
                            (String) minuteSpinner.getSelectedItem(), ActivityLogIn.admin.getAddress());
                    insertEventInDB(event);
                    goBackToHomeAdmin();
                }
            }
        });
        return createEvent;
    }

    private void goBackToHomeAdmin() {
        getActivity().getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentHomeAdmin())
                .commit();
    }

    private void setSpinnersData(Spinner yearSpinner, Spinner monthSpinner, Spinner daySpinner, Spinner hourSpinner, Spinner minuteSpinner) {
        setYearSpinner(yearSpinner);
        setMonthSpinner(monthSpinner);
        setDaySpinner(daySpinner);
        setHourSpinner(hourSpinner);
        setMinuteSpinner(minuteSpinner);
    }

    private void setYearSpinner(Spinner yearSpinner) {
        String[] values = new String[10];
        Integer currentYear = Integer.valueOf(getCurrentYear());
        for (Integer i = 0; i <= 9; i++) {
            String year = String.valueOf(currentYear + i);
            values[i] = year;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        yearSpinner.setAdapter(adapter);
    }

    private void setMonthSpinner(Spinner monthSpinner) {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        monthSpinner.setAdapter(adapter);
    }

    private void setDaySpinner(Spinner daySpinner) {
        String[] values = new String[31];
        for (Integer i = 0; i < 31; i++) {
            if (i < 9)
                values[i] = "0" + String.valueOf(i + 1);
            else
                values[i] = String.valueOf(i + 1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        daySpinner.setAdapter(adapter);
    }

    private void setHourSpinner(Spinner hourSpinner) {
        String[] values = new String[24];
        for (Integer i = 0; i < 24; i++) {
            if (i <= 9)
                values[i] = "0" + String.valueOf(i);
            else values[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        hourSpinner.setAdapter(adapter);
    }

    private void setMinuteSpinner(Spinner minuteSpinner) {
        String[] values = new String[60];
        for (Integer i = 0; i < 60; i++) {
            if (i <= 9)
                values[i] = "0" + String.valueOf(i);
            else values[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        minuteSpinner.setAdapter(adapter);
    }

    private void insertEventInDB(final Event event) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_EVENT_URL, new Response.Listener<String>() {
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
                Log.e("REST INSERT EVENT", event.toString());
                Map<String, String> parameters = new HashMap<>();
                parameters.put("title", event.getTitle());
                parameters.put("details", event.getDetails());
                parameters.put("date", event.getDate());
                parameters.put("hour", event.getHour());
                parameters.put("minute", event.getMinute());
                parameters.put("address", event.getAddress());
                return parameters;
            }
        };
        requestQueue.add(request);
        Toast.makeText(getActivity(), "Event created with success.", Toast.LENGTH_SHORT).show();
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
