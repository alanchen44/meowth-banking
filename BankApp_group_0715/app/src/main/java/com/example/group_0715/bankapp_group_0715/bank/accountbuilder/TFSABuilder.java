package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.TFSA;

import java.math.BigDecimal;

public interface TFSABuilder {
  
  /**
   * Create the balance for this TFSA.
   * @param balance the balance
   * @return the builder that is constructing this TFSA
   */
  public TFSABuilder createBalance(BigDecimal balance);

  /**
   * Create the id for this TFSA.
   * @param id the id for this TFSA
   * @return the builder that is constructing this TFSA.
   */
  public TFSABuilder createId(int id);

  /**
   * Create the name for this TFSA.
   * @param name the name for this TFSA
   * @return the builder that is constructing this TFSA.
   */
  public TFSABuilder createName(String name);

  /**
   * Get the TFSA with the current specifications.
   * @return the TFSA with the current specifications
   */
  public TFSA build(Context context);

}
