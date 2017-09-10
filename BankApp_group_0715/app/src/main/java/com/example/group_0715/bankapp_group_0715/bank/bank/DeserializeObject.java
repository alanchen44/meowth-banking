package com.example.group_0715.bankapp_group_0715.bank.bank;

import android.content.Context;

import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.Admin;
import com.example.group_0715.bankapp_group_0715.bank.basicusertypes.User;
import com.example.group_0715.bankapp_group_0715.bank.database.DatabaseDriverA;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseInsertHelper;
import com.example.group_0715.bankapp_group_0715.bank.databasehelpers.DatabaseSelectHelper;
import com.example.group_0715.bankapp_group_0715.bank.generics.AccountTypesMap;
import com.example.group_0715.bankapp_group_0715.bank.generics.RoleMap;
import com.example.group_0715.bankapp_group_0715.bank.basicaccounttypes.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DeserializeObject {
  private List<Account> accountsFromFile;
  private List<User> usersFromFile;
  
  public DeserializeObject(User admin, Context context) throws SQLException{
    try {
      //create object output stream based on file
	  ObjectInputStream input = new ObjectInputStream(new FileInputStream("database_copy.ser"));
	  //read accoutn list from files
	  accountsFromFile = (List<Account>) input.readObject();
	  //read user list from files
	  usersFromFile = (List<User>) input.readObject();
	  // check account list and user list have valid id in enum map
	  boolean checkIdInMap = checkType(context);
	  boolean validAdmin = false;
	  //before clean database, get account type info in order from database
	  List<String> accountTypeInfo = getAcountTypeInfo(context);
	  //before clean database, get account role info in order from database
	  List<String> roleInfo = getRoleInfo(context);
	  //check if admin is in database
	  if(admin instanceof Admin){
	    validAdmin = usersFromFile.contains(admin); 
	  }
	  //if admin is in database and type ids and role ids are valid
	  if(checkIdInMap && validAdmin){
		//
		DatabaseDriverA db = new DatabaseDriverA(context);
          db.onUpgrade(db.getWritableDatabase(),1,1);
          db.onCreate(db.getWritableDatabase());
		//ovveride information about user and account in database
	    overrideUserInDatabase(context);
	    overrideAccountInDatabase(context);
	    //insert new account type table
	    buildNewAccountType(accountTypeInfo,context);
	    //insert new role table
	    buildNewRole(roleInfo,context);
	  }
	}catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
  
  
  /**
   * Override user information in database, based
   * on userList from file
   */
  private void overrideUserInDatabase(Context context){
	//if userList is not empty
    if(!(usersFromFile.isEmpty())){
      // iterate user list
      for(User user : usersFromFile){
    	int roleId = user.getRoleId();
    	String address = user.getAddress();
    	int age = user.getAge();
    	String name = user.getName();
    	//cannot get password for current user, also cannot get password from database, since I clear database befotr running override methods
    	//so we should store a password for each user ???????
          DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
          insert.insertNewUser(name, age, address, roleId, user.getPassword());
          insert.close();
      }
    }else{
      System.out.println("nothing needs to be updated for user");
    }
  }
  
  /**
   * Override informaiton in database, based
   * on account list from file
   */
  private void overrideAccountInDatabase(Context context){
	//if account list is not empty
    if(!(accountsFromFile.isEmpty())){
      //iterate list
      for(Account account : accountsFromFile){
    	String name =  account.getName();
    	int typeId = account.getType();
    	BigDecimal balance = account.getBalance(context);
          DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
          insert.insertAccount(name, balance, typeId);
          insert.close();
      }
    }else{
      System.out.println("nothing needs to be updated for account");
    }
  }
  
  /**
   * chekc all accounts and users have valid role id
   * and account type ids in enum maps
   * @return true iff ids are in enum maps
   */
  private boolean checkType(Context context){
    for(User user : usersFromFile){
      //get role id for current user
      int roleId = user.getRoleId();
      //check if role id is in enum map
      boolean exist = RoleMap.getInstance(context).getmap().values().contains(roleId);
      //if role id is not in enum map
      if(!(exist)){
        return false;
      }
    }
    for(Account account : accountsFromFile){
      //get account type id for current account
      int typeId = account.getType();
      //check if type id is in enum map
      boolean exist = AccountTypesMap.getInstance(context).getmap().values().contains(typeId);
      //if type id is not in enum map
      if(!(exist)){
        return false;
      }
    }
	return true;
  }
  
  /**
   * get all account type info from database
   * @return all accountType infos
   * @throws SQLException
   */
  private static List<String> getAcountTypeInfo(Context context) throws SQLException{
    List<String> result = new ArrayList<String>();
    //update account type map
    AccountTypesMap.getInstance(context).updateAccountMap(context);
    //get numbers of account type in database
    int size = AccountTypesMap.getInstance(context).getmap().size();
      DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      //adding account type info to result list
    for(int i = 1; i < size + 1; i++){
      String info = "" + i + ",";
      info += select.getAccountTypeName(i) + ",";
      info += select.getInterestRate(i) + ",";
      //evering string has format: id,typeName,InterestRate
      result.add(info);
      info = "";     
    }
    select.close();
    return result;
  }
  
  /**
   * insert account type in new database
   * @param accountTypeInfo all account tyype information
   * from pervious datavase.
   */
  private void buildNewAccountType(List<String> accountTypeInfo, Context context){
    DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
      for(String info : accountTypeInfo){
      String[] splitInfo = info.split(",");
      insert.insertAccountType(splitInfo[1], new BigDecimal(splitInfo[2]));
    }
    insert.close();
  }
  
  /**
   * get all role info from database
   * @return all role info from database
   * @throws SQLException
   */
  private static List<String> getRoleInfo(Context context) throws SQLException{
    List<String> result = new ArrayList<String>();
	//update account type map
	RoleMap.getInstance(context).updateRoleMap(context);
	//get numbers of role in database
	int size = RoleMap.getInstance(context).getmap().size();

      DatabaseSelectHelper select = new DatabaseSelectHelper(context);
      //adding role info to result list
	for(int i = 1; i < size + 1; i++){
	  String info = "" + i + ",";
	  info += select.getRole(i);
	  //every string has format: roleId,roleName
	  result.add(info);
	  info = "";     
	  }
	  select.close();
	  return result;
	}
  
  /**
   * Insert role for new database
   * @param roleInfoList all role info from old database
   */
  private void buildNewRole(List<String> roleInfoList, Context context){
    DatabaseInsertHelper insert = new DatabaseInsertHelper(context);
      for(String roleInfo : roleInfoList){
      String[] splitInfo = roleInfo.split(",");
      insert.insertRole(splitInfo[1]);
    }
    insert.close();
  } 
}
