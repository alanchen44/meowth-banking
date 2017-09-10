package com.example.group_0715.bankapp_group_0715.bank.exceptions;

public class AuthenticationException extends Exception {
  
  /**
   * This is the serialVersionUID for the exception.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Initialize a AuthenticationException object with no error message.
   * 
   * @see Exception
   */
  public AuthenticationException() {
    super();
  }

  /**
   * Initialize a AuthenticationException object with an error message.
   * @param error input the error message.
   * @see Exception
   */
  public AuthenticationException(String error) {
    super(error);
  }

}
