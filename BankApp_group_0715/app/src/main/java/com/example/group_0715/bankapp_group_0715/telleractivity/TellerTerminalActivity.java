package com.example.group_0715.bankapp_group_0715.telleractivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.group_0715.bankapp_group_0715.R;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.messageactivity.ViewMessages;
import com.example.group_0715.bankapp_group_0715.startupactivity.WelcomeActivity;
import com.example.group_0715.bankapp_group_0715.transactionactivity.ViewCustomerAccountsActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.group_0715.bankapp_group_0715.R.id.all;
import static com.example.group_0715.bankapp_group_0715.R.id.authenticateUserButton;
import static com.example.group_0715.bankapp_group_0715.R.id.makeAccountButton;

/**
 * Created by Alan on 2017-07-28.
 */

public class TellerTerminalActivity extends AppCompatActivity {

    private int customerId = -1;
    private final String EXPRESSION = "^[0-9]+$";

    // for give interest
    private String accountId;
    private int interestAdded;
    private int interestCustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Why do we need this??? there is not customerId to be passed from TellerHomeActivity
         // store the same customer id from login
         Intent objIntent = this.getIntent();
         customerId = objIntent.getIntExtra("customerId", -1);
         **/
        customerId = -1;

        setContentView(R.layout.teller_terminal_activity);
        updateButtonClickable();
    }

    public void authenticateUser(View view) {
        Intent intent = new Intent(this, TellerAuthenticateCustomerActivity.class);
        startActivityForResult(intent, 0);
    }

    public void goToMakeUserPage(View view) {
        Intent intent = new Intent(this, TellerMakeUserActivity.class);
        startActivity(intent);
    }

    public void goToMakeAccountPage(View view) {
        Intent intent = new Intent(this, TellerMakeAccountActivity.class);
        intent.putExtra("customerId", customerId);
        startActivity(intent);
    }

    public void goToViewAccountsPage(View view) {
        Intent intent = new Intent(this, ViewCustomerAccountsActivity.class);
        intent.putExtra("customerId", customerId);
        intent.putExtra("isTellerTerminal", true);
        startActivity(intent);
    }

    public void goToMakeMessageForCustomer(View view) {
        Intent intent = new Intent(this, TellerMakeMessagesActivity.class);
        intent.putExtra("userId", customerId);
        startActivity(intent);
    }

    public void goToViewCustomerMessages(View view) {
        Intent intent = new Intent(this, ViewMessages.class);
        intent.putExtra("userId", customerId);
        startActivity(intent);
    }


    public void deauthenticateUser(View view) {
        customerId = -1;
        // change the text to let user know that no customer is selected
        TextView currentCustomerText = (TextView) findViewById(R.id.currentCustomerText);
        String noServing = getResources().getString(R.string.noServing);
        currentCustomerText.setText(noServing);
        currentCustomerText.setGravity(Gravity.CENTER);
        // disable buttons
        updateButtonClickable();
    }

    private void updateButtonClickable() {
        Button authenticateButton = (Button) findViewById(R.id.authenticateUserButton);
        Button makeAccountButton = (Button) findViewById(R.id.makeAccountButton);
        Button viewAccountsButton = (Button) findViewById(R.id.terminalViewAccounts);
        Button viewMessagesButton = (Button) findViewById(R.id.terminalViewMessages);
        Button deauthenticateButton = (Button) findViewById(R.id.terminalDeauthenticate);
        // if a customer is not being served...
        if (customerId < 0) {
            //set authenticate user button to be clickable
            authenticateButton.setClickable(true);
            authenticateButton.setAlpha(1f);

            // set make account, view accounts, deauthenticate to be unclickable
            makeAccountButton.setClickable(false);
            viewAccountsButton.setClickable(false);
            viewMessagesButton.setClickable(false);
            deauthenticateButton.setClickable(false);

            makeAccountButton.setAlpha(.5f);
            viewAccountsButton.setAlpha(.5f);
            viewMessagesButton.setAlpha(.5f);
            deauthenticateButton.setAlpha(.5f);
        } else {
            //set authenticate user button to be unclickable
            authenticateButton.setClickable(false);
            authenticateButton.setAlpha(.5f);

            // set make account, view accounts, deauthenticate to be clickable
            makeAccountButton.setClickable(true);
            viewAccountsButton.setClickable(true);
            viewMessagesButton.setClickable(true);
            deauthenticateButton.setClickable(true);

            makeAccountButton.setAlpha(1f);
            viewAccountsButton.setAlpha(1f);
            viewMessagesButton.setAlpha(1f);
            deauthenticateButton.setAlpha(1f);
        }
    }

    public void performGiveInterest(View view) {
        //alert dialog box with EditText
        AlertDialog.Builder interestBox = new AlertDialog.Builder(this);
        interestBox.setTitle(getResources().getString(R.string.giveInterest));
        LinearLayout interestContent = new LinearLayout(this);
        interestContent.setOrientation(LinearLayout.VERTICAL);

        TextView promptField = new TextView(this);
        promptField.setText(getResources().getString(R.string.enterAccountIdPrompt));
        interestContent.addView(promptField);

        final EditText accountField = new EditText(this);
        accountField.setHint(getResources().getString(R.string.enterAccountIdHint));
        interestContent.addView(accountField);

        // create padding for LinearLayout according to dp
        float scale = getResources().getDisplayMetrics().density;
        int dpValue = 20;
        int dpScale = (int) (dpValue*scale + 0.5f);
        interestContent.setPadding(dpScale, dpScale, dpScale, dpScale);

        interestBox.setView(interestContent);


        interestBox.setNegativeButton(getResources().getString(R.string.exit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });
        interestAdded = -1;
        interestCustomerId = -1;
        interestBox.setPositiveButton(getResources().getString(R.string.addInterest),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accountId = accountField.getText().toString();
                        interestAdded = 0;
                        if (matchRegex(accountId)) {
                            interestCustomerId = accIdInDB(Integer.parseInt(accountId));
                            if (interestCustomerId != -1) {
                                giveInterest(Integer.parseInt(accountId));
                                interestAdded = 1;
                            }
                        }
                        // close interestBox, let the user know if the interest was successfully added or not
                        // open a new dialog box
                        showGiveInterestResult();
                    }
                });
        interestBox.setCancelable(false);
        interestBox.create().show();
    }

    private void showGiveInterestResult() {
        if (interestAdded != -1) {
            String statusMessage;
            String statusTitle;
            if (interestAdded == 1) {
                statusTitle = getResources().getString(R.string.Success);
                statusMessage = getResources().getString(R.string.successInterestAdded) + " " + accountId + ".";
            } else {
                statusTitle = getResources().getString(R.string.Error);
                statusMessage = getResources().getString(R.string.invalidIdGeneral);
            }

            AlertDialog.Builder resultBox = new AlertDialog.Builder(this);
            resultBox.setMessage(statusMessage);
            resultBox.setTitle(statusTitle);
            resultBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            resultBox.setCancelable(false);
            resultBox.create().show();
        }
    }


    private void giveInterest(int account_id) {
        // obtain interest rate and account balance from database
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        BigDecimal currentBalance = select.getBalance(account_id);
        int accType = select.getAccountType(account_id);
        BigDecimal interestRate = select.getInterestRate(accType);
        select.close();
        BigDecimal calculateInterest = currentBalance.multiply(interestRate);
        // calculate interest, add to current balance, and update new balance to database
        BigDecimal newBalance = currentBalance.add(calculateInterest);
        newBalance = newBalance.setScale(2, RoundingMode.HALF_UP);
        DatabaseUpdateHelper update = new DatabaseUpdateHelper(this);
        update.updateAccountBalance(newBalance, account_id);



        String message = getResources().getString(R.string.interestAdded)
                + " " + account_id + ".";
        DatabaseInsertHelper insert = new DatabaseInsertHelper(this);
        insert.insertMessage(interestCustomerId, message);
        insert.close();
    }


    private boolean matchRegex(String input) {
        Pattern pattern = Pattern.compile(EXPRESSION);
        Matcher matcher = pattern.matcher(input);

        boolean isValid = false;
        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Returns the customer ID who owns this account ID, if it exists in the database.
     * @param accountId the accountId
     * @return the customer ID, -1 otherwise
     */
    private int accIdInDB(int accountId) {
        int inDb = -1;
        DatabaseSelectHelper select = new DatabaseSelectHelper(this);
        List<User> allUsers = select.getUsersDetailsList(this);
        for (User curUser : allUsers) {
            int curUserId = curUser.getId();
            List<Integer> allAccInCurUser = select.getAccountIdsList(curUserId);
            if(allAccInCurUser.contains(accountId)) {
                inDb = curUserId;
            }
        }
        select.close();
        return inDb;
    }


    public void closeTerminal(View view) {
        if (customerId != -1) {
            AlertDialog.Builder resultBox = new AlertDialog.Builder(this);
            resultBox.setMessage(getResources().getString(R.string.preventExitTellerTerminal));
            resultBox.setTitle(getResources().getString(R.string.error));
            resultBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            resultBox.setCancelable(false);
            resultBox.create().show();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (customerId != -1) {
            AlertDialog.Builder resultBox = new AlertDialog.Builder(this);
            resultBox.setMessage(getResources().getString(R.string.preventExitTellerTerminal));
            resultBox.setTitle(getResources().getString(R.string.error));
            resultBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            resultBox.setCancelable(false);
            resultBox.create().show();
        } else {
            finish();
        }
    }

    private void setCustomerId(int newId) {
        // set new customer id
        customerId = newId;
        if (customerId > 0) {
            TextView curCustomerText = (TextView) findViewById(R.id.currentCustomerText);
            String newCustomerMessage = "You are serving customer with the id: " + customerId;
            curCustomerText.setText(newCustomerMessage);
        }
        // enable/disable buttons
        updateButtonClickable();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // gets id from successful customer authentication
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String idString = data.getStringExtra("id");
                setCustomerId(Integer.parseInt(idString));
            }
        }
    }
}
