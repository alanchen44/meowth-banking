package com.example.group_0715.bankapp_group_0715.transactionactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.TFSA;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Alan on 2017-07-27.
 */

public class WithdrawActivity extends AppCompatActivity {

    private int account_id = -1;
    private int customer_id = -1;
    private final String EXPRESSION = "^[0-9]+[.][0-9]{2}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////// LOAD ACCOUNT DETAILS INFORMATION FROM DATABASE ///////////////////
        setContentView(R.layout.withdraw_activity);
    }

    // after onCreate(), program proceed to onResume()
    @Override
    protected void onResume() {
        super.onResume();
        displayAccountInfo();
    }

    private void displayAccountInfo() {
        Intent objIntent = this.getIntent();
        account_id = objIntent.getIntExtra("accountId", -1);
        customer_id = objIntent.getIntExtra("customerId", -1);
        // it is better to get data from db rather than passing intent because info is updating
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        Account acc = select.getAccountDetails(account_id, this);
        String accName = acc.getName();
        int accType = select.getAccountType(account_id);
        String accTypeString = select.getAccountTypeName(accType);
        // instead of displaying ENUM string, show better wording
        if(accTypeString.equalsIgnoreCase("CHEQUING")) accTypeString = "Chequing";
        else if(accTypeString.equalsIgnoreCase("SAVING")) accTypeString = "Saving";
        else if(accTypeString.equalsIgnoreCase("TFSA")) accTypeString = "TFSA";
        else if(accTypeString.equalsIgnoreCase("RESCTRICTEDSAVING")) accTypeString = "Restricted Saving";
        else if(accTypeString.equalsIgnoreCase("BALANCEOWING")) accTypeString = "Balance Owing";
        // ensure that there are 2 decimal places
        BigDecimal currentBalance = select.getBalance(account_id);
        currentBalance = currentBalance.setScale(2);

        TextView nameText = (TextView) findViewById(R.id.nameText);
        TextView idText = (TextView) findViewById(R.id.accIdText);
        TextView accountTypeText = (TextView) findViewById(R.id.accountTypeText);
        TextView balanceText = (TextView) findViewById(R.id.balanceText);

        nameText.setText(accName);
        idText.setText("" + account_id);
        accountTypeText.setText(accTypeString);
        balanceText.setText("$" + currentBalance.toString());

        select.close();
    }

    private boolean matchRegex(String input) {
        Pattern pattern = Pattern.compile(EXPRESSION);
        Matcher matcher = pattern.matcher(input);

        boolean isValid = false;
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    public void performWithdrawal(View view) {
        boolean success_status = false;
        EditText rText = (EditText) findViewById(R.id.withdrawAmount);
        String amountString = rText.getText().toString();

        // check to see the input is valid
        if (matchRegex(amountString)) {
            try {
                // try to convert the string to a BigDecimal with 2 decimal places
                double amountDouble = Double.valueOf(amountString);
                BigDecimal amount = new BigDecimal(amountDouble);
                amount.setScale(2, RoundingMode.HALF_UP);
                // get current balance before withdrawal
                DatabaseSelectHelper select = new DatabaseSelectHelper(this);
                BigDecimal balanceBeforeDeposit = select.getBalance(account_id);
                // find account type to check for TFSA Restriction (see below)
                int accountTypeId = select.getAccountType(account_id);
                String accountTypeName = select.getAccountTypeName(accountTypeId);
                select.close();
                // performing deposit and updating database
                if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(balanceBeforeDeposit) <= 0) {
                    BigDecimal newBalance = balanceBeforeDeposit.subtract(amount);
                    newBalance = newBalance.setScale(2, RoundingMode.HALF_UP);
                    // update database
                    DatabaseUpdateHelper update = new DatabaseUpdateHelper(this);
                    success_status = update.updateAccountBalance(newBalance, account_id);
                    // TFSA Restriction: change to SAVING if balance falls below $1000
                    if (accountTypeName.equalsIgnoreCase("TFSA")
                            && newBalance.compareTo(new BigDecimal(1000.00)) < 0) {
                        // find the id for SAVING account
                        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
                        int savingId = accTypeMap.getAccountTypeId("SAVING", this);

                        update.updateAccountType(savingId, account_id);

                        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
                        String message = getResources().getString(R.string.accountWithID)
                                + " " + account_id + " "
                                + getResources().getString(R.string.hasBeenChangedAccType);
                        insert.insertMessage(customer_id, message);
                        insert.close();

                    }
                    update.close();
                }
            } catch (Exception e) {
                success_status = false;
            }
        }

        String success_title;
        String success_message;
        if (success_status) {
            success_title = getResources().getString(R.string.Success);
            success_message = "Amount has been withdrawn successfully. Please check the account balance.";
            displayAccountInfo();
        } else {
            success_title = getResources().getString(R.string.Success);
            success_message = getResources().getString(R.string.incompleteInput);
        }

        AlertDialog.Builder interestBox = new AlertDialog.Builder(this);
        interestBox.setMessage(success_message);
        interestBox.setTitle(success_title);
        interestBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        interestBox.setCancelable(false);
        interestBox.create().show();

    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
