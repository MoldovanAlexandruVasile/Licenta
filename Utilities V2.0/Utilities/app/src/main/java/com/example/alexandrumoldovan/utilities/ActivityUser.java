package com.example.alexandrumoldovan.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alexandrumoldovan.utilities.Domain.Contract;
import com.example.alexandrumoldovan.utilities.Domain.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.CONTRACT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.INSERT_CONTRACT_URL;
import static com.example.alexandrumoldovan.utilities.AppUtils.DataVariables.USER_URL;

public class ActivityUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        populateUsers();

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new FragmentHomeUser())
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private Boolean isUserInContracts(Integer id) {
        for (Contract contract : ActivityLogIn.contracts) {
            if (contract.getUser().equals(id))
                return true;
        }
        return false;
    }

    private void performLogOut() {
        Toast.makeText(getApplicationContext(), "You've been logged out.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText(R.string.exit);
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogOut();
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
        getMenuInflater().inflate(R.menu.activity_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.action_information_all_users) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentInfoAllUsers())
                    .commit();
        } else if (id == R.id.action_about_all_users) {
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
        if (id == R.id.nav_profile_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentProfileUser())
                    .commit();
        } else if (id == R.id.nav_payment_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentResourcesUser())
                    .commit();
        } else if (id == R.id.nav_settings_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentSettingsUser())
                    .commit();
        } else if (id == R.id.nav_home_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentHomeUser())
                    .commit();
        } else if (id == R.id.nav_logoff_user) {
            confirmLogOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String title) {
        try {
            getSupportActionBar().setTitle(title);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }

    public void confirmLogOut() {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to log out?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogOut();
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

    public void showNoOfPplInfo(View view) {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText(R.string.persons_lived_in_apartament);
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

    public void showGarageCareInfo(View view) {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText(R.string.charged_for_park_spot);
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

    public void showCleaningInfo(View view) {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText(R.string.charged_for_cleaning);
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

    public void showReparationsInfo(View view) {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText(R.string.charged_for_reparations);
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

    public void redirectToMail(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"qamytesting@yahoo.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Utilities");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose application"));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void goToHistoryFragment(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentArchiveUser())
                .commit();
    }

    public void goToSettingsUser(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentSettingsUser())
                .commit();
    }

    public void goToHistoryFragmentV2(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentArchiveUser())
                .commit();
    }

    public void goToPredictFragment(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentPredictUser())
                .commit();
    }

    public void goToPredictFragmentV2(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentPredictUser())
                .commit();
    }

    public void infoAI(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentInfoOmniUser())
                .commit();
    }

    public void goToChangePassFragment(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentChangePasswordUser())
                .commit();
    }

    public void saveUserName(View view) {
    }

    public void goToElectricity(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentResourcesElectricityUser())
                .commit();
    }

    public void goToResourceGas(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentResourcesGasUser())
                .commit();
    }

    public void goBackToResource(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentResourcesUser())
                .commit();
    }

    public void goToResourceWater(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentResourcesWaterUser())
                .commit();
    }

    public void goToResourcePeople(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentResourcesPeopleUser())
                .commit();
    }

    private void populateUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, USER_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("REST USER", response.toString());
                        try {
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray resultsArray = responseObject.getJSONArray("user");
                            ActivityLogIn.users = new ArrayList<>();
                            for (Integer i = 0; i < resultsArray.length(); i++) {
                                User localUser = new Gson().fromJson(resultsArray.get(i).toString(), User.class);
                                ActivityLogIn.users.add(localUser);
                                if (localUser.getEmail().equals(ActivityLogIn.user.getEmail())) {
                                    ActivityLogIn.user.setID(localUser.getID());
                                    if (!isUserInContracts(localUser.getID())) {
                                        insertUserInContracts(localUser.getID());
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REST USER Response", error.toString());
                    }
                });
        requestQueue.add(objectRequest);
    }

    private void insertUserInContracts(final Integer id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, INSERT_CONTRACT_URL, new Response.Listener<String>() {
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
                Contract contract = new Contract(id, "false", "false", "false", "false");
                ActivityLogIn.contracts.add(contract);
                Log.e("REST INSERT CONTRACT", String.valueOf(id));
                parameters.put("user", String.valueOf(id));
                parameters.put("water", "false");
                parameters.put("gas", "false");
                parameters.put("electricity", "false");
                parameters.put("garage", "false");
                return parameters;
            }
        };
        requestQueue.add(request);
    }
}