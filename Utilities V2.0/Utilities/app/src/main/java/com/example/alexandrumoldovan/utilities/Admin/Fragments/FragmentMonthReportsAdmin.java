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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexandrumoldovan.utilities.Admin.Activities.ActivityAdmin;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Models.Report;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.getCurrentMonth;


public class FragmentMonthReportsAdmin extends Fragment {
    View archive;
    public static List<Report> reports;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Month reports");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (ActivityAdmin.myUsers.size() > 0) {
            archive = inflater.inflate(R.layout.layout_months_reports_admin, container, false);
            ListView listView = archive.findViewById(R.id.apartmentsListCurrentMonth);
            CustomAdapter customAdapter = new CustomAdapter();
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    populateUserReports(position);
                    if (reports.size() > 0)
                        getActivity().getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                                .replace(R.id.content_frame, new FragmentMonthReportsDetailsAdmin())
                                .commit();
                    else
                        showOkPopUp("The user from apartment " + ActivityAdmin.myUsers.get(position).getApartment() +
                                " didn't sent any reports yet.");
                }
            });
            return archive;
        } else {
            archive = inflater.inflate(R.layout.layout_empty_stuff, container, false);
            return archive;
        }
    }

    private void populateUserReports(Integer position) {
        String currentMonth = getCurrentMonth(true);
        reports = new ArrayList<>();
        User user = ActivityAdmin.myUsers.get(position);
        for (Report localReport : ActivityLogIn.reports) {
            if (localReport.getUser().equals(user.getID())
                    && localReport.getMonth().equals(currentMonth))
                reports.add(localReport);
        }
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

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ActivityAdmin.myUsers.size();
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
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View viewEvent = layoutInflater.inflate(R.layout.custom_row_apartment_admin, null);
            User user = ActivityAdmin.myUsers.get(position);
            TextView apartmentTV = viewEvent.findViewById(R.id.apartmentNumberCustomLayoutTextViewAdmin);
            apartmentTV.setText(String.valueOf(user.getApartment()));
            TextView nameTV = viewEvent.findViewById(R.id.apartmentOwnerCustomLayoutTextViewAdmin);
            nameTV.setText(user.getName());
            return viewEvent;
        }
    }
}
