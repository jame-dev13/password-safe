package com.jame.safepassword.rest;

import com.jame.safepassword.exceptions.BadParamFormatException;
import com.jame.safepassword.exceptions.MinimumNumberPasswordsValidException;
import com.jame.safepassword.exceptions.PasswordNullException;
import com.jame.safepassword.model.Password;
import com.jame.safepassword.servicio.in.PasswordService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;


@RestController
@RequestMapping("${app.route.uri}")
public class PasswordController {
   private static final int MIN_N_PASSWORDS = 3;

   private final PasswordService passwordService;

   public PasswordController(PasswordService passwordService) {
      this.passwordService = passwordService;
   }

   @GetMapping
   public ResponseEntity<Password> getNormalPassword() {
      Password password = passwordService.createPassword();
      if (Objects.isNull(password)) {
         throw new PasswordNullException("Password null.");
      }
      return ResponseEntity.ok()
              .body(passwordService.createPassword());
   }

   @GetMapping("/password/{length}")
   public ResponseEntity<Password> getLengthPassword(@PathVariable Integer length) throws BadParamFormatException {
      Password password = passwordService.createPassword(length);
      if (Objects.isNull(password)) {
         throw new PasswordNullException("Password null.");
      }

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(password);
   }

   @GetMapping("/password/alphaNum")
   public ResponseEntity<Password> getPasswordAlphaNum() {
      Password password = passwordService.createPasswordAlphaNum();
      if (Objects.isNull(password)) {
         throw new PasswordNullException("Password null.");
      }
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(password);
   }

   @GetMapping("/password/alphaNum/{length}")
   public ResponseEntity<Password> getPasswordAlphaNumLength(@PathVariable Integer length) throws BadParamFormatException {
      Password password = passwordService.createPasswordAlphaNum(length);
      if (Objects.isNull(password)) {
         throw new PasswordNullException("Password null.");
      }
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(password);
   }

   @GetMapping("/{n}")
   public ResponseEntity<Set<Password>> getPasswords(@PathVariable Integer n) throws BadParamFormatException {
      if (n <= 0) {
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(passwordService.createPasswords(MIN_N_PASSWORDS));
      }
      Set<Password> passwords = passwordService.createPasswords(n);
      if (Objects.isNull(passwords)) {
         throw new PasswordNullException("Password set is null.");
      }
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(passwords);
   }

   @GetMapping("/{n}/{length}")
   public ResponseEntity<Set<Password>> getPasswords(@PathVariable Integer n, @PathVariable Integer length) throws BadParamFormatException {
      if(n <= 0){
         throw new MinimumNumberPasswordsValidException("'n' value must be greater than 0.");
      }
      if(length < 8){
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(passwordService.createPasswords(n));
      }

      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(passwordService.createPasswords(n, length));
   }
}
