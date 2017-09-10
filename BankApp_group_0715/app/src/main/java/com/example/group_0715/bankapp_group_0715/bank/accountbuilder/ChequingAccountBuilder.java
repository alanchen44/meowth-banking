package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.ChequingAccount;

import java.math.BigDecimal;

public interface ChequingAccountBuilder {
  /**
   * Create balance for this chequing account.
   * @param balance the balance
   * @return the builder that is constructing this chequing account
   */
  public ChequingAccountBuilder createBalance(BigDecimal balance);

  /**
   * Create id for this chequing account.
   * @param id the id for this account
   * @return the builder that is constructing this chequing account
   */
  public ChequingAccountBuilder createId(int id);

  /**
   * Create name for this chequing account.
   * @param name the name for this account
   * @return the builder that is constructing this chequing account
   */
  public ChequingAccountBuilder createName(String name);

  /**
   * Get the chequing account with the current specifications.
   * @return the chequing account with the current specifications
   */
  public ChequingAccount build(Context context);
}
