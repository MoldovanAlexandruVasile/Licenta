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
import com.example.alexandrumoldovan.utilities.Domain.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentYear;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.DELETE_EVENT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_EVENT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.UPDATE_EVENT_URL;

public class FragmentEditEventAdmin extends Fragment {
    View manage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(FragmentEventsManagementAdmin.pickedEvent.getTitle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        manage = inflater.inflate(R.layout.layout_manage_event_admin, container, false);
        final Spinner yearSpinner = manage.findViewById(R.id.year);
        final Spinner monthSpinner = manage.findViewById(R.id.month);
        final Spinner daySpinner = manage.findViewById(R.id.day);
        final Spinner hourSpinner = manage.findViewById(R.id.hour);
        final Spinner minuteSpinner = manage.findViewById(R.id.minute);
        setSpinnersData(yearSpinner, monthSpinner, daySpinner, hourSpinner, minuteSpinner);

        TextView titleTV = manage.findViewById(R.id.titleEditTextAdmin);
        titleTV.setText(FragmentEventsManagementAdmin.pickedEvent.getTitle());
        TextView detailsTV = manage.findViewById(R.id.detailsEditTextAdmin);
        detailsTV.setText(FragmentEventsManagementAdmin.pickedEvent.getDetails());

        CardView save = manage.findViewById(R.id.editEventCardView);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleET = manage.findViewById(R.id.titleEditTextAdmin);
                String title = titleET.getText().toString();
                EditText detailsET = manage.findViewById(R.id.detailsEditTextAdmin);
                String details = detailsET.getText().toString();
                if (title.isEmpty() || details.isEmpty())
                    showOkPopUp("Make sure all the fields are completed.");
                else {
                    String date = yearSpinner.getSelectedItem() + "-"
                            + monthSpinner.getSelectedItem() + "-"
                            + daySpinner.getSelectedItem();
                    Event event = new Event(title, details, date, (String) hourSpinner.getSelectedItem(),
                            (String) minuteSpinner.getSelectedItem(), ActivityLogIn.admin.getAddress());
                    updateEventLocally(event);
                    updateEventInDB(event);
                    goBackToEventsManagement();
                }
            }
        });
        CardView delete = manage.findViewById(R.id.deleteEventCardView);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeletePopUp("Are you sure you want to delete this event?");
            }
        });

        return manage;
    }

    private void updateEventLocally(Event event) {
        for (Event localEvent : ActivityAdmin.myEvents) {
            if (localEvent.getID().equals(FragmentEventsManagementAdmin.pickedEvent.getID())){
                localEvent.setTitle(event.getTitle());
                localEvent.setDetails(event.getDetails());
                localEvent.setDate(event.getDate());
                localEvent.setHour(event.getHour());
                localEvent.setMinute(event.getMinute());
                break;
            }
        }
    }

    private void deleteEventLocally(Integer ID) {
        for (Event localEvent : ActivityAdmin.myEvents) {
            if (localEvent.getID().equals(ID)){
                ActivityAdmin.myEvents.remove(localEvent);
                break;
            }
        }
    }

    private void deleteEventInDB(final Integer ID) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_EVENT_URL, new Response.Listener<String>() {
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
                Log.e("REST DELETE EVENT", String.valueOf(ID));
                Map<String, String> parameters = new HashMap<>();
                parameters.put("ID", String.valueOf(ID));
                return parameters;
            }
        };
        requestQueue.add(request);
        Toast.makeText(getActivity(), "Event deleted with success.", Toast.LENGTH_SHORT).show();
    }

    private void updateEventInDB(final Event event) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_EVENT_URL, new Response.Listener<String>() {
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
                Log.e("REST UPDATE EVENT", event.toString());
                Map<String, String> parameters = new HashMap<>();
                parameters.put("ID", String.valueOf(FragmentEventsManagementAdmin.pickedEvent.getID()));
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
        Toast.makeText(getActivity(), "Event updated with success.", Toast.LENGTH_SHORT).show();
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

        Integer spinnerPosition = adapter.getPosition(FragmentEventsManagementAdmin.pickedEvent.getDate().substring(0, 4));
        yearSpinner.setSelection(spinnerPosition);
    }

    private void setMonthSpinner(Spinner monthSpinner) {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        monthSpinner.setAdapter(adapter);
        Integer spinnerPosition = adapter.getPosition(FragmentEventsManagementAdmin.pickedEvent.getDate().substring(5, 7));
        monthSpinner.setSelection(spinnerPosition);
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
        Integer spinnerPosition = adapter.getPosition(FragmentEventsManagementAdmin.pickedEvent.getDate().substring(8));
        daySpinner.setSelection(spinnerPosition);
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
        Integer spinnerPosition = adapter.getPosition(FragmentEventsManagementAdmin.pickedEvent.getHour());
        hourSpinner.setSelection(spinnerPosition);
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
        Integer spinnerPosition = adapter.getPosition(FragmentEventsManagementAdmin.pickedEvent.getMinute());
        minuteSpinner.setSelection(spinnerPosition);
    }

    private void goBackToEventsManagement() {
        getActivity().getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentEventsManagementAdmin())
                .commit();
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

    private void showDeletePopUp(String message){
        final Dialog customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText(message);
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEventLocally(FragmentEventsManagementAdmin.pickedEvent.getID());
                deleteEventInDB(FragmentEventsManagementAdmin.pickedEvent.getID());
                goBackToEventsManagement();
                customDialog.dismiss();
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
