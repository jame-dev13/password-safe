package com.jame.safepassword.servicio.in;

import com.jame.safepassword.model.Password;

import java.util.Set;

public interface PasswordService {
    Password createPassword();
    Password createPassword(Integer length);
    Password createPasswordAlphaNum();
    Password createPasswordAlphaNum(Integer length);
    Set<Password> createPasswords(Integer nPasswords);
    Set<Password> createPasswords(Integer nPasswords, Integer length);
}
