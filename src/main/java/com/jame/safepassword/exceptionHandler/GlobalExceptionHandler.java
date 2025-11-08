package com.jame.safepassword.exceptionHandler;

import com.jame.safepassword.exceptions.BadParamFormatException;
import com.jame.safepassword.exceptions.MinimumNumberPasswordsValidException;
import com.jame.safepassword.exceptions.PasswordNullException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(PasswordNullException.class)
   public ResponseEntity<Map<String, String>> handlePasswordNullException(PasswordNullException ex){
      return ResponseEntity.badRequest()
              .contentType(MediaType.APPLICATION_JSON)
              .body(Map.of("error", ex.getMessage()));
   }

   @ExceptionHandler(BadParamFormatException.class)
   public ResponseEntity<Map<String, String>> handleBadParamException(BadParamFormatException ex){
      return ResponseEntity.badRequest()
              .contentType(MediaType.APPLICATION_JSON)
              .body(Map.of("error", "Illegal param value"));
   }

   @ExceptionHandler(MinimumNumberPasswordsValidException.class)
   public ResponseEntity<Map<String, String>> handleMinimumValueValidException(MinimumNumberPasswordsValidException ex){
      return ResponseEntity.badRequest()
              .contentType(MediaType.APPLICATION_JSON)
              .body(Map.of("error", ex.getMessage()));
   }
}
