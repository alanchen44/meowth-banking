package com.example.group_0715.bankapp_group_0715.bank.exceptions;

public class ConnectionFailedException extends Exception {

  /**
   * This is the serialVersionUID for the exception.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Initialize a ConnectionFailedException object with no error message.
   * 
   * @see Exception
   */
  public ConnectionFailedException() {
    super();
  }

  /**
   * Initialize a ConnectionFailedException object with an error message.
   * 
   * @param error input the error message
   * @see Exception
   */
  public ConnectionFailedException(String error) {
    super(error);
  }

}
