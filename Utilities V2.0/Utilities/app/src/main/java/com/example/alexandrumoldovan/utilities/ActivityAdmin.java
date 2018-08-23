package com.example.alexandrumoldovan.utilities;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


//TODO: Based on how many apartments are registered on "Resource reports" and "Archive" screens, they will be displayed there for being selected
public class ActivityAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toast infoToast;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        String username = getIntent().getStringExtra("username");
        LinearLayout linearLayout = ((NavigationView) (findViewById(R.id.nav_view_admin))).getHeaderView(0).findViewById(R.id.adminNavigation);
        TextView usernameEditText = linearLayout.findViewById(R.id.navHeaderUsernameAdmin);
        usernameEditText.setText(username);
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

    public void checkPasswordsAdmin(View view) {
        //TODO: check if the old password it's correct too
        EditText newPassEditText = findViewById(R.id.newPasswordTextInputAdmin);
        EditText confirmPassEditText = findViewById(R.id.confirmPasswordTextInputAdmin);
        String newPass = newPassEditText.getText().toString();
        String confirmPass = confirmPassEditText.getText().toString();
        //TODO: Old Password field must be filled
        if (newPass.compareTo("") == 0 || confirmPass.compareTo("") == 0) {
            completeAllFields();
        } else {
            if (newPass.compareTo(confirmPass) == 0) {
                changePasswordSuccess();
            } else {
                changePasswordFailed();
            }
        }
    }

    private void completeAllFields() {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
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
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
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
                goToSettingsAdmin();
            }
        });
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.show();
    }

    private void changePasswordFailed() {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
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
        String username = usernameET.getText().toString();
        String pass = passwordET.getText().toString();
        if (username.compareTo("") == 0 || pass.compareTo("") == 0) {
            completeAllFields();
        } else {
            confirmDeleteAccountPoUp(customDialog);
        }
    }

    private void confirmDeleteAccountPoUp(final Dialog prev) {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText("Are you sure you want to delete the account?");
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: delete account
                customDialog.dismiss();
                prev.dismiss();
                infoToast = Toast.makeText(getBaseContext(), "Account deleted", Toast.LENGTH_LONG);
                infoToast.show();
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


    public void goToSettingsAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentSettingsAdmin())
                .commit();
    }

    private void goToSettingsAdmin() {
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

    public void createAccountAdmin(View view) {
        //TODO: check if the username it's available
        EditText usernameET = findViewById(R.id.usernameTextInputAdmin);
        EditText passwordET = findViewById(R.id.newPasswordTextInputAdmin);
        EditText confirmPassET = findViewById(R.id.confirmPasswordTextInputUser);
        String username = usernameET.getText().toString();
        String pass = passwordET.getText().toString();
        String confirmPass = confirmPassET.getText().toString();

        if (username.compareTo("") == 0 || confirmPass.compareTo("") == 0 || pass.compareTo("") == 0) {
            completeAllFields();
        } else if (pass.compareTo(confirmPass) != 0) {
            changePasswordFailed();
        } else {
            accountCreatedWithSuccess();
        }
    }

    private void accountCreatedWithSuccess() {
        final Dialog customDialog = new Dialog(ActivityAdmin.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText("The account has been created with success !");
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

    public void goToArchiveAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.content_frame, new FragmentArchiveAdmin())
                .commit();
    }

    public void goToHomeAdmin(View view) {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right)
                .replace(R.id.content_frame, new FragmentHomeAdmin())
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
}

