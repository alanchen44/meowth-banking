package com.example.group_0715.bankapp_group_0715.bank.exceptions;

public class IllegalAmountException extends Exception {

  /**
   * This is the serialVersionUID for the exception.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Initialize a IllegalAmountException object with no error message.
   * 
   * @see Exception
   */
  public IllegalAmountException() {
    super();
  }

  /**
   * Initialize a IllegalAmountException object with an error message.
   * 
   * @param error the error message
   * @see Exception
   */
  public IllegalAmountException(String error) {
    super(error);
  }

}
