package com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseUpdateHelper;

import java.math.BigDecimal;

public class RestrictedSavingsAccount extends Account {

  private BigDecimal interestRate;
  private static final long serialVersionUID = 4L;

  /**
   * Initialize a RestrictedSavingsAccount object.
   * 
   * @param id the id of the account
   * @param name the name of the account
   * @param balance the balance of account
   */
  public RestrictedSavingsAccount(int id, String name, BigDecimal balance, Context context) {
    super.setId(id);
    super.setName(name);
    super.setBalance(balance);
    super.setType("RESTRICTEDSAVING", context);
    this.findAndSetInterestRate(context);
  }

  /**
   * Obtain the interest rate for this type of account.
   */
  public void findAndSetInterestRate(Context context) {
    // If there is an error, then the interest rate will be set to 0
    BigDecimal userInterestRate = new BigDecimal(0);

    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    // Obtain the type of the account and the associated interest rate
    int accountType = super.getType();
    userInterestRate = select.getInterestRate(accountType);
    select.close();

    // Set the interest rate of the user
    this.interestRate = userInterestRate;
  }

  /**
   * Add the interest rate to the account as a part of their balance and update it in the DB.
   */
  public void addInterest(Context context) {
    // First make sure that the interest rate is set to their Account type
    this.findAndSetInterestRate(context);

    // Calculating the interest as a percentage of the interest rate
    BigDecimal interestAddedToBalance = super.getBalance(context).multiply(interestRate);

    // Calculate the updated balance as related to their previous balance
    BigDecimal updatedBalance = super.getBalance(context).add(interestAddedToBalance);
    DatabaseUpdateHelper update = new DatabaseUpdateHelper(context);
    // Find out if the updating of the balance is successful
    boolean success = update.updateAccountBalance(updatedBalance, super.getId());
    update.close();
    // Set the new balance in the object
    if (success) {
      super.setBalance(updatedBalance);
    }
  }

}
