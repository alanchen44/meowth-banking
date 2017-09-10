package com.example.group_0715.bankapp_group_0715.bank.generics;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;


/**
 * A singleton class, (will provide a single instance of the class).
 * 
 * @author jacquelinechan
 *
 */
public class AccountTypesMap {
  private static EnumMap<AccountTypes, Integer> AccountTypesMap =
      new EnumMap<AccountTypes, Integer>(AccountTypes.class);
  private static AccountTypesMap instance = null;

  /**
   * Instantiates the class.
   */
  // private constructor, can only be called internally.
  private AccountTypesMap(Context context) {
    buildAccountTypeMap(context);
  }


  /**
   * Returns the instance of the Class.
   * 
   * @return AccountTypesMap, instance of the Class.
   */
  public static AccountTypesMap getInstance(Context context) {
    // if the instance of the class exists, return the existing instance
    // if not create an instance
    if (instance == null) {
      instance = new AccountTypesMap(context);
      return instance;
    } else {
      return instance;
    }
  }


  /**
   * Returns an EnumMap of AccountTypes as keys, and it's values (type id) as values.
   * 
   * @return EnumMap.
   */
  public static EnumMap<AccountTypes, Integer> getmap() {
    // return an EnumMap of AccounTypes as keys, Integer as values.
    return AccountTypesMap;
  }

  /**
   * A private method that builds an AccountTypeMap through accessing the Database.
   */
  private static void buildAccountTypeMap(Context context) {
    DatabaseSelectHelper select = new DatabaseSelectHelper(context);
    // get the the list of accounttypeids from the database.
    List<Integer> typeIdList = select.getAccountTypesIdList();
    // loop through each id.
    for (Integer typeId : typeIdList) {
      // Store the String representation of the id.
      String typeName = select.getAccountTypeName(typeId);
      // loop through each account types in the enum class.
      for (AccountTypes type : AccountTypes.values()) {
        // place the type and typeid in the map
        // if the String representation of the ids match the desired enum type name
        if ((type + "").equalsIgnoreCase(typeName)) {
          AccountTypesMap.put(type, typeId);
        }
      }
    }
    select.close();
  }

  /**
   * Calls the private methods to update the enummap through updating database.
   */
  public void updateAccountMap(Context context) {
    // update the EnumMap by clearing the private EnumMap variable, and reconnecting to database.
    AccountTypesMap.clear();
    buildAccountTypeMap(context);
  }

  /**
   * 
   * @param typeName is the type of account (in the Enum class AccountTypes)
   * @return The corresponding account type id, return 0 otherwise.
   */
  public int getAccountTypeId(String typeName, Context context) {
    updateAccountMap(context);
    // checks what account type the parameter is requesting for
    // returns the id of that type through database helper methods.
    if (typeName.equals("SAVING")) {
      return AccountTypesMap.get(AccountTypes.SAVING);
    } else if (typeName.equals("CHEQUING")) {
      return AccountTypesMap.get(AccountTypes.CHEQUING);
    } else if (typeName.equals("TFSA")) {
      return AccountTypesMap.get(AccountTypes.TFSA);
    } else if (typeName.equals("RESTRICTEDSAVING")) {
      return AccountTypesMap.get(AccountTypes.RESTRICTEDSAVING);
    } else if (typeName.equals("BALANCEOWING")) {
      return AccountTypesMap.get(AccountTypes.BALANCEOWING);
    }
    return 0;
  }
}
