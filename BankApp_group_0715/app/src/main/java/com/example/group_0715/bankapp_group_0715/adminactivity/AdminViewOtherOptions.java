package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacquelinechan on 2017-07-30.
 */

public class AdminViewOtherOptions extends AppCompatActivity {
    private int userId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        userId = intent.getIntExtra("userId", -1);

        setContentView(R.layout.admin_terminal_other_options_selection_activity);
    }

    public void goToSerializationOptions(View view) {
        Intent intent = new Intent(this, AdminSerializeOrDeserializeDatabaseActivity.class);
        startActivity(intent);
    }

    public void goToMakeMessagePage(View view) {
        Intent intent = new Intent(this, AdminMakeMessageActivity.class);
        startActivity(intent);
    }

    public void goToAdminViewAnyMessagePage(View view) {
        Intent intent = new Intent(this, AdminViewAnyMessageActivity.class);
        startActivity(intent);
    }


    public void performTotalBalance(View view) {
        // Obtain the total balance from the admin terminal
        BigDecimal totalBalance = getTotalAccountBalance();

        // Make a pop up box to show the total balance
        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        String printMessage = "The total bank balance is: $" + totalBalance.toString();
        String successString = "View Balance";
        successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close on click
            }
        });
        successBox.setCancelable(false);
        successBox.setMessage(printMessage);
        successBox.setTitle(successString);
        successBox.create().show();
    }


    public void goToHomePage(View view) {
        Intent intent = new Intent(AdminViewOtherOptions.this, AdminHomeActivity.class);
        intent.putExtra("userId", userId);
        // all activities on top of AdminHomeActivity will be removed from back stack
        // goes to previous AdminHomeActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    private BigDecimal getTotalAccountBalance() {
        int current = 1;
        boolean hasNext = true;
        BigDecimal total = new BigDecimal(0);
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        while (hasNext) {
            Account acc = select.getAccountDetails(current, this);
            if (acc != null) {
                total = total.add(acc.getBalance(this));
            } else {
                hasNext = false;
            }
            current += 1;
        }
        select.close();
        return total;
    }

}
