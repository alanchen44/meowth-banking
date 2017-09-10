package com.example.group_0715.bankapp_group_0715.bank.accountbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.TFSA;

import java.math.BigDecimal;

public class TFSABuilderImp implements TFSABuilder {
  private Integer id;
  private String name;
  private BigDecimal balance;
  private TFSA account;


  @Override
  public TFSABuilder createBalance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  @Override
  public TFSABuilder createId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public TFSABuilder createName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public TFSA build(Context context) {
    // if all the necessary inputs are given, return the account
    if (this.id != null && this.name != null && this.balance != null) {
      this.account = new TFSA(this.id, this.name, this.balance, context);
    }
    return this.account;
  }
}
