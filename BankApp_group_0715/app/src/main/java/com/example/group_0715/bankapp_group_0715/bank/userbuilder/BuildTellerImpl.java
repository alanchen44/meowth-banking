package com.example.group_0715.bankapp_group_0715.bank.userbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Teller;

public class BuildTellerImpl implements BuildTeller {

  private Teller teller;
  
  /**
   * The constructor of this builder will create a new Teller object.
   */
  public BuildTellerImpl(Context context) {
    // creates a new Teller object
    this.teller = new Teller();
    // sets the role for this Teller
    createRole(context);
  }
  
  @Override
  public BuildTeller createId(int id) {
    teller.setId(id);
    return this;
  }

  @Override
  public BuildTeller createAddress(String address) {
    teller.setAddress(address);
    return this;
  }

  @Override
  public BuildTeller createAge(int age) {
    teller.setAge(age);
    return this;
  }

  @Override
  public BuildTeller createName(String name) {
    teller.setName(name);
    return this;
  }


  @Override
  public BuildTeller createRole(Context context) {
    teller.setRoleId("TELLER", context);
    return this;
  }

  @Override
  public BuildTeller createAuth(boolean authenticate) {
    teller.setAuthenticated(authenticate);
    return this;
  }

  @Override
  public Teller build() {
    return this.teller;
  }

}
