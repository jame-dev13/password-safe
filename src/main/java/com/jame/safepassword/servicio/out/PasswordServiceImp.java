package com.jame.safepassword.servicio.out;

import com.jame.safepassword.model.Password;
import com.jame.safepassword.repository.in.PasswordRepository;
import com.jame.safepassword.servicio.in.PasswordService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PasswordServiceImp implements PasswordService {

   private final PasswordRepository passwordRepository;

   public PasswordServiceImp(PasswordRepository passwordRepository) {
      this.passwordRepository = passwordRepository;
   }

   @Override
   public Password createPassword() {
      return new Password(passwordRepository.generatePassword());
   }

   @Override
   public Password createPassword(Integer length) {
      return new Password(passwordRepository.generatePassword(length));
   }

   @Override
   public Password createPasswordAlphaNum() {
      return new Password(passwordRepository.generatePasswordAlphaNum());
   }

   @Override
   public Password createPasswordAlphaNum(Integer length) {
      return new Password(passwordRepository.generatePasswordAlphaNum(length));
   }

   @Override
   public Set<Password> createPasswords(Integer nPasswords) {
      return passwordRepository.generateNPasswords(nPasswords);
   }

   @Override
   public Set<Password> createPasswords(Integer nPasswords, Integer length) {
      return passwordRepository.generateNPasswordsOfLength(nPasswords, length);
   }
}
