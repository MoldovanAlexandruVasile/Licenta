package com.example.alexandrumoldovan.utilities.Admin.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentChargesAdmin;
import com.example.alexandrumoldovan.utilities.Common.ActivityLogIn;
import com.example.alexandrumoldovan.utilities.Common.FragmentAboutAllUsers;
import com.example.alexandrumoldovan.utilities.Models.Admin;
import com.example.alexandrumoldovan.utilities.Models.Charges;
import com.example.alexandrumoldovan.utilities.Models.Event;
import com.example.alexandrumoldovan.utilities.Models.User;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentAccountManagementAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentArchiveAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentArchiveMonthsAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentChangePasswordAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentCreateEventAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentEventsManagementAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentEventsReportsAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentHomeAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentMonthReportsAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentProfileAdmin;
import com.example.alexandrumoldovan.utilities.Admin.Fragments.FragmentSettingsAdmin;
import com.example.alexandrumoldovan.utilities.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.AppUtils.wasInPast;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.DELETE_CONTRACT_BY_USER;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.DELETE_EVENT_USER_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.DELETE_REPORT_BY_USER_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.DELETE_USER_URL;

public class ActivityAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager = getFragmentManager();
    public static List<Event> myEvents;
    public static List<User> myUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fillMyUsers();
        fillMyEvents();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager.beginTransaction().
                replace(R.id.content_frame, new FragmentHomeAdmin())
                .commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void fillMyEvents() {
        myEvents = new ArrayList<>();
        for (Event event : ActivityLogIn.events)
            if (event.getAddress().equals(ActivityLogIn.admin.getAddress())
                    && !myEvents.contains(event))
                if (!wasInPast(event.getDate()))
                    myEvents.add(event);
    }

    private void fillMyUsers() {
        myUsers = new ArrayList<>();
        for (User user : ActivityLogIn.users)
            if (user.getAddress().equals(ActivityLogIn.admin.getAddress())
                    && !myUsers.contains(user))
                myUsers.add(user);
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to exit?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);
        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about_all_users) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentAboutAllUsers())
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentHomeAdmin())
                    .commit();
        } else if (id == R.id.nav_event_admin) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentProfileAdmin())
                    .commit();
        } else if (id == R.id.nav_charges_admin) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentChargesAdmin())
                    .commit();
        } else if (id == R.id.nav_settings_admin) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentSettingsAdmin())
                    .commit();
        } else if (id == R.id.nav_logoff_admin) {
            confirmLogOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }

    private void completeAllFields() {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText("Please complete all the fields !");
        CardView cardView = customDialog.findViewById(R.id.okButton);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void confirmLogOut() {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to log out?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                goToLogInScreen();
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

    private void goToLogInScreen() {
        Intent intent = new Intent(this, ActivityLogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void deleteAccountPopUp(View view) {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.setContentView(R.layout.custom_login_pop_up);
        CardView cardView = customDialog.findViewById(R.id.popupDeleteAccountCardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields(customDialog);
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void checkFields(Dialog customDialog) {
        EditText usernameET = customDialog.findViewById(R.id.usernameEditTextCardView);
        EditText passwordET = customDialog.findViewById(R.id.passwordEditTextCardView);
        String email = usernameET.getText().toString();
        String pass = passwordET.getText().toString();
        if (email.isEmpty() || pass.isEmpty()) {
            completeAllFields();
        } else {
            confirmDeleteAccountPoUp(customDialog, email, pass);
        }
    }

    private void confirmDeleteAccountPoUp(final Dialog prev, final String email, final String pass) {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText(R.string.delete_account_text);
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getUserDetails(email, pass);
                customDialog.dismiss();
                if (user == null)
                    Toast.makeText(getBaseContext(), "Such user does not exist.", Toast.LENGTH_LONG).show();
                else if (user.getAddress().equals(ActivityLogIn.admin.getAddress())) {
                    prev.dismiss();
                    deleteUserFromDB(user);
                    Toast.makeText(getBaseContext(), "Account deleted", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getBaseContext(), "Account cannot be deleted. " +
                            "This account is not registered to one of the apartments in this block.", Toast.LENGTH_LONG).show();
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

    private void deleteUserFromDB(final User user) {
        RequestQueue requestDeleteQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("REST RESPONSE", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REST ERROR", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                String ID = String.valueOf(user.getID());
                Log.e("REST DELETE USER ID ", ID);
                parameters.put("ID", ID);
                return parameters;
            }
        };
        requestDeleteQueue.add(request);
        deleteReportByUser(user);
    }

    private void deleteReportByUser(final User user) {
        RequestQueue requestDeleteQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_REPORT_BY_USER_URL, new Response.Listener<String>() {
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
                String ID = String.valueOf(user.getID());
                Log.e("REST DELETE REPORT ID ", ID);
                parameters.put("user", ID);
                return parameters;
            }
        };
        requestDeleteQueue.add(request);
        deleteEventUserByUser(user);
    }

    private void deleteEventUserByUser(final User user) {
        RequestQueue requestDeleteQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_EVENT_USER_USER_URL, new Response.Listener<String>() {
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
                String ID = String.valueOf(user.getID());
                Log.e("REST DELETE USER ID ", ID);
                parameters.put("user", ID);
                return parameters;
            }
        };
        requestDeleteQueue.add(request);
        deleteContractsByUser(user);
    }

    private void deleteContractsByUser(final User user) {
        RequestQueue requestDeleteQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_CONTRACT_BY_USER, new Response.Listener<String>() {
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
                String ID = String.valueOf(user.getID());
                Log.e("REST DELETE USER ID ", ID);
                parameters.put("user", ID);
                return parameters;
            }
        };
        requestDeleteQueue.add(request);
    }

    private User getUserDetails(String email, String pass) {
        for (User user : ActivityLogIn.users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(pass))
                return user;
        }
        return null;
    }

    public void goToSettingsAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentSettingsAdmin())
                .commit();
    }

    public void goToChangePassFragment(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentChangePasswordAdmin())
                .commit();
    }

    public void goToArchiveAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentArchiveAdmin())
                .commit();
    }

    public void goBackToMonthsArchive(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentArchiveMonthsAdmin())
                .commit();
    }

    public void goBackToArchive(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentArchiveAdmin())
                .commit();
    }

    public void goToHomeAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentHomeAdmin())
                .commit();
    }

    public void goToEventsList(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentEventsReportsAdmin())
                .commit();
    }

    public void goToCreateEventAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentCreateEventAdmin())
                .commit();
    }

    public void goToMonthReportsAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentMonthReportsAdmin())
                .commit();
    }

    public void goToAccountsManagementAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentAccountManagementAdmin())
                .commit();
    }

    public void goToEventsManagementAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentEventsManagementAdmin())
                .commit();
    }

    public void goBackToEventsManagementAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentEventsManagementAdmin())
                .commit();
    }

    public void goToEventsReportsAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentEventsReportsAdmin())
                .commit();
    }

    public void goBackToMonthReports(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentMonthReportsAdmin())
                .commit();
    }
}

