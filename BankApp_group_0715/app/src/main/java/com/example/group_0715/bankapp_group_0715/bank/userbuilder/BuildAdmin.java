package com.example.group_0715.bankapp_group_0715.bank.userbuilder;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Admin;

public interface BuildAdmin {
  
  /**
   * Creates id for admin.
   * @param id the id for admin 
   * @return the builder class that is constructing this admin
   */
  public BuildAdmin createId(int id);
  
  /**
   * Creates admin address.
   * @param address the admin address
   * @return the builder class that is constructing this admin
   */
  public BuildAdmin createAddress(String address);
  
  /**
   * Creates admin age.
   * @param age the admin age
   * @return the builder class that is constructing this admin
   */
  public BuildAdmin createAge(int age);
  
  /**
   * Creates admin name.
   * @param name the admin name
   * @return the builder class that is constructing this admin
   */
  public BuildAdmin createName(String name);
  
  /**
   * Create admin role.
   * @return the builder class that is constructing this admin
   */
  public BuildAdmin createRole(Context context);
  
  /**
   * Creates the authentication status of this admin.
   * @param authenticate the authentication status of this admin
   * @return the builder class that is constructing this admin
   */
  public BuildAdmin createAuth(boolean authenticate);
  
  /**
   * Gets the admin object with its current specification.
   * @return the admin object
   */
  public Admin build();
}
