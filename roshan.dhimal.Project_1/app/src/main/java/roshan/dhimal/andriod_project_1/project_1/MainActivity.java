package roshan.dhimal.andriod_project_1.project_1;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText phNumEditText;

    private static final int REQUEST_DIALER = 1;
    private static final int REQUEST_MESSAGE = 2;
    private static final String PHONE_NUMBER_KEY = "phNum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_portrait);
        } else {
            setContentView(R.layout.activity_main_landscape);
        }

        phNumEditText = findViewById(R.id.editTextPhoneNumber);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phNum = phNumEditText.getText().toString();
                if (isValidphNum(phNum)) {
                    startDialer(phNum);
                } else {
                    showToast("Invalid phone number: " + phNum);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phNum = phNumEditText.getText().toString();
                if (isValidphNum(phNum)) {
                    startMessaging(phNum);
                } else {
                    showToast("Invalid phone number: " + phNum);
                }
            }
        });

        // Restore phone number on configuration change
        if (savedInstanceState != null) {
            String savedphNum = savedInstanceState.getString(PHONE_NUMBER_KEY);
            phNumEditText.setText(savedphNum);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PHONE_NUMBER_KEY, phNumEditText.getText().toString());
    }

    private boolean isValidphNum(String phNum) {
        return phNum.matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$");
    }

    private void startDialer(String phNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(android.net.Uri.parse("tel:" + phNum));
        startActivity(intent);
    }

    private void startMessaging(String phNum) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(android.net.Uri.parse("smsto:" + phNum));
        intent.putExtra("sms_body", "Hello, from Roshan Dhimal");
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

