package com.example.group_0715.bankapp_group_0715.bank.bank;


import android.content.Context;
import android.provider.ContactsContract;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.BalanceOwingAccount;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.ChequingAccount;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.RestrictedSavingsAccount;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.SavingsAccount;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.TFSA;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Customer;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Teller;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;
import com.example.group_0715.bankapp_group_0715.bank.exceptions.AuthenticationException;
import com.example.group_0715.bankapp_group_0715.bank.exceptions.ConnectionFailedException;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.*;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TellerTerminal extends BankingApplication {

  // Declaring local fields
  private Teller currentUser;
  private boolean currentUserAuthenticated;
  public static int APP_ID = 2;

  /**
   * Initialize a new Teller Terminal.
   * 
   * @param tellerId the tellers id
   * @param password the tellers password
   */
  public TellerTerminal(int tellerId, String password, Context context) {
    currentUser = null;
    currentUserAuthenticated = false;
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    // set currentUser
    User curUser = select.getUserDetailsInfo(tellerId, context);
    select.close();
    // if the given id is a teller, cast teller into curUser
    if (curUser instanceof Teller) {
      currentUser = (Teller) curUser;
    }


    // Attempt to authenticate the user given the password
    currentUserAuthenticated = currentUser.authenticate(password, context);

    super.setCurrentTellerAuthenticated(currentUserAuthenticated);

    // Setting the current customer to null and his authentication to false
    super.authenticateOverride(false);
    super.setCurrentCustomer(null);
    super.setAppId(APP_ID);
  }



  /**
   * Set the current customer of the Teller Terminal.
   * 
   * @param customer the customer
   */
  public void setCurrentCustomer(Customer customer) {
    super.setCurrentCustomer(customer);
  }

  /**
   * Authenticate the current customer of the Teller Terminal.
   * 
   * @param password the password of the customer
   * @return True if authentcation of customer is successful, false if the password is incorrect.
   * @throws ConnectionFailedException if the customer is null
   */
  public boolean authenticateCurrentCustomer(String password, Context context) throws ConnectionFailedException {
    if (getCurrentCustomer() != null) {
      // Very importantly, we load the user's information in the BankingApplication class
      return authenticate(getCurrentCustomer().getId(), password,context);

    } else { // Throw auth error if not verified
      throw new ConnectionFailedException("Current customer does not exist.");
    }
  }

  /**
   * Makes a new account and registers this account to the current customer in the database.
   * 
   * @param name the name of account
   * @param balance balance of the account
   * @param type the type of the account
   * @return true if successful insert, false otherwise
   * @throws AuthenticationException if either the user or customer not authenticated
   */
  public boolean makeNewAccount(String name, BigDecimal balance, int type, Context context)
      throws AuthenticationException {
    if (currentUserAuthenticated && super.isCustomerAuthenticated()) {
      int success = 0;
      // Obtaining the name of the type of account in order to create a specific type of account
      DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
      int successfulAccountInsert = (int)insert.insertAccount(name, balance, type);
      insert.close();
      if (successfulAccountInsert != -1) {
        Account obtainSuccessfulAccount = null;

          // Obtain the account
          DatabaseSelectHelper select = new DatabaseSelectHelper(context);
          obtainSuccessfulAccount = select.getAccountDetails(successfulAccountInsert,context);

        if (obtainSuccessfulAccount != null) {
          Customer currentCustomer = (Customer) super.getCurrentCustomer();
          // Set the customer's account, add it to their object
          boolean successAdd = currentCustomer.addAccount(obtainSuccessfulAccount);
          if (successAdd) {
            DatabaseInsertHelper insert2 = new DatabaseInsertHelper(context);
            success = (int)insert2.insertUserAccount(currentCustomer.getId(),
              successfulAccountInsert);
            insert2.close();
          }
        }
      }
      if (success != -1) {
        return true;
      } else {
        return false;
      }
    } else {
      // Throw auth error if not verified
      throw new AuthenticationException(
          "Both the Teller and Customer must be authenticated to make a new acccount.");
    }
  }

  /**
   * Makes a new user and inserts it into the DB.
   * 
   * @param name the name of the user
   * @param age the age of the user
   * @param address the address of the user
   * @param password the password of the user
   * @throws AuthenticationException if either the user or customer not authenticated
   * @throws SQLException if something wrong in database
   */
  public void makeNewUser(String name, int age, String address, String password, Context context)
      throws AuthenticationException, SQLException {
    if (currentUserAuthenticated) {
      // search in the database for the customer role id
      int customerRoleId = -1;
      DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      List<Integer> roleIdList = select.getRoleList();
      // iterate through customer role id list
      for (int roleId : roleIdList) {
        if (select.getRole(roleId).equals("CUSTOMER")) {
          customerRoleId = roleId;
        }
      }
      // only insert the new user if customerId can be found
      if (customerRoleId >= 0) {
        DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
        insert.insertNewUser(name, age, address, customerRoleId, password);
        insert.close();
      }
    } else {
      // Throw auth error if not verified
      throw new AuthenticationException("The Teller must be authenticated to make a new user.");
    }
  }

  /**
   * DeAuthenticates the current customer.
   */
  public void deAuthenticateCustomer() {
    setCurrentCustomer(null);
    authenticateOverride(false);
  }

  /**
   * Gives interest to the accountId provided for the current customer.
   * 
   * @param accountId the id of the account to give interest
   * @throws AuthenticationException if either the user or customer not authenticated
   */
  public void giveInterest(int accountId, Context context) throws AuthenticationException {
    if (currentUserAuthenticated && isCustomerAuthenticated() && customerHasAcc(accountId, context)) {
      Account giveInterestAccount = null;
      DatabaseSelectHelper select = new DatabaseSelectHelper(context);

        // Get the account details
        giveInterestAccount = select.getAccountDetails(accountId,context);
      select.close();;

        // Get the account id and add interest according to it's account type
        int typeAccountId = select.getAccountType(accountId);
        String typeAccountString = select.getAccountTypeName(typeAccountId);
        Customer currentCustomer = (Customer) super.getCurrentCustomer();
      select.close();

      DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
        // add a message for each account that was touched.
        if (typeAccountString.equals("CHEQUING")) {
          ((ChequingAccount) giveInterestAccount).addInterest(context);
          insert.insertMessage(currentCustomer.getId(),
                  "Your Account: " + accountId + " Interest has been successfully added:");
        } else if (typeAccountString.equals("SAVING")) {
          ((SavingsAccount) giveInterestAccount).addInterest(context);
          insert.insertMessage(currentCustomer.getId(),
                  "Your Account: " + accountId + " Interest has been successfully added:");
        } else if (typeAccountString.equals("TFSA")) {
          ((TFSA) giveInterestAccount).addInterest(context);
          insert.insertMessage(currentCustomer.getId(),
                  "Your Account: " + accountId + " Interest has been successfully added:");
        } else if (typeAccountString.equals("RESTRICTEDSAVING")) {
          ((RestrictedSavingsAccount) giveInterestAccount).addInterest(context);
          insert.insertMessage(currentCustomer.getId(),
                  "Your Account: " + accountId + " Interest has been successfully added:");
        } else if (typeAccountString.equalsIgnoreCase("BALANCEOWING")) {
          ((BalanceOwingAccount) giveInterestAccount).addInterest(context);
          insert.insertMessage(currentCustomer.getId(),
                  "Your Account: " + accountId + " Interest has been successfully added:");
        }
      insert.close();

    } else {
      // Throw auth error if not verified
      throw new AuthenticationException(
          "Both the Teller and Customer must be authenticated to make a new user.");
    }
  }
  
  
  /**
   * Tellers will have the ability to leave messages for their customers.
   * @param userId the customer Id
   * @param message the message the Teller wants to leave for the customers.
   * @return returns the message id that was inserted or -1 if it was not successful.
   */
  public int leaveMessage(int userId, String message, Context context) {


    int success = -1;
    if (currentUserAuthenticated) {

        DatabaseSelectHelper select = new DatabaseSelectHelper(context);
        User userInput = select.getUserDetailsInfo(userId, context);
      select.close();
        if (userInput == null || !(userInput instanceof Customer)) {
          System.out.println("This is not a valid customer id.");
          //return success;
        } else {
          Customer user = (Customer) userInput;
          DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
          success = (int)insert.insertMessage(user.getId(), message);
          insert.close();
          return success;
        }

    }
    return success;
  }
  
  
  
  /**
   * Teller can view messages, updating the message status to viewed!
   * @param messageId of the message wanted.
   * @return the message chosen by the Teller.
   * @throws AuthenticationException is thrown if the teller or customer is not authenticated.
   * @throws SQLException is thrown for any database issues.
   */
  public String viewSpecificTellerMessage(int messageId, Context context)
          throws AuthenticationException, SQLException {
    // Check for authentication
    if (currentUserAuthenticated) {
      String message = "";
      if (tellerHasMess(messageId, context)) {
        DatabaseSelectHelper select = new DatabaseSelectHelper(context);
        // get the specific message of the user from the database.
        message = select.getSpecificMessage(messageId);
        select.close();
        // update the message to being viewed.
        DatabaseUpdateHelper update = new DatabaseUpdateHelper(context);
        update.updateUserMessageState(messageId);
        update.close();
      } else {
        System.out.println("Looks like you may have entered the wrong Message ID!");
      }
      return message;
    } else {
      // throw autnetication error if any.
      throw new AuthenticationException("The user is not authenticated.");
    }
  }
  

  /**
   * Teller can view their own messages.
   * @return a list of All available Message ID.
   */
  public List<Integer> viewAllTellerMessagesId(Context context) throws AuthenticationException, SQLException {
    // if the Teller is authenticated then...
    if (currentUserAuthenticated) {
      List<Integer> message = new ArrayList<Integer>();
      // get the desired message from the database with the id.
      DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      message = select.getAllMessagesList(currentUser.getId());
      select.close();
      return message;
    } else {
      // throw exception if there appears to be an authentication error.
      throw new AuthenticationException("The user is not authenticated.");
    }
      
  }
  
  /**
   * Given an message id, check if the Teller has this message.
   * 
   * @param messageId the message id to check in the user list.
   * @return true if user has this message, false otherwise.
   */
  protected boolean tellerHasMess(int messageId, Context context) {
    boolean hasMess = false;
    // get the list of Teller's message Id
    List<Integer> tellerMessList = currentUser.viewAllMessagesID(context);
    // iterate through the Teller message list
    for (Integer tmess : tellerMessList) {
      // true if teller has this message id in their message list
      if (tmess.intValue() == messageId) {
        hasMess = true;
      }
    }
    return hasMess;
  }
  
  
  
  
}
