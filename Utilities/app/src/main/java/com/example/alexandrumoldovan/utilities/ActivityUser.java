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

import java.util.Objects;

public class ActivityUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager.beginTransaction().
                replace(R.id.content_frame, new FragmentHomeUser())
                .commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityUser.this);
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
        getMenuInflater().inflate(R.menu.user, menu);
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

        if (id == R.id.nav_history_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentHistoryUser())
                    .commit();
        } else if (id == R.id.nav_payment_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentResourcesUser())
                    .commit();
        } else if (id == R.id.nav_settings_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentSettingsUser())
                    .commit();
        } else if (id == R.id.nav_predict_user) {
            fragmentManager.beginTransaction().
                    replace(R.id.content_frame, new FragmentPredictUser())
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
    }

    public void showNoOfPplInfo(View view) {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText("Please choose the number of people that lived in the house more than 20 days a month.");
        CardView cardView = customDialog.findViewById(R.id.popupCardView);

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
        textView.setText("You will be charged for keeping the garage clean if you are using a parking spot in it.");
        CardView cardView = customDialog.findViewById(R.id.popupCardView);

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
        textView.setText("You will be charged for the cleaning of the apartment building each month.");
        CardView cardView = customDialog.findViewById(R.id.popupCardView);

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
        textView.setText("You will be charged if the apartment building needs any reparations.");
        CardView cardView = customDialog.findViewById(R.id.popupCardView);

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
    }

    @SuppressLint("ClickableViewAccessibility")
    public void goToHistoryFragment(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentHistoryUser())
                .commit();
    }

    public void goToPredictFragment(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentPredictUser())
                .commit();
    }

    public void infoAI(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentInfoOmniUser())
                .commit();
    }

    public void checkPasswords(View view) {
        //TODO: check if the old password it's correct too
        EditText newPassEditText = findViewById(R.id.newPasswordTextInputUser);
        EditText confirmPassEditText = findViewById(R.id.confirmPasswordTextInputUser);
        String newPass = newPassEditText.getText().toString();
        String confirmPass = confirmPassEditText.getText().toString();
        //TODO: Old Password field must be filled
        if (newPass.compareTo("") == 0 || confirmPass.compareTo("") == 0) {
            this.completePasswordFields();
        } else {
            if (newPass.compareTo(confirmPass) == 0) {
                this.changePasswordSuccess();
            } else {
                this.changePasswordFailed();
            }
        }
    }

    private void completePasswordFields() {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText("Please complete all the fields !");
        CardView cardView = customDialog.findViewById(R.id.popupCardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void changePasswordSuccess() {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText("The password was changed with success !");
        CardView cardView = customDialog.findViewById(R.id.popupCardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                goToSettings();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void changePasswordFailed() {
        final Dialog customDialog = new Dialog(ActivityUser.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText("Passwords do not match !");
        CardView cardView = customDialog.findViewById(R.id.popupCardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    public void goToSettings() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentSettingsUser())
                .commit();
    }

    public void goToChangePassFragment(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentChangePasswordUser())
                .commit();
    }

    public void goToSettingsUser(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentSettingsUser())
                .commit();
    }
}