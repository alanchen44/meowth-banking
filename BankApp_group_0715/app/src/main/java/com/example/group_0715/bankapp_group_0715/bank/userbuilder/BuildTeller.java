package com.example.group_0715.bankapp_group_0715.bank.userbuilder;


import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Teller;

public interface BuildTeller {
  
  /**
   * Creates id for teller.
   * @param id the id for teller 
   * @return the builder class that is constructing this teller
   */
  public BuildTeller createId(int id);
  
  /**
   * Creates address for teller.
   * @param address the address for teller 
   * @return the builder class that is constructing this teller
   */
  public BuildTeller createAddress(String address);
  
  /**
   * Creates age for teller.
   * @param age the age for teller 
   * @return the builder class that is constructing this teller
   */
  public BuildTeller createAge(int age);
  
  /**
   * Creates the name for teller.
   * @param name the name for teller
   * @return the builder class that is constructing this teller
   */
  public BuildTeller createName(String name);
  
  /**
   * Creates the role for this teller.
   * @return the builder class that is constructing this teller
   */
  public BuildTeller createRole(Context context);
  
  /**
   * Sets authenticaton of this teller.
   * @param authenticate the authentication of this teller
   * @return the builder class that is constructing this teller
   */
  public BuildTeller createAuth(boolean authenticate);
  
  /**
   * Gets the teller object with its current specifications.
   * @return the teller object with its current specifications
   */
  public Teller build();
}
