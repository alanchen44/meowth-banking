package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.SavingsAccount;

import java.math.BigDecimal;


public interface SavingsAccountBuilder {
  /**
   * Create balance for this savings account.
   * @param balance the balance
   * @return the builder that is constructing this savings account.
   */
  public SavingsAccountBuilder createBalance(BigDecimal balance);
  
  /**
   * Create the id for this savings account.
   * @param id the id for this savings account
   * @return the builder that is constructing this savings account.
   */
  public SavingsAccountBuilder createId(int id);
  
  /**
   * Create the name for this savings account.
   * @param name the name for this savings account
   * @return the builder that is constructing this savings account.
   */
  public SavingsAccountBuilder createName(String name);

  /**
   * Get the savings account in this builder under the current specifications.
   * @return the savings account under the current specifications
   */
  public SavingsAccount build(Context context);
}
