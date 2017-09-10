package com.example.group_0715.bankapp_group_0715.telleractivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypes;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.group_0715.bankapp_group_0715.R.string.balance;

/**
 * Created by Alan on 2017-07-28.
 */

public class TellerMakeAccountActivity extends AppCompatActivity{

    private int customerId;
    private Spinner dropdownMenu;
    private final String EXPRESSION = "^[-]{0,1}[0-9]+[.][0-9]{2}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terminal_make_account_activity);
        // get the customer Id which will be the owner of the new account
        Intent objIntent = this.getIntent();
        customerId = objIntent.getIntExtra("customerId", -1);
        // set up the dropdown menu options
        dropdownMenu = (Spinner)findViewById(R.id.spinner);
        String[] accTypes = new String[]{"Chequing", "Savings", "TFSA", "Restricted Savings", "Balance Owing"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, accTypes);
        dropdownMenu.setAdapter(adapter);
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

    public void makeNewAccount(View view) {
        EditText nameField = (EditText) findViewById(R.id.namePrompt);
        String name = nameField.getText().toString();
        EditText balanceField = (EditText) findViewById(R.id.balancePrompt);
        String balanceString = balanceField.getText().toString();

        boolean successState = false;
        long newId = -1;
        if (matchRegex(balanceString)) {
            BigDecimal balance = new BigDecimal(balanceString);

            // switch from dropdown menu string to ENUM strings to insert to database
            String accType = dropdownMenu.getSelectedItem().toString();
            if (accType.equalsIgnoreCase("Chequing")) accType = "CHEQUING";
            else if (accType.equalsIgnoreCase("Savings")) accType = "SAVING";
            else if (accType.equalsIgnoreCase("TFSA")) accType = "TFSA";
            else if (accType.equalsIgnoreCase("Restricted Savings")) accType = "RESTRICTEDSAVING";
            else if (accType.equals("Balance Owing")) accType = "BALANCEOWING";

            boolean canCreate = true;
            // TFSA Restriction: TFSA must have a minimum balance of $1000 on creation
            // BALANCEOWING Restriction: BALANCEOWING must have a negative or zero balance
            if (accType.equalsIgnoreCase("TFSA") && balance.compareTo(new BigDecimal("1000.00")) < 0) {
                canCreate = false;
                successState = false;
            } else if (accType.equalsIgnoreCase("BALANCEOWING")
                    && balance.compareTo(BigDecimal.ZERO) > 0) {
                canCreate = false;
                successState = false;
            }

            if (canCreate) {
                AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
                int accountTypeid = accTypeMap.getAccountTypeId(accType, this);
                DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
                newId = insert.insertAccount(name, balance, accountTypeid); // name, balance, typeId
                long newUserAccount = insert.insertUserAccount(customerId, (int) newId);
                insert.close();
                if ((int) newId > 0) {
                    successState = true;
                }
            }
        }
        // make a pop out box that shows whether or not the account was successfully created
        String printMessage;
        String successString;
        if(successState) {
            printMessage = "You have created a new account. The account ID is: " + newId;
            successString = getResources().getString(R.string.Success);
        } else {
            successString = getResources().getString(R.string.Error);
            printMessage = getResources().getString(R.string.incompleteInput);
        }

        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        successBox.setMessage(printMessage);
        successBox.setTitle(successString);
        successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        successBox.setCancelable(false);
        successBox.create().show();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

}
