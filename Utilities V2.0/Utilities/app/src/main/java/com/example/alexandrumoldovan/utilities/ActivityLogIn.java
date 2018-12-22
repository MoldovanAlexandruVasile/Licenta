package com.example.alexandrumoldovan.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

import java.util.Objects;

public class ActivityLogIn extends AppCompatActivity {

    private RelativeLayout rellay1;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
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
        getSupportActionBar().hide();
        rellay1 = findViewById(R.id.rellay1);
        handler.postDelayed(runnable, 1500);

        TextView textView = findViewById(R.id.signUpHere);
        SpannableString content = new SpannableString("HERE");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        ConnectionDetector cd = new ConnectionDetector(this);
        if (!cd.isConnected())
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
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
        EditText usernameET = findViewById(R.id.usernameEditTextLogIn);
        EditText passET = findViewById(R.id.passwordEditTextLogIn);
        String username = usernameET.getText().toString();
        String pass = passET.getText().toString();
        if (username.compareTo("admin") == 0) {
            Intent intent = new Intent(getApplicationContext(), ActivityAdmin.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), ActivityUser.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
        //TODO: InvalidCredentialsMessage
    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivitySignUp.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}
