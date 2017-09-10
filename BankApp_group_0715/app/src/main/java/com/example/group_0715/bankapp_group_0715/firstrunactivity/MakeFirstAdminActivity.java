package com.example.group_0715.bankapp_group_0715.firstrunactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypes;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alan on 2017-07-30.
 */

public class MakeFirstAdminActivity extends AppCompatActivity {

    private String firstAdminName;
    private int firstAdminAge;
    private String firstAdminAddress;
    private String firstAdminPass;

    // regex for valid age input
    private final String EXPRESSION = "^[0-9]+$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_make_admin_activity);
    }


    public void makeAdmin(View view) {
        EditText nameField = (EditText) findViewById(R.id.namePrompt);
        String name = nameField.getText().toString();
        EditText ageField = (EditText) findViewById(R.id.agePrompt);
        String ageText = ageField.getText().toString();
        EditText addressField = (EditText) findViewById(R.id.addressPrompt);
        String address = addressField.getText().toString();
        EditText passwordField = (EditText) findViewById(R.id.passwordPrompt);
        String passwordText = passwordField.getText().toString();

        boolean successState = false;
        if (matchRegex(ageText)) {
            try {
                int age = Integer.parseInt(ageText);
                /////CHECK THE INPUTS/////////////

                if (!name.equals("") && !ageText.equals("") && !address.equals("") && !passwordText.equals("")
                        && (address.length() <= 100 || age >= 0)) {
                    //// DATABASE CREATE NEW USER///////

                    /**
                    RoleMap roleMap = RoleMap.getInstance(this);
                    int roleId = roleMap.findRoleId("ADMIN", this);

                    DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
                    newId = (int) insert.insertNewUser(name, age, address, roleId, passwordText);

                    insert.close();
                     **/
                    firstAdminName = name;
                    firstAdminAge = age;
                    firstAdminAddress = address;
                    firstAdminPass = passwordText;

                    successState = true;
                } else {
                    successState = false;
                }
            } catch (NumberFormatException e) {
                successState = false;
            }
        }
        // make a pop out box that shows whether or not the user was successfully created
        String printMessage;
        String successString;
        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        if(successState) {
            printMessage = "This admin ID will created once you complete the entire setup on the next page.";
            successString = getResources().getString(R.string.Success);
            successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MakeFirstAdminActivity.this, ChooseInterestActivity.class);
                    intent.putExtra("firstAdminName", firstAdminName);
                    intent.putExtra("firstAdminAge", firstAdminAge);
                    intent.putExtra("firstAdminAddress", firstAdminAddress);
                    intent.putExtra("firstAdminPass", firstAdminPass);
                    startActivity(intent);
                }
            });
        } else {
            successString = getResources().getString(R.string.Error);
            printMessage = getResources().getString(R.string.incompleteInput);
            successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }

        successBox.setMessage(printMessage);
        successBox.setTitle(successString);

        successBox.create().show();
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

    public void goBack(View view){
        super.onBackPressed();
    }

}
