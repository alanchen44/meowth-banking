package com.example.group_0715.bankapp_group_0715.bank.bank;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.TFSA;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Customer;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.bank.exceptions.AuthenticationException;
import com.example.group_0715.bankapp_group_0715.bank.exceptions.InsufficientFundsException;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public abstract class BankingApplication {

  private Customer currentCustomer = null;
  private boolean currentTellerAuthenticated = false;
  private boolean currentCustomerAuthenticated = false;
  private int appId = 0;
  private static int APP_ID_ATM = 1;
  private static int APP_ID_TELLER_TERMINAL = 2;

  /**
   * Sets the app id of the app to be used (for authentication).
   * 
   * @param appId 1 for ATM, 2 for Teller Terminal
   */
  public void setAppId(int appId) {
    this.appId = appId;
  }

  /**
   * Set the authentication status of the current teller.
   * 
   * @param currentTellerAuthenticated true to authenticate the teller, false otherwise.
   */
  public void setCurrentTellerAuthenticated(boolean currentTellerAuthenticated) {
    this.currentTellerAuthenticated = currentTellerAuthenticated;
  }

  /**
   * Return whether the current customer is authenticated.
   * 
   * @return true if the customer is authenticated, false otherwise
   */
  public boolean isCustomerAuthenticated() {
    return this.currentCustomerAuthenticated;
  }

  /**
   * Set the current customer.
   * 
   * @param currentCustomer the new customer
   */
  public void setCurrentCustomer(Customer currentCustomer) {
    this.currentCustomer = currentCustomer;
  }

  /**
   * Override authentication check and automatically authenticate the current customer.
   * 
   * @param currentCustomerAuthenticated true to authenticate, false otherwise
   */
  public void authenticateOverride(boolean currentCustomerAuthenticated) {
    this.currentCustomerAuthenticated = currentCustomerAuthenticated;
  }

  /**
   * Returns the current customer.
   * 
   * @return the current customer
   */
  public User getCurrentCustomer() {
    return currentCustomer;
  }

  /**
   * Runs the authentication test based on the type of banking application being used.
   * 
   * @return true if authentication passed, false otherwise
   */
  private boolean authenticationPassed() {
    // The ATM only requires for the customer to be authenticated to run the methods
    if (appId == APP_ID_ATM) {
      if (currentCustomerAuthenticated) {
        return true;
      } else {
        return false;
      }

      // The Teller terminal requires both the customer and Teller to be authenticated, and for
      // there to be
      // no restriction on the user
      // to run the methods
    } else if (appId == APP_ID_TELLER_TERMINAL) {
      if (currentCustomerAuthenticated && currentTellerAuthenticated) {
        return true;
      } else {
        return false;
      }

      // If the app id was not found for some reason, return false
    } else {
      return false;
    }
  }

  /**
   * List all the accounts available to customer.
   * 
   * @return List all the accounts
   * @throws AuthenticationException if not authenticated
   */
  public List<Account> listAccounts(Context context) throws AuthenticationException {
    // Check for authentication
    if (authenticationPassed()) {
      // Return the list of their accounts
      return currentCustomer.getAccounts(context);
    } else {
      throw new AuthenticationException("Either the user or customer is not authenticated.");
    }
  }

  /**
   * Set current customer and attempts to authenticate the customer.
   * 
   * @param userId the user's id
   * @param password the user's password
   * @return true if authenticated ,false otherwise
   */
  public boolean authenticate(int userId, String password, Context context) {
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    // fetch user with userId
      User curUser = select.getUserDetailsInfo(userId, context);
      // if the userId is the id of a Customer, set currentCustomer to the new Customer
      if (curUser instanceof Customer) {
        // creates a new object to receive accurate information about accounts
        currentCustomer = (Customer) curUser;
      }
      select.close();


    // Attempt to authenticate the user given the password
    if (currentCustomer != null) {
      currentCustomerAuthenticated = currentCustomer.authenticate(password, context);
    }
    // Return the confirmation that they are authenticated
    return currentCustomerAuthenticated;
  }


  /**
   * Given an account id, check if the customer has this account.
   * 
   * @param accountId the account id to check in the customer account list.
   * @return true if customer has this account, false otherwise.
   */
  protected boolean customerHasAcc(int accountId, Context context) {
    boolean hasAcc = false;
    List<Account> customerAccList = currentCustomer.getAccounts(context);
    // iterate through the customer's account list
    for (Account curAcc : customerAccList) {
      // true if customer has this account id in their account list
      if (curAcc.getId() == accountId) {
        hasAcc = true;
      }
    }
    return hasAcc;
  }
  
  
  /**
   * Given an message id, check if the customer has this message.
   * 
   * @param messageId the message id to check in the user list.
   * @return true if user has this messaage, false otherwise.
   */
  protected boolean customerHasMess(int messageId, Context context) {
    boolean hasMess = false;
    List<Integer> customerMessList = currentCustomer.viewAllMessagesID(context);
    // iterate through the customer's account list
    for (Integer cuMess : customerMessList) {
      // true if customer has this account id in their account list
      if (cuMess.intValue() == messageId) {
        hasMess = true;
      }
    }
    return hasMess;
  }

  /**
   * Make a deposit.
   * 
   * @param amount the amount
   * @param accountId the account id
   * @return true if successful ,false otherwise
   * @throws AuthenticationException if not authenticated
   * @throws SQLException if there is a problem accessing the database
   */
  public boolean makeDeposit(BigDecimal amount, int accountId, Context context)
      throws AuthenticationException, SQLException {

    // Check for authentication
    if (authenticationPassed()) {

      boolean depositStatus = false;

      // if not authenticated, throw AuthenticationException
      if (!currentCustomerAuthenticated) {
        throw new AuthenticationException("The user is not authenticated.");
      } else if (currentCustomerAuthenticated && customerHasAcc(accountId, context)
          && amount.compareTo(BigDecimal.ZERO) >= 0) {
        DatabaseSelectHelper select = new DatabaseSelectHelper(context);
        BigDecimal beforeDepositBalance = select.getBalance(accountId);
        select.close();
        // new balance
        BigDecimal afterDepositBalance = beforeDepositBalance.add(amount);
        // Update the balance in the DB, updateAccoutBalance will round the new value to 2 decimal
        // places
        DatabaseUpdateHelper update = new DatabaseUpdateHelper(context);
        depositStatus = update.updateAccountBalance(afterDepositBalance, accountId);
      }
      return depositStatus;
    } else {
      throw new AuthenticationException("Either the user or customer is not authenticated.");
    }
  }

  /**
   * Make a withdrawal. If the given account is TFSA and its balance falls below $1000, it will
   * become a SAVING account.
   * 
   * @param amount the amount
   * @param accountId the account id
   * @return true if successful ,false otherwise
   * @throws AuthenticationException if not authenticated
   * @throws SQLException throws if there is a database error
   * @throws InsufficientFundsException throws if amount is greater than current balance.
   */
  public boolean makeWithdrawal(BigDecimal amount, int accountId, Context context)
      throws AuthenticationException, SQLException, InsufficientFundsException {

    // Check for authentication
    if (authenticationPassed()) {
      DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      // Obtain withdrawal amount and status from balance
      boolean withdrawStatus = false;
      BigDecimal beforeWithdrawBalance = select.getBalance(accountId);
      select.close();

      // If the balance is less than 0 after withdrawal, return an error
      if (beforeWithdrawBalance.compareTo(amount) < 0) {
        throw new InsufficientFundsException("Cannot withdraw more than current balance.");
      } else if (currentCustomerAuthenticated && customerHasAcc(accountId, context)
          && amount.compareTo(BigDecimal.ZERO) >= 0) {
        // new balance
        BigDecimal afterWithdrawBalance = beforeWithdrawBalance.subtract(amount);
        // Update the balance in the DB, updateAccoutBalance will round the new value to 2 decimal
        // places
        DatabaseUpdateHelper update = new DatabaseUpdateHelper(context);
        withdrawStatus = update.updateAccountBalance(afterWithdrawBalance, accountId);
        update.close();
        // Restriction: if account is TFSA and balance is below $1000, the TFSA becomes SAVING
        if (withdrawStatus) {
          restrictionTfsa(afterWithdrawBalance, accountId, context);
        }
      }
      return withdrawStatus;
    } else {
      throw new AuthenticationException("Either the user or customer is not authenticated.");
    }
  }

  /**
   * Check to see if the account is TFSA and the balance is below $1000. If it is, convert the TFSA
   * account to SAVING.
   * 
   * @param balance the current balance of the account
   * @param accountId the id of the account
   */
  private void restrictionTfsa(BigDecimal balance, int accountId, Context context) {
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    Account acc = select.getAccountDetails(accountId, context);
    select.close();
    // if the account is TFSA and the balance is below $1000, convert the TFSA to SAVING
    if (acc instanceof TFSA && balance.compareTo(new BigDecimal(1000.00)) < 0) {
      System.out.println("This TFSA balance is now below $1000.");
      System.out.println("It will now automatically become a SAVING acccount.");
      //bank inserts a message to notify the user a change has happened.
      DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
      insert.insertMessage(currentCustomer.getId(), "The Account " + accountId
            + " that used to be a TFSA account automatically become a SAVINGS acccount");
      insert.close();
      // using account types enum maps to get the SAVING account type id
      AccountTypesMap accTypeMap = AccountTypesMap.getInstance(context);
      int savingId = accTypeMap.getAccountTypeId("SAVING",context);

      DatabaseUpdateHelper update = new DatabaseUpdateHelper(context);
      // updating the database for the type conversion
      update.updateAccountType(savingId, accountId);
      update.close();
    }

  }

  /**
   * Returns balance of account with id accountId.
   * 
   * @param accountId the id of the account
   * @return the balance of the account
   * @throws AuthenticationException if not authenticated
   * @throws SQLException if there is a database error
   */
  public BigDecimal checkBalance(int accountId, Context context) throws AuthenticationException, SQLException {
    // Check for authentication
    if (authenticationPassed()) {
      BigDecimal returnValue = null;
      // check if the customer even has the account
      if (customerHasAcc(accountId,context)) {
        // get the current balance of the account from the database
        DatabaseSelectHelper select = new DatabaseSelectHelper(context);
        returnValue = select.getBalance(accountId);
        select.close();
      }
      return returnValue;
    } else {
      throw new AuthenticationException("Either the user or customer is not authenticated.");
    }
  }

  
/**
 * Gets the message on the request of the user with the user providing a message id.
 * @param messageId The Id of the message the user wants to view.
 * @return the message the user wants to view.
 * @throws AuthenticationException if the teller or customer is not authenticated.
 * @throws SQLException if there is an error in the database.
 */
  public String viewSpecificMessage(int messageId, Context context) throws AuthenticationException, SQLException {
    if (authenticationPassed()) {
      String message = "";
      // Check for authentication
      if (customerHasMess(messageId, context)) {
        // get the current balance of the account from the database
        //message = DatabaseSelectHelper.getSpecificMessage(messageId);
        //DatabaseUpdateHelper.updateUserMessageState(messageId);
        message = currentCustomer.viewSpecificMessage(messageId, context);
    } else {
     System.out.println("Looks like you may have entered the wrong Message ID!");
    }
      // return a blank message if none exists
    return message;
  } else {
    throw new AuthenticationException("Either the user or customer is not authenticated.");
  }
}
  

  /**
   * Returns a list of the message id's of the current customer's messages
   * @return a list of integers that corresponds with the id's of the customer messages.
   */
  public List<Integer> viewAllCustomerMessagesID(Context context) throws AuthenticationException, SQLException {
      // check for authentication of either teller and/or customer
      if (authenticationPassed()) {
          // initialize list to store the customer's id's.
          List<Integer> message = new ArrayList<Integer>();
          //message = DatabaseSelectHelper.getAllMessages(currentCustomer.getId());
          message = currentCustomer.viewAllMessagesID(context);
          return message;
      } else {
        throw new AuthenticationException("Either the user or customer is not authenticated.");
      }
      
  }

}

