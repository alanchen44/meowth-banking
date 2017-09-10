package com.example.group_0715.bankapp_group_0715.firstrunactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.database.DatabaseDriverA;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;

/**
 * Created by Alan on 2017-07-30.
 */

public class FirstRunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/**
        // create new db
        DatabaseDriverA db = new DatabaseDriverA(this);
        db.close();
        // insert roles into db
        insertRoles();
**/
        setContentView(R.layout.first_run_activity);
    }

    /**
    private void insertRoles() {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        insert.insertRole("ADMIN");
        insert.insertRole("TELLER");
        insert.insertRole("CUSTOMER");
        insert.close();
    }
     **/

    public void createAdminFirstRun(View view) {
        Intent intent = new Intent(this, MakeFirstAdminActivity.class);
        startActivity(intent);
    }
}
