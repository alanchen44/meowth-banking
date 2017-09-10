package com.example.group_0715.bankapp_group_0715.telleractivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.transactionactivity.DepositActivity;
import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;
import com.example.group_0715.bankapp_group_0715.transactionactivity.WithdrawActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static android.R.attr.accountType;
import static android.R.attr.id;

/**
 * Created by Alan on 2017-07-28.
 */

public class TellerAccountDetailsActivity extends AppCompatActivity {

    private int account_id = -1;
    private int customer_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teller_account_details_activity);
    }

    // NOTE: after onCreate(), program proceed to onResume() as part of Activity Lifecycle
    @Override
    protected void onResume() {
        super.onResume();
        displayAccountInfo();
    }

    private void displayAccountInfo() {
        Intent objIntent = this.getIntent();
        account_id = objIntent.getIntExtra("accId", -1);
        customer_id = objIntent.getIntExtra("customerId", -1);
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

        select.close();
    }

    public void withdrawRequest(View view) {

        Intent intent = new Intent(this, WithdrawActivity.class);
        intent.putExtra("accountId", account_id);
        intent.putExtra("customerId", customer_id);
        startActivity(intent);

    }

    public void depositRequest(View view) {
        Intent intent = new Intent(this, DepositActivity.class);
        intent.putExtra("accountId", account_id);
        startActivity(intent);
    }

    public void giveInterest(View view) {
        // obtain interest rate and account balance from database
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        BigDecimal currentBalance = select.getBalance(account_id);
        int accType = select.getAccountType(account_id);
        BigDecimal interestRate = select.getInterestRate(accType);
        select.close();
        BigDecimal calculateInterest = currentBalance.multiply(interestRate);
        // calculate interest, add to current balance, and update new balance to database
        BigDecimal newBalance = currentBalance.add(calculateInterest);
        newBalance = newBalance.setScale(2, RoundingMode.HALF_UP);
        DatabaseUpdateHelper update = new DatabaseUpdateHelper(this);
        update.updateAccountBalance(newBalance, account_id);

        // display updated account info to the screen
        displayAccountInfo();

        AlertDialog.Builder interestBox = new AlertDialog.Builder(this);
        interestBox.setMessage("Interest applied. Please check the account balance.");
        interestBox.setTitle("Success");
        interestBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        interestBox.setCancelable(false);
        interestBox.create().show();


        String message = getResources().getString(R.string.interestAdded)
                + " " + account_id + ".";
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        insert.insertMessage(customer_id, message);
        insert.close();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

}
