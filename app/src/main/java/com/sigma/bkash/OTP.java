package com.sigma.bkash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView countdown, phoneNo, countDownButton;
    private EditText OTP1, OTP2, OTP3, OTP4, OTP5, OTP6;
    private Button confirm, NoChange;
    private String phoneNumber, VerificationCode;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        toolbar = findViewById(R.id.customToolbar_Id);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        countdown = findViewById(R.id.countDown_Id);
        countDownButton = findViewById(R.id.countdownLine_Id);
        phoneNo = findViewById(R.id.phoneNo_Id);
        phoneNumber = getIntent().getExtras().getString("PhoneNo");
        phoneNo.setText(phoneNumber);

        OTP1 = findViewById(R.id.OTP1_Id);
        OTP2 = findViewById(R.id.OTP2_Id);
        OTP3 = findViewById(R.id.OTP3_Id);
        OTP4 = findViewById(R.id.OTP4_Id);
        OTP5 = findViewById(R.id.OTP5_Id);
        OTP6 = findViewById(R.id.OTP6_Id);

        String PIN_1 = OTP1.getText().toString();
        String PIN_2 = OTP2.getText().toString();
        String PIN_3 = OTP3.getText().toString();
        String PIN_4 = OTP4.getText().toString();
        String PIN_5 = OTP5.getText().toString();
        String PIN_6 = OTP6.getText().toString();

        confirm = findViewById(R.id.OTPConfirm_Id);
        NoChange = findViewById(R.id.NoChange_Id);

        setUpOTPInput();
        sendVerificationCode(phoneNumber);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText( "in " + millisUntilFinished / 1000 + "s");
                countDownButton.setEnabled(false);
            }

            public void onFinish() {
                countdown.setText("");
                countDownButton.setEnabled(true);

            }
        }.start();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verificationCode = PIN_1+PIN_2+PIN_3+PIN_4+PIN_5+PIN_6;

                if (verificationCode.isEmpty()){
                    Toast.makeText(OTP.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    verifyCode(verificationCode);
                }

            }
        });

    }

    private void sendVerificationCode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private void setUpOTPInput() {

        OTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                    OTP2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        OTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                    OTP3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        OTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                    OTP4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        OTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                    OTP5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        OTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                    OTP6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        OTP6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                confirm.setEnabled(true);
                confirm.setBackgroundColor(getResources().getColor(R.color.PrimaryColor));
                startActivity(new Intent(getApplicationContext(), DashBoard.class));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            VerificationCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationCode, code);
        signIN(credential);
    }

    private void signIN(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(OTP.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Intent intent = new Intent(OTP.this, DashBoard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(OTP.this, "You are logged in", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}