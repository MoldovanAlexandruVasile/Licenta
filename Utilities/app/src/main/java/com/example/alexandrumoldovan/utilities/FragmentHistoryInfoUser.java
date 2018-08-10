package com.example.alexandrumoldovan.utilities;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentHistoryInfoUser extends Fragment {

    View historyInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String value = getArguments().getString("Month");
        ((ActivityUser) getActivity()).setActionBarTitle(value);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        historyInfo = inflater.inflate(R.layout.layout_history_information_user, container, false);

        final ListView listView = historyInfo.findViewById(R.id.historyListView);

        // Initializing a new String Array
        String[] values = new String[]{"TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD"
                , "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD"
                , "TBD", "TBD", "TBD", "TBD", "TBD", "TBD", "TBD"};

        // Create a List from String Array elements
        List<String> monthsList = new ArrayList<>(Arrays.asList(values));

        // Create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, monthsList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);


        return historyInfo;
    }
}
