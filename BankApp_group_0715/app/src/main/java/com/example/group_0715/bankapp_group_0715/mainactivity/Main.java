package com.example.group_0715.bankapp_group_0715.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.group_0715.bankapp_group_0715.bank.database.DatabaseDriverA;
import com.example.group_0715.bankapp_group_0715.firstrunactivity.FirstRunActivity;
import com.example.group_0715.bankapp_group_0715.firstrunactivity.MakeFirstAdminActivity;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

import static android.R.attr.start;

/**
 * Created by Alan on 2017-07-30.
 */

public class Main extends Activity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Perhaps set content view here

        prefs = getSharedPreferences("com.example.group_0715.bankapp_group_0715", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // if this is the first run
        if (prefs.getBoolean("firstrun", true)) {
            Intent intent = new Intent(this, FirstRunActivity.class);
            // prefs.edit().putBoolean("firstrun", false).commit();
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
