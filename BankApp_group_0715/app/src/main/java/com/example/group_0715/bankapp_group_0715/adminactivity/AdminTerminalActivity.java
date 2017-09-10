package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

/**
 * Created by Alan on 2017-07-28.
 */

public class AdminTerminalActivity extends AppCompatActivity {
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_activity);

        Intent intent = this.getIntent();
        userId = intent.getIntExtra("userId", -1);
    }

    public void goToMakeUserSelection(View view) {
        Intent intent = new Intent(this, AdminMakeUserSelectionActivity.class);
        startActivity(intent);
    }

    public void goToViewUserSelection(View View) {
        Intent intent = new Intent(this, AdminViewAllUsers.class);
        startActivity(intent);
    }

    public void performPromoteTeller(View view) {
        Intent intent = new Intent(this, AdminPromoteTellerActivity.class);
        startActivity(intent);
    }

    public void goToViewOtherOptions(View view) {
        Intent intent = new Intent(this, AdminViewOtherOptions.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void closeTerminal(View view) {
        super.onBackPressed();
    }
}

