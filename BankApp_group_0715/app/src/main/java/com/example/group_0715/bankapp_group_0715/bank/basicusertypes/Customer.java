package com.example.group_0715.bankapp_group_0715.bank.basicusertypes;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer extends User {

  private List<Account> accounts = new ArrayList<>();
  private static final long serialVersionUID = 8L;

  /**
   * Initialize default Customer constructor to begin using the builder.
   */
  public Customer() {}

  /**
   * Initialize a Customer object without authentication.
   * 
   * @param id the id of the customer
   * @param name the name of the customer
   * @param age the age of the customer
   * @param address the address of the customer
   * @throws SQLException 
   */
  public Customer(int id, String name, int age, String address, Context context) throws SQLException {
    super.setId(id);
    super.setName(name);
    super.setAge(age);
    super.setAddress(address);
    super.setRoleId("CUSTOMER", context);
    accounts = new ArrayList<>();
    this.getAccounts(context);
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    String password = select.getPassword(this.getId());
    select.close();
    super.setPassword(password);
  }



  /**
   * Initialize a Customer object with authentication.
   * 
   * @param id the id of the customer
   * @param name the name of the customer
   * @param age the age of the customer
   * @param address the address of the customer
   * @param authenticated authentication status of the customer
   * @throws SQLException 
   */
  public Customer(int id, String name, int age, String address, boolean authenticated, Context context) throws SQLException {
    this(id, name, age, address, context);
    super.setAuthenticated(authenticated);
  }


  /**
   * Returns the list of accounts assigned to the Customer.
   * 
   * @return list of accounts
   */
  public List<Account> getAccounts(Context context) {
      // resets account list and re-add all the customer accounts to update status of these accounts
      accounts = new ArrayList<>();

      DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      // add accounts
      List<Integer> customerAccountIds = select.getAccountIdsList(this.getId());
      // Creating a list iterator to iterate through the collection of customer account ids
      Iterator<Integer> caItr = customerAccountIds.iterator();
      while (caItr.hasNext()) {
        int curAccInt = (int) caItr.next();
        // Obtaining the Account details given the Id from the list iterator
        Account currentAccount = select.getAccountDetails(curAccInt, context);
        // Adding the account to the Customer's list of accounts
        this.addAccount(currentAccount);
      }
      select.close();
    return accounts;
  }

  /**
   * Add an account to the customer.
   * 
   * @param account the account to add
   */
  public boolean addAccount(Account account) {
    boolean accountExists = false;
    // loop through the customer's account list to check if this account already exists
    for (Account curAccount : accounts) {
      // compare account id to see if the customer already has this account
      if (curAccount.getId() == account.getId()) {
        accountExists = true;
      }
    }

    // if account is not already added, add account
    if (!accountExists) {
      accounts.add(account);
      return true;
    }
    return false;
  }

}
