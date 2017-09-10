package com.example.group_0715.bankapp_group_0715.firstrunactivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.database.DatabaseDriverA;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;

import static android.R.attr.value;
import static android.os.Build.ID;

/**
 * Created by Ivo on 7/30/2017.
 */

public class ChooseInterestActivity extends AppCompatActivity {
    // for first-run status
    SharedPreferences prefs = null;

    // Initializing seekbars and textviews
    SeekBar chequingSeek;
    SeekBar savingSeek;
    SeekBar tfsaSeek;
    SeekBar restrictedSavingSeek;
    SeekBar balanceOwingSeek;
    TextView chequingSeekValue;
    TextView savingSeekValue;
    TextView tfsaSeekValue;
    TextView restrictedSavingSeekValue;
    TextView balanceOwingSeekValue;

    // New values to save the interest rates
    BigDecimal interestChequing = new BigDecimal(0);
    BigDecimal interestSaving = new BigDecimal(0);
    BigDecimal interestTFSA = new BigDecimal(0);
    BigDecimal interestRestricted = new BigDecimal(0);
    BigDecimal interestBalanceOwing = new BigDecimal(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_interest_rates_first_run_activity);

        // Initialize the view of the seek bars
        chequingSeek = (SeekBar) findViewById(R.id.seekBarChequing);
        savingSeek = (SeekBar) findViewById(R.id.seekBarSaving);
        tfsaSeek = (SeekBar) findViewById(R.id.seekBarTFSA);
        restrictedSavingSeek = (SeekBar) findViewById(R.id.seekBarRestrictedSaving);
        balanceOwingSeek = (SeekBar) findViewById(R.id.seekBarBalanceOwing);

        // Initialize the view of the text view for the seek bars' values
        chequingSeekValue = (TextView) findViewById(R.id.chequingValueSeek);
        savingSeekValue = (TextView) findViewById(R.id.savingValueSeek);
        tfsaSeekValue = (TextView) findViewById(R.id.tfsaValueSeek);
        restrictedSavingSeekValue = (TextView) findViewById(R.id.restrictedSavingValueSeek);
        balanceOwingSeekValue = (TextView) findViewById(R.id.balanceOwingValueSeek);

        // Listener for the chequing seek bar
        chequingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int newValue, boolean b) {
                // Set the value of the interest rate
                interestChequing = new BigDecimal((float)newValue/1000);
                interestChequing = interestChequing.setScale(2, RoundingMode.CEILING);
                // When the user changes the value, update the text value
                chequingSeekValue.setText(interestChequing.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seek) {
                //  Nothing needs to be done here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seek) {
                // Nothing needs to be done here
            }
        });

        // Listener for the saving seek bar
        savingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int newValue, boolean b) {
                // Set the value of the interest rate
                interestSaving = new BigDecimal((float) newValue/1000);
                interestSaving = interestSaving.setScale(2, RoundingMode.CEILING);
                // When the user changes the value, update the text value
                savingSeekValue.setText(interestSaving.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seek) {
                //  Nothing needs to be done here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seek) {
                // Nothing needs to be done here
            }
        });

        // Listener for the TFSA seek bar
        tfsaSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int newValue, boolean b) {
                // Set the value of the interest rate
                interestTFSA = new BigDecimal((float) newValue/1000);
                interestTFSA = interestTFSA.setScale(2, RoundingMode.CEILING);

                // When the user changes the value, update the text value
                tfsaSeekValue.setText(String.valueOf(interestTFSA.toString()));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seek) {
                //  Nothing needs to be done here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seek) {
                // Nothing needs to be done here
            }
        });

        // Listener for the restricted savings seek bar
        restrictedSavingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int newValue, boolean b) {
                // Set the value of the interest rate
                interestRestricted = new BigDecimal((float) newValue/1000);
                interestRestricted = interestRestricted.setScale(2, RoundingMode.CEILING);

                // When the user changes the value, update the text value
                restrictedSavingSeekValue.setText(interestRestricted.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seek) {
                //  Nothing needs to be done here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seek) {
                // Nothing needs to be done here
            }
        });

        // Listener for the balance owing seek bar
        balanceOwingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seek, int newValue, boolean b) {
                // Set the value of the interest rate
                interestBalanceOwing = new BigDecimal((float) newValue/1000);
                interestBalanceOwing = interestBalanceOwing.setScale(2, RoundingMode.CEILING);
                // When the user changes the value, update the text value
                balanceOwingSeekValue.setText(interestBalanceOwing.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seek) {
                //  Nothing needs to be done here
            }

            @Override
            public void onStopTrackingTouch(SeekBar seek) {
                // Nothing needs to be done here
            }
        });

        // sharedpreferences for this app
        prefs = getSharedPreferences("com.example.group_0715.bankapp_group_0715", MODE_PRIVATE);

    }

    public void finishButton(View view) {
        // Make database here
        // create new db
        DatabaseDriverA db = new DatabaseDriverA(this);
        db.close();

        // insert roles into db
        insertRoles();

        // insert roles firstAdmin     TODO and disable firstrun status
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("firstAdminName");
        int age = intent.getIntExtra("firstAdminAge", -1);
        String address = intent.getStringExtra("firstAdminAddress");
        String pass = intent.getStringExtra("firstAdminPass");
        int adminId = insertFirstAdmin(name, age, address, pass);

        // insert accounttypes
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        long chequingId = insert.insertAccountType("CHEQUING", interestChequing);
        long savingId = insert.insertAccountType("SAVING", interestSaving);
        long tfsaId = insert.insertAccountType("TFSA", interestTFSA);
        long rSavingId = insert.insertAccountType("RESTRICTEDSAVING", interestRestricted);
        long bOwingId = insert.insertAccountType("BALANCEOWING", interestBalanceOwing);
        insert.close();

        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
        chequingId = accTypeMap.getAccountTypeId("CHEQUING", this);
        savingId = accTypeMap.getAccountTypeId("SAVING", this);
        tfsaId = accTypeMap.getAccountTypeId("TFSA", this);
        rSavingId = accTypeMap.getAccountTypeId("RESTRICTEDSAVING", this);
        bOwingId = accTypeMap.getAccountTypeId("BALANCEOWING", this);

        // set first-run to false, since setup has been completed
        prefs.edit().putBoolean("firstrun", false).commit();


        AlertDialog.Builder successBox = new AlertDialog.Builder(this);
        successBox.setPositiveButton("Proceed to Main Menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ChooseInterestActivity.this, WelcomeActivity.class);
                startActivity(intent);
                // clear activity stack
                finishAffinity();
            }
        });
        successBox.setMessage("Account types and admin ID created successfully. Your new admin ID is: " + adminId+ ".");
        successBox.setTitle("Success");
        successBox.setCancelable(false);
        successBox.create().show();
    }


    private int insertFirstAdmin(String name, int age, String address, String pass) {
        RoleMap roleMap = RoleMap.getInstance(this);
        int roleId = roleMap.findRoleId("ADMIN", this);
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        int newId = (int) insert.insertNewUser(name, age, address, roleId, pass);
        insert.close();

        return newId;
    }

    private void insertRoles() {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        insert.insertRole("ADMIN");
        insert.insertRole("TELLER");
        insert.insertRole("CUSTOMER");
        insert.close();
    }

}
