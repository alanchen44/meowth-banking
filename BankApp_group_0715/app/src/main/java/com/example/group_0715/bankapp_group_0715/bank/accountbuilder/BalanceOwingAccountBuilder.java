package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.BalanceOwingAccount;

import java.math.BigDecimal;

public interface BalanceOwingAccountBuilder {
  /**
   * Create balance for this Balance Owing account.
   * @param balance the balance
   * @return the builder that is constructing this Balance Owing account
   */
  public BalanceOwingAccountBuilder createBalance(BigDecimal balance);

  /**
   * Create id for this Balance Owing account.
   * @param id the id for this account
   * @return the builder that is constructing this Balance Owing account
   */
  public BalanceOwingAccountBuilder createId(int id);

  /**
   * Create name for this Balance Owing account.
   * @param name the name for this account
   * @return the builder that is constructing this Balance Owing account
   */
  public BalanceOwingAccountBuilder createName(String name);

  /**
   * Get the Balance Owing account with the current specifications.
   * @return the Balance Owing account with the current specifications
   */
  public BalanceOwingAccount build(Context context);
}
