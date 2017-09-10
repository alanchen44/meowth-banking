package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Teller;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.firstrunactivity.ChooseInterestActivity;
import com.example.group_0715.bankapp_group_0715.firstrunactivity.MakeFirstAdminActivity;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

import static android.R.attr.id;

/**
 * Created by Alan on 2017-07-31.
 */

public class AdminPromoteTellerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_promote_teller_activity);
    }

    public void performPromoteTeller(View view) {
        EditText inputId = (EditText) findViewById(R.id.tellerIdInput);
        String idText = inputId.getText().toString();

        RoleMap rm = RoleMap.getInstance(this);
        int adminId = rm.findRoleId("ADMIN", this);

        boolean success = false;
        int userId = -1;
        try {
            userId = Integer.parseInt(idText);
            DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            User user = select.getUserDetailsInfo(userId, this);
            select.close();
            if (user instanceof Teller) {
                DatabaseUpdateHelper update = new DatabaseUpdateHelper(this);
                success = update.updateUserRole(adminId, userId);
                update.close();
            } else {
                success = false;
            }

        } catch (NumberFormatException e) {
            success = false;
        }

        // make a pop out box that shows whether or not the user was successfully created
        String printMessage;
        String successString;
        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        if (success) {
            printMessage = "Teller with ID " + userId + " has been promoted to Admin.";
            successString = "Success";
            successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        } else {
            successString = "Error";
            printMessage = "The input provided cannot be promoted to admin.";
            successBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        successBox.setMessage(printMessage);
        successBox.setTitle(successString);
        successBox.setCancelable(false);
        successBox.create().show();

    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
