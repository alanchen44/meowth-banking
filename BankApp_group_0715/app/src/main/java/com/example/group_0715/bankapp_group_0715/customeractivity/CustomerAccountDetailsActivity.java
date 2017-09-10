package com.example.group_0715.bankapp_group_0715.customeractivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.transactionactivity.DepositActivity;
import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;
import com.example.group_0715.bankapp_group_0715.transactionactivity.WithdrawActivity;

import org.w3c.dom.Text;

import java.math.BigDecimal;

/**
 * Created by Alan on 2017-07-27.
 */

public class CustomerAccountDetailsActivity extends AppCompatActivity {

    private int customerId = -1;
    private int account_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_account_details_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayAccountInfo();
    }

    private void displayAccountInfo() {
        Intent objIntent = this.getIntent();
        account_id = objIntent.getIntExtra("accId", -1);
        customerId = objIntent.getIntExtra("customerId", -1);
        // it is better to get data from db rahter than passing intent because info is updating
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        Account acc = select.getAccountDetails(account_id, this);
        String accName = acc.getName();
        int accType = select.getAccountType(account_id);
        String accTypeString = select.getAccountTypeName(accType);
        // instead of displaying ENUM string, show better wording
        if(accTypeString.equalsIgnoreCase("CHEQUING")) accTypeString = "Chequing";
        else if(accTypeString.equalsIgnoreCase("SAVING")) accTypeString = "Saving";
        else if(accTypeString.equalsIgnoreCase("TFSA")) accTypeString = "TFSA";
        else if(accTypeString.equalsIgnoreCase("RESTRICTEDSAVING")) accTypeString = "Restricted Saving";
        else if(accTypeString.equalsIgnoreCase("BALANCEOWING")) accTypeString = "Balance Owing";
        // ensure that there are 2 decimal places
        BigDecimal currentBalance = select.getBalance(account_id).setScale(2);
        BigDecimal interestRate = select.getInterestRate(accType).setScale(2); // TFSA crashes on this

        TextView nameText = (TextView) findViewById(R.id.nameText);
        TextView idText = (TextView) findViewById(R.id.accIdText);
        TextView accountTypeText = (TextView) findViewById(R.id.accountTypeText);
        TextView interestRateText = (TextView) findViewById(R.id.interestRateText);
        TextView balanceText = (TextView) findViewById(R.id.balanceText);

        nameText.setText(accName);
        idText.setText(account_id + "");
        accountTypeText.setText(accTypeString);
        interestRateText.setText(interestRate.toString());
        balanceText.setText("$" + currentBalance.toString());

        // disable deposit/withdraw buttons from customer if the account is RESTRICTEDSAVING
        if(accTypeString.equalsIgnoreCase("Restricted Saving")) setRestricted();

        select.close();
    }

    private void setRestricted() {
        // disable account modifying buttons on this page
        // this is the implementation of customers not being able to modify RESTRICTEDSAVING acc
        Button depositButton = (Button) findViewById(R.id.accDetailsDepositButton);
        Button withdrawButton = (Button) findViewById(R.id.accDetailsWithdrawButton);
        depositButton.setClickable(false);
        withdrawButton.setClickable(false);
        depositButton.setAlpha(.5f);
        withdrawButton.setAlpha(.5f);
    }

    public void withdrawRequest(View view) {
        boolean isRestricted = false;
        //////////////CHECK IF ACCOUNT IS RESTRICTED///////////////////

        if(isRestricted) {
            Context context = getApplicationContext();
            CharSequence message = "You do not have permission to modify this restricted account. Please see a teller.";
            Toast popup = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            popup.show();
        } else {
            Intent intent = new Intent(this, WithdrawActivity.class);
            intent.putExtra("customerId", customerId);
            intent.putExtra("accountId", account_id);
            startActivity(intent);
        }
    }

    public void depositRequest(View view) {
        boolean isRestricted = false;
        //////////////CHECK IF ACCOUNT IS RESTRICTED///////////////////

        if(isRestricted) {
            Context context = getApplicationContext();
            CharSequence message = "You do not have permission to modify this restricted account. Please see a teller.";
            Toast popup = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            popup.show();
        } else {
            Intent intent = new Intent(this, DepositActivity.class);
            intent.putExtra("customerId", customerId);
            intent.putExtra("accountId", account_id);
            startActivity(intent);
        }
    }

    public void goBack(View view){
        super.onBackPressed();
    }
}
