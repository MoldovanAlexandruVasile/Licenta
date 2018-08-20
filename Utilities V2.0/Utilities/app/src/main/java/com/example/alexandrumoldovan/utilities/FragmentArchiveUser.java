package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentArchiveUser extends Fragment {

    View history;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActivityUser) getActivity()).setActionBarTitle("History");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        history = inflater.inflate(R.layout.layout_archive_user, container, false);

        final ListView listView = history.findViewById(R.id.monthsList);

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
        return history;
    }

    private void sendToTheInfoFragment(String s) {
        FragmentArchiveInfoUser fragmentArchiveInfoUser = new FragmentArchiveInfoUser();
        Bundle args = new Bundle();
        args.putString("Month", s);
        fragmentArchiveInfoUser.setArguments(args);
        getActivity().getFragmentManager().
                beginTransaction().
                setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right).
                add(R.id.content_frame, fragmentArchiveInfoUser).
                replace(R.id.content_frame, fragmentArchiveInfoUser).
                commit();
    }
}
