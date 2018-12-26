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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Event;
import com.example.alexandrumoldovan.utilities.Domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        final CardView createEventBtn = createEvent.findViewById(R.id.createEventCardView);
        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleET = createEvent.findViewById(R.id.titleEditTextAdmin);
                final String title = titleET.getText().toString();
                EditText detailsET = createEvent.findViewById(R.id.detailsEditTextAdmin);
                final String details = detailsET.getText().toString();
                if (title.isEmpty() || details.isEmpty())
                    showOkPopUp("Make sure all the fields are completed.");
                else {
                    insertEventInDB(title, details);
                }
            }
        });
        return createEvent;
    }

    private void insertEventInDB(final String title, final String details) {
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
                Log.e("REST INSERT EVENT", title);
                Map<String, String> parameters = new HashMap<>();
                parameters.put("title", title);
                parameters.put("details", details);
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
