package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

/**
 * Created by Alan on 2017-07-31.
 */

public class AdminMakeUserSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_make_user_selection_activity);

    }

    public void goToMakeAdminPage(View view) {
        Intent intent = new Intent(this, AdminMakeAdminActivity.class);
        startActivity(intent);
    }

    public void goToMakeTellerPage(View view) {
        Intent intent = new Intent(this, AdminMakeTellerActivity.class);
        startActivity(intent);
    }


    public void goBack(View view){
        super.onBackPressed();
    }


}
