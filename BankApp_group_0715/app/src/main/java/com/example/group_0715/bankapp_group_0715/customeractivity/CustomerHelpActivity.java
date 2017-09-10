package com.example.group_0715.bankapp_group_0715.customeractivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

/**
 * Created by Alan on 2017-07-27.
 */

public class CustomerHelpActivity extends AppCompatActivity {

    private int customerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // store the same customer id from login
        Intent objIntent = this.getIntent();
        customerId = objIntent.getIntExtra("customerId", -1);

        setContentView(R.layout.customer_help_activity);
    }

    public void performLogout(View view){
        // ADD DEAUTHENTICATTION CODE HERE!!!!!

        AlertDialog.Builder logoutBox = new AlertDialog.Builder(this);
        logoutBox.setMessage("You have logged out successfully.");
        logoutBox.setTitle("Success");
        logoutBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CustomerHelpActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        logoutBox.create().show();
    }

    public void goBack(View view){
        super.onBackPressed();
    }
}
