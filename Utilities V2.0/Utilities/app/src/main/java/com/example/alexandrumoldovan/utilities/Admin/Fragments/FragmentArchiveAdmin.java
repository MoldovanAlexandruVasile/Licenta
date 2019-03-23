package com.example.alexandrumoldovan.utilities.Admin.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alexandrumoldovan.utilities.Admin.Activities.ActivityAdmin;
import com.example.alexandrumoldovan.utilities.Models.Report;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.R;

import java.util.List;

public class FragmentArchiveAdmin extends Fragment {
    View archive;
    public static List<Report> reports;
    public static Integer userID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Archive");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (ActivityAdmin.myUsers.size() > 0) {
            archive = inflater.inflate(R.layout.layout_archive_admin, container, false);
            ListView listView = archive.findViewById(R.id.apartmentsListArchive);
            CustomAdapter customAdapter = new CustomAdapter();
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    userID = ActivityAdmin.myUsers.get(position).getID();
                    getActivity().getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                            .replace(R.id.content_frame, new FragmentArchiveMonthsAdmin())
                            .commit();
                }
            });
            return archive;
        } else {
            archive = inflater.inflate(R.layout.layout_empty_stuff, container, false);
            return archive;
        }
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
            viewEvent.setMinimumHeight(100);

            User user = ActivityAdmin.myUsers.get(position);
            TextView apartmentTV = viewEvent.findViewById(R.id.apartmentNumberCustomLayoutTextViewAdmin);
            apartmentTV.setText(String.valueOf(user.getApartment()));
            TextView nameTV = viewEvent.findViewById(R.id.apartmentOwnerCustomLayoutTextViewAdmin);
            nameTV.setText(user.getName());
            return viewEvent;
        }
    }
}
