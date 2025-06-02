package com.jame.safepassword.servicio;

import com.jame.safepassword.model.Password;

import java.util.List;

public interface IServiciePassword {
    Password createPassword();
    Password createPassword(Integer length);
    Password createPasswordAlphaNum();
    Password createPasswordAlphaNum(Integer length);
    List<Password> createPasswords(Integer nPasswords);
    List<Password> createPasswords(Integer nPasswords, Integer length);
}
