package com.sigma.bkash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText number, pin;
    private Button nextButton;
    private TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.darkGray));


        number = findViewById(R.id.accNumber_Id);
        pin = findViewById(R.id.pin_Id);
        nextButton = findViewById(R.id.nextButton_Id);
        reg = findViewById(R.id.registration_Id);

        number.setText("+88 ");

        number.addTextChangedListener(loginWatcher);
        pin.addTextChangedListener(loginWatcher);

        reg.setOnClickListener(this);

    }

    private TextWatcher loginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String id = number.getText().toString().trim();
            String pass = pin.getText().toString().trim();

            if (id.length() == 15 && pass.length() >= 4) {
                nextButton.setEnabled(true);
                nextButton.setBackgroundColor(getResources().getColor(R.color.PrimaryColor));
                nextButton.setOnClickListener(MainActivity.this);
            }
            else {
                nextButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.nextButton_Id) {
            verifyUser();
        }

        if (v.getId() == R.id.registration_Id) {
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
        }
    }

    private void verifyUser() {

        String phoneNo = number.getText().toString();
        String pass = pin.getText().toString();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = myRef.orderByChild("phone").equalTo(phoneNo);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String UserGivenPhone = snapshot.child(phoneNo).child("phone").getValue(String.class);
                    String UserGivenPass = snapshot.child(pass).child("password").getValue(String.class);

                    if (pass.equals(UserGivenPass) && phoneNo.equals(UserGivenPhone)){

                        startActivity(new Intent(MainActivity.this, DashBoard.class));

                        Toast.makeText(MainActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Phone or password incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}