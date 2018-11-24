package com.example.alexandrumoldovan.utilities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityAssociationCode extends AppCompatActivity implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
    private EditText et_digit1, et_digit2, et_digit3, et_digit4;
    private int whoHasFocus;
    private char[] code = new char[4];
    private Dialog customDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_association_code);
        getSupportActionBar().hide();
        initializeView();
        et_digit1.requestFocus();
        customDialog = new Dialog(ActivityAssociationCode.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setContentView(R.layout.custom_pop_up);
        TextView textView = customDialog.findViewById(R.id.popupTextView);
        textView.setText(R.string.invalid_code);
        CardView cardView = customDialog.findViewById(R.id.okButton);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_digit1.getText().toString().isEmpty())
                    et_digit1.setText("");
                if (!et_digit2.getText().toString().isEmpty())
                    et_digit2.setText("");
                if (!et_digit3.getText().toString().isEmpty())
                    et_digit3.setText("");
                if (!et_digit4.getText().toString().isEmpty())
                    et_digit4.setText("");
                customDialog.dismiss();
                et_digit1.requestFocus();
            }
        });
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_vfcode_digit1:
                whoHasFocus = 1;
                break;

            case R.id.et_vfcode_digit2:
                whoHasFocus = 2;
                break;

            case R.id.et_vfcode_digit3:
                whoHasFocus = 3;
                break;

            case R.id.et_vfcode_digit4:
                whoHasFocus = 4;
                break;

            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (whoHasFocus) {
            case 1:
                if (!et_digit1.getText().toString().isEmpty()) {
                    code[0] = et_digit1.getText().toString().charAt(0);
                    et_digit2.requestFocus();
                }
                break;

            case 2:
                if (!et_digit2.getText().toString().isEmpty()) {
                    code[1] = et_digit2.getText().toString().charAt(0);
                    et_digit3.requestFocus();
                }
                break;

            case 3:
                if (!et_digit3.getText().toString().isEmpty()) {
                    code[2] = et_digit3.getText().toString().charAt(0);
                    et_digit4.requestFocus();
                }
                break;

            case 4:
                if (!et_digit4.getText().toString().isEmpty()) {
                    code[3] = et_digit4.getText().toString().charAt(0);
                    if (code[0] == '1' && code[1] == '2' && code[2] == '3' && code[3] == '4') {
                        Intent intent = new Intent(this, ActivityUser.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        customDialog.show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                switch (v.getId()) {
                    case R.id.et_vfcode_digit2:
                        if (et_digit2.getText().toString().isEmpty())
                            et_digit1.requestFocus();
                        break;

                    case R.id.et_vfcode_digit3:
                        if (et_digit3.getText().toString().isEmpty())
                            et_digit2.requestFocus();
                        break;

                    case R.id.et_vfcode_digit4:
                        if (et_digit4.getText().toString().isEmpty())
                            et_digit3.requestFocus();
                        break;

                    default:
                        break;
                }
            }
        }
        return false;
    }

    private void initializeView() {
        et_digit1 = findViewById(R.id.et_vfcode_digit1);
        et_digit2 = findViewById(R.id.et_vfcode_digit2);
        et_digit3 = findViewById(R.id.et_vfcode_digit3);
        et_digit4 = findViewById(R.id.et_vfcode_digit4);
        setListeners();
    }

    private void setListeners() {
        et_digit1.addTextChangedListener(this);
        et_digit2.addTextChangedListener(this);
        et_digit3.addTextChangedListener(this);
        et_digit4.addTextChangedListener(this);

        et_digit1.setOnKeyListener(this);
        et_digit2.setOnKeyListener(this);
        et_digit3.setOnKeyListener(this);
        et_digit4.setOnKeyListener(this);

        et_digit1.setOnFocusChangeListener(this);
        et_digit2.setOnFocusChangeListener(this);
        et_digit3.setOnFocusChangeListener(this);
        et_digit4.setOnFocusChangeListener(this);
    }
}