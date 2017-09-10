package com.example.group_0715.bankapp_group_0715.telleractivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;

/**
 * Created by Alan on 2017-07-28.
 */

public class TellerAuthenticateCustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
    }

    public void submitLoginInfo(View view) {
        EditText userId = (EditText) findViewById(R.id.userId);
        EditText userPassword = (EditText) findViewById(R.id.userPassword);
        String userIdText = userId.getText().toString();
        String passwordText = userPassword.getText().toString();

        boolean authenticationSuccess = false;
        int userIdInt = -1;

        // Get the user ids
        userIdInt = Integer.parseInt(userIdText);
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        User user = select.getUserDetailsInfo(userIdInt, this);
        select.close();

        // Find the role type id to check if they are of the correct type
        RoleMap rm = RoleMap.getInstance(this);
        int customerRoleId = rm.findRoleId("CUSTOMER", this);

        // Attempt to authenticate the customer
        if (user == null) {
            authenticationSuccess = false;
        } else {
            if (user.getRoleId() == customerRoleId) {
                authenticationSuccess = user.authenticate(passwordText, this);
            } else {
                authenticationSuccess = false;
            }
        }

        // If the authentication is successful, send the successful intent, else tell the user
        // that they had an unsuccessful authentication
        if (authenticationSuccess) {
            // give customer id to the previous activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("id", userIdText);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {
            Context context = getApplicationContext();
            CharSequence message = "You have entered incorrect information. Please try again.";
            Toast popup = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            popup.show();
        }
    }

    public void goBack(View view) {
        super.onBackPressed();
    }


}
