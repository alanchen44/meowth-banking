package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.RestrictedSavingsAccount;

import java.math.BigDecimal;


public interface RestrictedSavingsAccountBuilder {
  /**
   * Create balance for this restricted savings account.
   * @param balance the balance
   * @return the builder that is constructing this restricted savings account.
   */
  public RestrictedSavingsAccountBuilder createBalance(BigDecimal balance);
  
  /**
   * Create the id for this restricted savings account.
   * @param id the id for this restricted savings account
   * @return the builder that is constructing this restricted savings account.
   */
  public RestrictedSavingsAccountBuilder createId(int id);
  
  /**
   * Create the name for this restricted savings account.
   * @param name the name for this restricted savings account
   * @return the builder that is constructing this restricted savings account.
   */
  public RestrictedSavingsAccountBuilder createName(String name);

  /**
   * Get the restricted savings account in this builder under the current specifications.
   * @return the restricted savings account under the current specifications
   */
  public RestrictedSavingsAccount build(Context context);
}
