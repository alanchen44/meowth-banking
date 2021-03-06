package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;

import static android.R.attr.password;

/**
 * Created by Alan on 2017-07-28.
 */

public class AdminMakeAdminActivity extends AppCompatActivity{

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
        /////CHECK THE INPUTS/////////////
        //// DATABASE CREATE NEW USER///////
        int age = -1;
        int newId = -1;
        try {
            age = Integer.parseInt(ageText);
            if(name.equals("") || ageText.equals("") || address.equals("") || passwordText.equals("")
                    || (address.length() > 100 || age <= 0)) {
                successState = false;
            } else {

                RoleMap roleMap = RoleMap.getInstance(this);
                int roleId = roleMap.findRoleId("ADMIN", this);

                DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
                newId = (int) insert.insertNewUser(name, age, address, roleId, passwordText);
                insert.close();
                successState = true;
            }
        } catch (NumberFormatException e) {
            successState = false;
        }


        // make a pop out box that shows whether or not the user was successfully created
        String printMessage;
        String successString;
        if(successState) {
            printMessage = "You have created a new admin. The account ID is: " + newId;
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

    public void goBack(View view){
        super.onBackPressed();
    }
}
