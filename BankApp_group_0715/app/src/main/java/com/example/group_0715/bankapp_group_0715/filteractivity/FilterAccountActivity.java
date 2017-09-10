package com.example.group_0715.bankapp_group_0715.filteractivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;
import com.example.group_0715.bankapp_group_0715.transactionactivity.ViewCustomerAccountsActivity;

import java.math.BigDecimal;

import static android.R.attr.id;
import static android.R.attr.max;
import static com.example.group_0715.bankapp_group_0715.R.id.userId;
import static com.example.group_0715.bankapp_group_0715.R.id.userIdText;
import static com.example.group_0715.bankapp_group_0715.R.id.userPassword;
import static com.example.group_0715.bankapp_group_0715.R.string.maxBalance;
import static com.example.group_0715.bankapp_group_0715.R.string.minBalance;

/**
 * Created by Alan on 2017-07-29.
 */

public class FilterAccountActivity extends AppCompatActivity {

    private Spinner dropdownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_account_activity);

        Intent objIntent = this.getIntent();
        // set up the dropdown menu options
        dropdownMenu = (Spinner) findViewById(R.id.spinner);
        String[] accTypes = new String[]{"All", "Chequing", "Saving", "Restricted Saving", "TFSA", "Balance Owing"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, accTypes);
        dropdownMenu.setAdapter(adapter);

    }

    public void performFilterAccount(View view) {
        EditText nameField = (EditText) findViewById(R.id.filterNameText);
        String name = nameField.getText().toString();
        EditText minBalanceField = (EditText) findViewById(R.id.minBalanceText);
        String minBalanceString = minBalanceField.getText().toString();
        EditText maxBalanceField = (EditText) findViewById(R.id.maxBalanceText);
        String maxBalanceString = maxBalanceField.getText().toString();

        boolean canFilter = true;
        // if no input is given, disregard this filter option
        // if an invalid input is given, prompt user again to give a valid input or no input
        double minBalanceDouble = Double.valueOf(Integer.MIN_VALUE);
        // if no input for min balance, disregard this minBalance restriction
        if(minBalanceString.equals("")){
            minBalanceDouble = Double.valueOf(Integer.MIN_VALUE);
        } else {
            try {
                minBalanceDouble = Double.valueOf(minBalanceString);
            } catch (NumberFormatException e) {
                canFilter = false;
            }
        }
        double maxBalanceDouble = Double.valueOf(Integer.MAX_VALUE);
        // if no input for min balance, disregard this minBalance restriction
        if(maxBalanceString.equals("")){
            maxBalanceDouble = Double.valueOf(Integer.MAX_VALUE);
        } else {
            try {
                maxBalanceDouble = Double.valueOf(maxBalanceString);
            } catch (NumberFormatException e) {
                canFilter = false;
            }
        }
        // cannot filter if minBalance is greater than maxBalance
        if(Double.compare(minBalanceDouble, maxBalanceDouble) > 0) canFilter = false;
        // popup dialog if canFilter is false
        if(!canFilter) {
            AlertDialog.Builder errorBox = new AlertDialog.Builder(this);
            errorBox.setMessage("Cannot perform filter. Please check input.");
            errorBox.setTitle("Error");
            errorBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
            errorBox.setCancelable(false);
            errorBox.create().show();
        } else {
            String accType = dropdownMenu.getSelectedItem().toString();
            // convert display string to ENUM string to match information on the database
            if(accType.equalsIgnoreCase("Chequing")) accType = "Chequing";
            else if(accType.equalsIgnoreCase("TFSA")) accType = "TFSA";
            else if(accType.equalsIgnoreCase("Restricted Saving")) accType = "RESTRICTEDSAVING";
            else if(accType.equalsIgnoreCase("Saving")) accType = "SAVING";
            else if(accType.equalsIgnoreCase("Balance Owing")) accType = "BALANCEOWING";

            Intent resultIntent = new Intent();
            resultIntent.putExtra("accType", accType);
            resultIntent.putExtra("operation", "filter");
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("minBalance", minBalanceDouble);
            resultIntent.putExtra("maxBalance", maxBalanceDouble);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    public void performFindAccount(View view) {
        EditText accIdField = (EditText) findViewById(R.id.accIdText);
        String accIdText = accIdField.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("operation", "find_by_id");
        resultIntent.putExtra("id", accIdText);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

}
