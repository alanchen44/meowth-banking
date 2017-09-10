package com.example.group_0715.bankapp_group_0715.loginactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.adminactivity.AdminHomeActivity;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Admin;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Customer;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Teller;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.customeractivity.CustomerHomeActivity;
import com.example.group_0715.bankapp_group_0715.telleractivity.TellerHomeActivity;

/**
 * Created by Alan on 2017-07-26.
 */

public class LoginActivity extends AppCompatActivity{

    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent objIntent = this.getIntent();
        userType = objIntent.getStringExtra("userType");

        if (userType.equals("admin")) {
            setContentView(R.layout.activity_admin_login);
        }
        else if (userType.equals("teller")) {
            setContentView(R.layout.activity_teller_login);
        }
        else if (userType.equals("customer")) {
            setContentView(R.layout.activity_customer_login);
        }
    }

    public void submitLoginInfo(View view) {
        EditText userId = (EditText) findViewById(R.id.userId);
        EditText userPassword = (EditText)findViewById(R.id.userPassword);
        String userIdText = userId.getText().toString();
        String passwordText = userPassword.getText().toString();

        boolean authenticationSuccess = false;
        int userIdInt = -1;
        try {
            userIdInt = Integer.parseInt(userIdText);
            DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            User user = select.getUserDetailsInfo(userIdInt, this);
            select.close();
            RoleMap rm = RoleMap.getInstance(this);
            int adminRoleId = rm.findRoleId("ADMIN", this);
            int tellerRoleId = rm.findRoleId("TELLER", this);
            int customerRoleId = rm.findRoleId("CUSTOMER", this);
            if(user == null) {
                authenticationSuccess = false;
            } else if(userType.equals("customer")) {
                if (user.getRoleId() == customerRoleId) {
                    authenticationSuccess = user.authenticate(passwordText, this);
                } else {
                    authenticationSuccess = false;
                }
            } else if(userType.equals("teller")) {
                if (user.getRoleId() == tellerRoleId) {
                    authenticationSuccess = user.authenticate(passwordText, this);
                } else {
                    authenticationSuccess = false;
                }
            } else if(userType.equals("admin")) {
                if (user.getRoleId() == adminRoleId) {
                    authenticationSuccess = user.authenticate(passwordText, this);
                } else {
                    authenticationSuccess = false;
                }
            }
        } catch(NumberFormatException e) {
            authenticationSuccess = false;
        }

        ///////////// DELETE THIS//// JUST FOR TESTING////
        ////THIS SHOULD BE WHERE WE AUTHENTICATE THE USER//////
        TextView backk = (TextView)findViewById(R.id.backButton);
/*        DatabaseSelectHelper s = new DatabaseSelectHelper(this);
        User user = s.getUserDetailsInfo(2, this);
        backk.setText(adminId+" "+tellerId+" "+customerId);
        backk.setText(user.getRoleId()+"");

        User user3 = s.getUserDetailsInfo(3, this);
        backk.setText(user3.getRoleString()+"");
        //////////////////////////////////////////////////////
*/
        if (authenticationSuccess && userIdInt != -1) {
            if (userType.equals("admin")) {
                Intent intent = new Intent(this, AdminHomeActivity.class);
                intent.putExtra("userId", userIdInt);
                startActivity(intent);
                finish();
            }
            else if (userType.equals("teller")) {
                Intent intent = new Intent(this, TellerHomeActivity.class);
                intent.putExtra("userId", userIdInt);
                startActivity(intent);
                finish();
            }
            else if (userType.equals("customer")) {
                Intent intent = new Intent(this, CustomerHomeActivity.class);
                intent.putExtra("customerId", userIdInt);
                startActivity(intent);
                finish();
            }
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
