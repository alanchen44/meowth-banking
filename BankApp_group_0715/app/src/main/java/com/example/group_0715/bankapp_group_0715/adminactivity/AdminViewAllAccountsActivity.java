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

public class AdminViewAllAccountsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_view_all_accounts_activity);
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
        //// TO DO/////
    }


    public void performLogout(View view){
        /////////////////////////// ADD DEAUTHENTICATTION CODE HERE!!!!!////////////////////////

        AlertDialog.Builder logoutBox = new AlertDialog.Builder(this);
        logoutBox.setMessage("You have logged out successfully.");
        logoutBox.setTitle("Success");
        logoutBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AdminViewAllAccountsActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        logoutBox.create().show();
    }

    public void goBack(View view){
        super.onBackPressed();
    }
}
