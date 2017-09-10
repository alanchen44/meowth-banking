package com.example.group_0715.bankapp_group_0715.startupactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.loginactivity.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void goToAdminPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userType", "admin");
        startActivity(intent);
    }

    public void goToTellerPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userType", "teller");
        startActivity(intent);
    }

    public void goToCustomerPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userType", "customer");
        startActivity(intent);
    }
}
