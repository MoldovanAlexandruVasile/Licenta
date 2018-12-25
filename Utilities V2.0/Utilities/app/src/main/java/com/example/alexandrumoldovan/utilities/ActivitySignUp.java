package com.example.alexandrumoldovan.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alexandrumoldovan.utilities.Domain.Admin;
import com.example.alexandrumoldovan.utilities.Domain.User;

import java.util.List;
import java.util.Objects;

public class ActivitySignUp extends AppCompatActivity {
    public static User user;
    private static List<User> users = ActivityLogIn.users;
    private static List<Admin> admins = ActivityLogIn.admins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = null;
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();

        Button signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUniqueEmail())
                    goToAddressScreen();
                else showOkPopUp("This account already exists !");
            }
        });
    }

    private Boolean isUniqueEmail() {
        EditText emailET = findViewById(R.id.usernameEditTextSignUp);
        String email = emailET.getText().toString();
        for(User user : users)
            if (user.getEmail().equals(email))
                return false;

        for(Admin admin : admins)
            if (admin.getEmail().equals(email))
                return false;
        return true;
    }

    public void goToAddressScreen() {
        EditText emailET = findViewById(R.id.usernameEditTextSignUp);
        String email = emailET.getText().toString();
        EditText passET = findViewById(R.id.passwordEditTextSignUp);
        String pass = passET.getText().toString();
        EditText repPassET = findViewById(R.id.repeatPasswordEditTextSignUp);
        String repPass = repPassET.getText().toString();
        EditText apartmentET = findViewById(R.id.apartmentNumberSignUp);
        String apartment = apartmentET.getText().toString();
        EditText nameET = findViewById(R.id.nameSignUp);
        String name = nameET.getText().toString();
        if (pass.equals(repPass) && !email.isEmpty() && !pass.isEmpty() && !apartment.isEmpty() && !name.isEmpty()) {
            user = new User(email, pass, name, "", Integer.valueOf(apartment));
            Intent intent = new Intent(getApplicationContext(), ActivityAddressList.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        } else if (!pass.equals(repPass)) {
            showOkPopUp("The passwords do not match !");
        } else showOkPopUp("Make sure all fields are completed");
    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivitySignUp.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_exit_pop_up);
        TextView textView = customDialog.findViewById(R.id.exitPopupTextView);
        textView.setText(R.string.cancel_sign_up);
        CardView yesCardView = customDialog.findViewById(R.id.yesPopUpCardView);

        yesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    private void showOkPopUp(String message) {
        final Dialog customDialog = new Dialog(ActivitySignUp.this);
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

    public void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }
}
