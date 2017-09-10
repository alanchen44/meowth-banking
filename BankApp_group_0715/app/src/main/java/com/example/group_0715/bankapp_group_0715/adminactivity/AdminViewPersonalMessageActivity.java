package com.example.group_0715.bankapp_group_0715.adminactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.group_0715.bankapp_group_0715.R;

/**
 * Created by jacquelinechan on 2017-07-30.
 */

public class AdminViewPersonalMessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_terminal_view_selectedpersonal_messages);
    }


    public void goBack(View view) {
        super.onBackPressed();
    }
}
