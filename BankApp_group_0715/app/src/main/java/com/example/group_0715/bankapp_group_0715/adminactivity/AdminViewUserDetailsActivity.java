package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import static com.example.group_0715.bankapp_group_0715.R.id.userId;

/**
 * Created by Alan on 2017-07-31.
 */

public class AdminViewUserDetailsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_display_activity);

        Intent intent = this.getIntent();
        int id = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age",-1);
        String address = intent.getStringExtra("address");

        TextView profileName = (TextView) findViewById(R.id.profileName);
        TextView profileAge = (TextView) findViewById(R.id.profileAge);
        TextView profileAddress = (TextView) findViewById(R.id.profileAddress);

        profileName.setText(name);
        profileAge.setText(age+"");
        profileAddress.setText(address);

    }

    public void goBack(View view){
        super.onBackPressed();
    }


}
