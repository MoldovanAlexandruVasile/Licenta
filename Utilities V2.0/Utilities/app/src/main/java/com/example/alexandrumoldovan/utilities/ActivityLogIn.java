package com.example.alexandrumoldovan.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class ActivityLogIn extends Activity {

    RelativeLayout rellay1;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log_in);

        rellay1 = findViewById(R.id.rellay1);

        handler.postDelayed(runnable, 1500);

    }

    @Override
    public void onBackPressed() {
        final Dialog customDialog = new Dialog(ActivityLogIn.this);
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

    public void hideKeyboardLogIn(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (NullPointerException e) {
            Log.d("STATE", e.toString());
        }
    }

    public void checkLogInCredentials(View view) {
        //TODO: must validate credentials
        EditText usernameET = findViewById(R.id.usernameEditTextLogIn);
        EditText passET = findViewById(R.id.passwordEditTextLogIn);
        String username = usernameET.getText().toString();
        String pass = passET.getText().toString();
        if (username.compareTo("admin") == 0) {
            this.finish();
            Intent intent = new Intent(this, ActivityAdmin.class);
            startActivity(intent);
        } else {
            this.finish();
            Intent intent = new Intent(this, ActivityUser.class);
            startActivity(intent);
        }
        //TODO: InvalidCredentialsMessage !!!!!!!!!!
    }
}
