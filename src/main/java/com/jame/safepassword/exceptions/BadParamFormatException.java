package com.jame.safepassword.exceptions;

public class BadParamFormatException extends IllegalArgumentException {
   public BadParamFormatException(String message) {
      super(message);
   }
}
