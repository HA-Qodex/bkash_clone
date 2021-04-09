package com.sigma.bkash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends AppCompatActivity implements View.OnClickListener {

    private EditText phone, pin;
    private Button nextButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.darkGray));

        toolbar = findViewById(R.id.customToolbar_Id);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone = findViewById(R.id.regNumber_Id);
        pin = findViewById(R.id.regiPIN_Id);
        nextButton = findViewById(R.id.regiNextButton_Id);

        phone.setText("+88 ");

        phone.addTextChangedListener(regTextChecker);
        pin.addTextChangedListener(regTextChecker);

        nextButton.setOnClickListener(this);

    }


    private TextWatcher regTextChecker = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String Number = phone.getText().toString().replace(" ", "");
            String pass = pin.getText().toString().replace("", "");

            if (Number.length() == 14 && pass.length() >= 4) {

                nextButton.setEnabled(true);
                nextButton.setBackgroundColor(getResources().getColor(R.color.PrimaryColor));
            } else {
                nextButton.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.regiNextButton_Id) {

            String UserNumber = phone.getText().toString().replace(" ", "");
            String UserPass = pin.getText().toString().replace("", "");

            if (UserNumber.isEmpty() && UserPass.isEmpty()) {
                nextButton.setEnabled(false);
                Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();


            } else {

                nextButton.setEnabled(true);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users");
                RegHelper helper = new RegHelper(UserNumber, UserPass);
                myRef.child(UserNumber).setValue(helper);

                Intent intent = new Intent(getApplicationContext(), OTP.class);
                intent.putExtra("phone", UserNumber);
                startActivity(intent);
            }

        }

    }
}