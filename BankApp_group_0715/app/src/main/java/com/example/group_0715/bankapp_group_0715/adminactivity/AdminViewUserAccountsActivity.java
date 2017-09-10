package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.group_0715.bankapp_group_0715.filteractivity.FilterAccountActivity;
import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

/**
 * Created by Alan on 2017-07-29.
 */

public class AdminViewUserAccountsActivity extends AppCompatActivity {

    private int customerId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///TODO: LOAD ACCOUNTS FROM DATABASE AND SET IT TO SCROLLVIEW///
/*
        // store the same customer id from login
        Intent objIntent = this.getIntent();
        customerId = objIntent.getIntExtra("customerId", -1);
        // set textiew value to tell the admin which customer they are viewing
        TextView selectedCustomer = (TextView)findViewById(R.id.userIdText);
        selectedCustomer.setText(customerId);
        // set textview value to tell the admin the total balance that this customer has
        TextView totalBalance = (TextView)findViewById(R.id.balanceTotalText);
        String balanceString = getBalance(customerId);
        totalBalance.setText(balanceString);
*/
        setContentView(R.layout.admin_terminal_view_user_accounts_activity);
    }

    private String getBalance(int customerId) {
        String returnBalance="";
        /////TODO:FIND TOTAL BALANCE OF ALL ACCOUNTS HERE////

        return returnBalance;
    }

    public void goToFilterAccountPage(View view) {
        Intent intent = new Intent(this, FilterAccountActivity.class);
        startActivityForResult(intent, 0);
        ////// STILL NEED TO RETRIEVE INFO FROM FILTER/////
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                //String idString = data.getStringExtra("id");
                // TODO: GET FILTER OPTIONS
            }
        }
    }

    public void performResetFilter(View view) {
        //// TODO/////
    }

    public void performLogout(View view){
        /////////////////////////// ADD DEAUTHENTICATTION CODE HERE!!!!!////////////////////////

        AlertDialog.Builder logoutBox = new AlertDialog.Builder(this);
        logoutBox.setMessage("You have logged out successfully.");
        logoutBox.setTitle("Success");
        logoutBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AdminViewUserAccountsActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        logoutBox.create().show();
    }

    public void goBack(View view){
        super.onBackPressed();
    }
}
