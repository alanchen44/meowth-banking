package com.example.group_0715.bankapp_group_0715.telleractivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.adminactivity.AdminHomeActivity;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.messageactivity.ViewMessages;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

import java.util.List;

/**
 * Created by Alan on 2017-07-28.
 */

public class TellerHomeActivity extends AppCompatActivity {
    private int userId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teller_home_activity);

        Intent intent = this.getIntent();
        userId = intent.getIntExtra("userId", -1);

        TextView helloUser = (TextView) findViewById(R.id.helloUser);
        TextView profileName = (TextView) findViewById(R.id.profileName);
        TextView profileAge = (TextView) findViewById(R.id.profileAge);
        TextView profileAddress = (TextView) findViewById(R.id.profileAddress);

        /////get profile information from database and set it////
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        User teller = select.getUserDetailsInfo(userId, this);
        select.close();
        helloUser.setText("Hello, " + teller.getName());
        profileName.setText(teller.getName());
        profileAge.setText(teller.getAge()+"");
        profileAddress.setText(teller.getAddress());
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayNewMessageNotification();
    }

    private void displayNewMessageNotification() {
        // set color and number of new messages
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> allUserMsgId = select.getAllMessagesList(userId);

        int count = 0;
        for (int curId : allUserMsgId) {
            int viewed = select.getMessageViewed(curId);
            if(viewed == 0) {
                count += 1;
            }
        }
        TextView notificationView = (TextView) findViewById(R.id.notificationMessage);
        String newMsgString;
        if(count > 0) {
            notificationView.setTextColor(getResources().getColor(R.color.colorOrangeNotification));

            if (count == 1) {
                newMsgString = getResources().getString(R.string.youHave)
                        + " " + count + " " +
                        getResources().getString(R.string.newMessage);
            } else {
                newMsgString = getResources().getString(R.string.youHave)
                        + " " + count + " " +
                        getResources().getString(R.string.newMessages);
            }
        } else {
            notificationView.setTextColor(Color.WHITE);
            newMsgString = getResources().getString(R.string.youHave)
                    + " " + count + " " +
                    getResources().getString(R.string.newMessages);
        }
        notificationView.setText(newMsgString);
        select.close();
    }


    public void goToTellerTerminal(View view) {
        Intent intent = new Intent(this, TellerTerminalActivity.class);
        startActivity(intent);
    }

    public void goToTellerHelp(View view) {
        Intent intent = new Intent(this, TellerHelpActivity.class);
        startActivity(intent);
    }

    public void goToViewMessages(View view) {
        Intent intent = new Intent(this, ViewMessages.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void performLogout(View view){
        // ADD DEAUTHENTICATTION CODE HERE!!!!!

        AlertDialog.Builder logoutBox = new AlertDialog.Builder(this);
        logoutBox.setMessage("You have logged out successfully.");
        logoutBox.setTitle("Success");
        logoutBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TellerHomeActivity.this, WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        logoutBox.create().show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder logoutBox = new AlertDialog.Builder(this);
        logoutBox.setMessage("You have logged out successfully. Returning to main menu.");
        logoutBox.setTitle(getResources().getString(R.string.exit));
        logoutBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(TellerHomeActivity.this, WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        logoutBox.setCancelable(false);
        logoutBox.create().show();
    }

}
