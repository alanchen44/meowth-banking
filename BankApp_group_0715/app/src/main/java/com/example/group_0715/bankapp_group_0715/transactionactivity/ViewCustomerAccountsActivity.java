package com.example.group_0715.bankapp_group_0715.transactionactivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;
import com.example.group_0715.bankapp_group_0715.customeractivity.CustomerAccountDetailsActivity;
import com.example.group_0715.bankapp_group_0715.filteractivity.FilterAccountActivity;
import com.example.group_0715.bankapp_group_0715.telleractivity.*;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.*;

import org.w3c.dom.Text;

import static android.R.attr.name;
import static com.example.group_0715.bankapp_group_0715.R.id.balanceTotalText;
import static com.example.group_0715.bankapp_group_0715.R.id.userId;
import static com.example.group_0715.bankapp_group_0715.R.string.find;
import static com.example.group_0715.bankapp_group_0715.R.string.maxAge;
import static com.example.group_0715.bankapp_group_0715.R.string.minAge;

/**
 * Created by Alan on 2017-07-27.
 */

public class ViewCustomerAccountsActivity extends AppCompatActivity {

    private int customerId = -1;
    private boolean isTellerTerminal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_customer_accounts_activity);
        // store the same customer id from login
        Intent objIntent = this.getIntent();
        customerId = objIntent.getIntExtra("customerId", -1);
        // check if the previous activity is teller terminal
        isTellerTerminal = objIntent.getBooleanExtra("isTellerTerminal", false);

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        TextView userIdText = (TextView) findViewById(R.id.userIdText);
        userIdText.setText(customerId + "");
        select.close();

        // display total account balance
        displayTotalBalance();

        //Load all accounts onto scroll view
        List<Account> filteredAcc = doAll();
        doFilter(filteredAcc, "", new BigDecimal(Integer.MIN_VALUE), new BigDecimal(Integer.MAX_VALUE));

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTotalBalance();
    }

    private void displayTotalBalance() {
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        BigDecimal total = new BigDecimal(0);
        List<Integer> accIdList = select.getAccountIdsList(customerId);
        for(int cur: accIdList){
            total = total.add(select.getBalance(cur));
        }
        TextView balanceField = (TextView) findViewById(balanceTotalText);
        balanceField.setText(total.toString());
        select.close();
    }

    private List<Account> doAll() {
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> accIdList = select.getAccountIdsList(customerId);
        List<Account> accList = new ArrayList<>();
        for (int cur : accIdList) {
            accList.add(select.getAccountDetails(cur, this));
        }
        select.close();
        return accList;
    }

    private List<Account> doChequing() {
        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
        int chequingId = accTypeMap.getAccountTypeId("CHEQUING", this);

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> accIdList = select.getAccountIdsList(customerId);
        List<Account> accList = new ArrayList<>();
        for (int cur : accIdList) {
            if (select.getAccountType(cur) == chequingId)
                accList.add(select.getAccountDetails(cur, this));
        }
        return accList;
    }

    private List<Account> doSaving() {
        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
        int savingId = accTypeMap.getAccountTypeId("SAVING", this);

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> accIdList = select.getAccountIdsList(customerId);
        List<Account> accList = new ArrayList<>();
        for (int cur : accIdList) {
            if (select.getAccountType(cur) == savingId)
                accList.add(select.getAccountDetails(cur, this));
        }
        return accList;

    }

    private List<Account> doTFSA() {
        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
        int tfsaId = accTypeMap.getAccountTypeId("TFSA", this);

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> accIdList = select.getAccountIdsList(customerId);
        List<Account> accList = new ArrayList<>();
        for (int cur : accIdList) {
            if (select.getAccountType(cur) == tfsaId)
                accList.add(select.getAccountDetails(cur, this));
        }
        return accList;
    }

    private List<Account> doRestrictedSaving() {
        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
        int restrictedId = accTypeMap.getAccountTypeId("RESTRICTEDSAVING", this);

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> accIdList = select.getAccountIdsList(customerId);
        List<Account> accList = new ArrayList<>();
        for (int cur : accIdList) {
            if (select.getAccountType(cur) == restrictedId)
                accList.add(select.getAccountDetails(cur, this));
        }
        return accList;

    }

    private List<Account> doBalanceOwing() {
        AccountTypesMap accTypeMap = AccountTypesMap.getInstance(this);
        int balanceOwingId = accTypeMap.getAccountTypeId("BALANCEOWING", this);

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<Integer> accIdList = select.getAccountIdsList(customerId);
        List<Account> accList = new ArrayList<>();
        for (int cur : accIdList) {
            if (select.getAccountType(cur) == balanceOwingId)
                accList.add(select.getAccountDetails(cur, this));
        }
        return accList;
    }


    private void doFilter(List<Account> accList, String name, BigDecimal min, BigDecimal max) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.accountScroll);
        scrollView.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        int numOfResults = 0;

        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        for (Account cur : accList) {
            if (cur.getName().startsWith(name) && cur.getBalance(this).compareTo(min) >= 0
                    && cur.getBalance(this).compareTo(max) <= 0) {
                // Add Buttons
                final int id = cur.getId();
                final String accName = cur.getName();
                final int accType = cur.getId();

                Button b = new Button(this);
                b.setText("ID: " + id + ",     " + "NAME: " + accName);
                if (isTellerTerminal) {
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewCustomerAccountsActivity.this, TellerAccountDetailsActivity.class);
                            intent.putExtra("accId", id);
                            intent.putExtra("customerId", customerId);
                            startActivity(intent);
                        }
                    });
                } else {
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewCustomerAccountsActivity.this, CustomerAccountDetailsActivity.class);
                            intent.putExtra("accId", id);
                            intent.putExtra("customerId", customerId);
                            startActivity(intent);
                        }
                    });
                }
                numOfResults += 1;
                linearLayout.addView(b);
            }
        }
        select.close();
        scrollView.addView(linearLayout);

        displayNumOfAcc(numOfResults);

    }

    private void displayNumOfAcc(int num) {
        TextView totalAccField = (TextView) findViewById(R.id.findNumAcc);
        totalAccField.setText(num + "");
        // change text to singular form if only 1 account is displayed, plural otherwise
        TextView accDisplayedField = (TextView)findViewById(R.id.accDisplayed);
        if(num == 1) {
            accDisplayedField.setText(R.string.accountDisplayed);
        } else {
            accDisplayedField.setText(R.string.accountsDisplayed);
        }
    }

    private void findAccById(String accIdString) {
        ScrollView scrollView = (ScrollView) findViewById(R.id.accountScroll);
        scrollView.removeAllViews();
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int numOfResults = 0;
        // catch for invalid inputs like empty string or having non-numeric characters
        try {
            int accId = Integer.parseInt(accIdString);
            DatabaseSelectHelper select = new DatabaseSelectHelper(this);
            Account cur = select.getAccountDetails(accId, this);

            if (cur != null) {
                final int id = cur.getId();
                final String accName = cur.getName();
                Button b = new Button(this);
                b.setText("ID: " + id + ",     " + "NAME: " + accName);
                if (isTellerTerminal) {
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewCustomerAccountsActivity.this, TellerAccountDetailsActivity.class);
                            intent.putExtra("accId", id);
                            intent.putExtra("customerId", customerId);
                            startActivity(intent);
                        }
                    });
                } else {
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewCustomerAccountsActivity.this, CustomerAccountDetailsActivity.class);
                            intent.putExtra("accId", id);
                            intent.putExtra("customerId", customerId);
                            startActivity(intent);
                        }
                    });
                }
                select.close();
                numOfResults += 1;
                linearLayout.addView(b);
            }
        } catch(NumberFormatException e) {
        }

        scrollView.addView(linearLayout);
        displayNumOfAcc(numOfResults);

    }

    public void goToFilterAccountPage(View view) {
        Intent intent = new Intent(this, FilterAccountActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String operation = data.getStringExtra("operation");
                // if the find button was pressed from the filter activity
                if (operation.equals("find_by_id")) {
                    String accIdString = data.getStringExtra("id");
                    findAccById(accIdString);
                }

                if (operation.equals("filter")) {
                    String accType = data.getStringExtra("accType");
                    String name = data.getStringExtra("name");
                    double minBalance = data.getDoubleExtra("minBalance", Integer.MIN_VALUE);
                    double maxBalance = data.getDoubleExtra("maxBalance", Integer.MAX_VALUE);

                    List<Account> filteredAcc = new ArrayList<>();
                    if (accType.equals("All")) {
                        filteredAcc = doAll();
                    } else if (accType.equalsIgnoreCase("CHEQUING")) {
                        filteredAcc = doChequing();
                    } else if (accType.equals("RESTRICTEDSAVING")) {
                        filteredAcc = doRestrictedSaving();
                    } else if (accType.equals("TFSA")) {
                        filteredAcc = doTFSA();
                    } else if (accType.equals("SAVING")) {
                        filteredAcc = doSaving();
                    } else if (accType.equals("BALANCEOWING")) {
                        filteredAcc = doBalanceOwing();
                    }
                    doFilter(filteredAcc, name, new BigDecimal(minBalance), new BigDecimal(maxBalance));

                }
            }
        }
    }

    public void performResetFilter(View view) {
        List<Account> filteredAcc = doAll();
        doFilter(filteredAcc, "", new BigDecimal(Integer.MIN_VALUE), new BigDecimal(Integer.MAX_VALUE));
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

}
