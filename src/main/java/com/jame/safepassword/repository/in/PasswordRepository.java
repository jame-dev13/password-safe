package com.jame.safepassword.repository.in;

import com.jame.safepassword.model.Password;

import java.util.Set;

public interface PasswordRepository {
   String generatePassword();
   String generatePassword(Integer length);
   String generatePasswordAlphaNum();
   String generatePasswordAlphaNum(Integer length);
   Set<Password> generateNPasswords(Integer n);
   Set<Password> generateNPasswordsOfLength(Integer n, Integer length);

}
