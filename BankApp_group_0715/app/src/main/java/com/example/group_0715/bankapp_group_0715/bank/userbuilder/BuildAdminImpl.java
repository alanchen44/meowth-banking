package com.example.group_0715.bankapp_group_0715.bank.userbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Admin;

public class BuildAdminImpl implements BuildAdmin {

  private Admin admin;
  
  /**
   * The constructor of this builder will create a new Admin object.
   */
  public BuildAdminImpl(Context context) {
    // creates a new Admin object
    this.admin = new Admin();
    // sets the role for this Admin
    createRole(context);
  }
  
  @Override
  public BuildAdmin createId(int id) {
    admin.setId(id);
    return this;
  }

  @Override
  public BuildAdmin createAddress(String address) {
    admin.setAddress(address);
    return this;
  }

  @Override
  public BuildAdmin createAge(int age) {
    admin.setAge(age);
    return this;
  }

  @Override
  public BuildAdmin createName(String name) {
    admin.setName(name);
    return this;
  }

  @Override
  public BuildAdmin createRole(Context context) {
    admin.setRoleId("ADMIN", context);
    admin.setRoleString("ADMIN");
    return this;
  }

  @Override
  public BuildAdmin createAuth(boolean authenticate) {
    admin.setAuthenticated(authenticate);
    return this;
  }

  @Override
  public Admin build() {
    return this.admin;
  }

}
