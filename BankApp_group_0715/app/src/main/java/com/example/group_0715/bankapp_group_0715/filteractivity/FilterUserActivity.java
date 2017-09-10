package com.example.group_0715.bankapp_group_0715.filteractivity;

import android.app.Activity;
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

import java.math.BigInteger;

import static com.example.group_0715.bankapp_group_0715.R.id.userId;
import static com.example.group_0715.bankapp_group_0715.R.id.userIdText;
import static com.example.group_0715.bankapp_group_0715.R.id.userPassword;

/**
 * Created by Alan on 2017-07-29.
 */

public class FilterUserActivity extends AppCompatActivity {

    private Spinner dropdownMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_user_activity);

        Intent objIntent = this.getIntent();
        // set up the dropdown menu options
        dropdownMenu = (Spinner)findViewById(R.id.spinner);
        String[] accTypes = new String[]{"All", "Admin", "Teller", "Customer"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, accTypes);
        dropdownMenu.setAdapter(adapter);

    }

    public void performFilterUser(View view) {
        EditText nameField = (EditText) findViewById(R.id.filterNameText);
        String name = nameField.getText().toString();
        EditText addressField = (EditText)findViewById(R.id.filterAddressText);
        String address = addressField.getText().toString();
        EditText minAgeField = (EditText) findViewById(R.id.minAgeText);
        String minAgeString = minAgeField.getText().toString();
        EditText maxAgeField = (EditText) findViewById(R.id.maxAgeText);
        String maxAgeString = maxAgeField.getText().toString();

        boolean canFilter = true;

        int minAge = 0;
        if(minAgeString.equals("")) {
            minAge = 0;
        }
        else {
            try {
                minAge = Integer.parseInt(minAgeString);
            } catch (NumberFormatException e) {
                canFilter = false;
            }
        }
        int maxAge = Integer.MAX_VALUE;
        if(maxAgeString.equals("")){
            maxAge = Integer.MAX_VALUE;
        } else {
            try {
                maxAge = Integer.parseInt(maxAgeString);
            } catch (NumberFormatException e) {
                canFilter = false;
            }
        }
        if(minAge > maxAge) canFilter = false;

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
            String userType = dropdownMenu.getSelectedItem().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("operation", "filter");
            resultIntent.putExtra("userType", userType);
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("address", address);
            resultIntent.putExtra("minAge", minAge);
            resultIntent.putExtra("maxAge", maxAge);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    public void performFindUser(View view) {
        EditText userIdField = (EditText) findViewById(R.id.idText);
        String userIdText = userIdField.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("operation", "find_by_id");
        resultIntent.putExtra("id", userIdText);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

}
