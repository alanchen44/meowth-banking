package com.example.group_0715.bankapp_group_0715.bank.bank;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Customer;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.sql.SQLException;


public class ATM extends BankingApplication {
  
  public static int APP_ID = 1;

  /**
   * Initialize an ATM without password (unauthenticated).
   * 
   * @param customerid the customer's id
   */
  public ATM(int customerid, Context context) {
    // Automatically set them to not be authenticated
    super.authenticateOverride(false);
    super.setAppId(APP_ID);

      DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      User currentUser = select.getUserDetailsInfo(customerid, context);
      select.close();
      // if customerid is actually a customer, the customer from the database given the customerid
      if (currentUser instanceof Customer) {
        // cast to Customer
        this.setCurrentCustomer((Customer) currentUser);
      }
  }

  /**
   * Initialize an ATM with password.
   * 
   * @param customerid the customer's id
   * @param password the customer's password
   */
  public ATM(int customerid, String password, Context context) {
    // Call the simple constructor to initialize the user, the rest will just
    // authenticate them
    this(customerid, context);
    super.setAppId(APP_ID);

    // Attempt to authenticate the user given the password
    if (super.getCurrentCustomer() != null) {
      super.authenticateOverride(this.getCurrentCustomer().authenticate(password, context));
    } else {
      // Call superclass method to set authentication correctly
      super.authenticateOverride(false);
    }
  }
  

  
  
  
  
  
  
}
