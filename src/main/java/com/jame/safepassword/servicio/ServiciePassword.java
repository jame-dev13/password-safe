package com.jame.safepassword.servicio;

import com.jame.safepassword.model.Password;
import com.jame.safepassword.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciePassword implements IServiciePassword {
    @Autowired
    private PasswordRepository repo;

    @Override
    public Password createPassword() {
        return new Password(repo.generatePassword());
    }

    @Override
    public Password createPassword(Integer length) {
        return new Password(repo.generatePassword(length));
    }

    @Override
    public Password createPasswordAlphaNum() {
        return new Password(repo.generatePasswordAlphaNum());
    }

    @Override
    public Password createPasswordAlphaNum(Integer length) {
        return new Password(repo.generatePasswordAlphaNum(length));
    }

    @Override
    public List<Password> createPasswords(Integer nPasswords) {
        return repo.generateNPasswords(nPasswords);
    }

    @Override
    public List<Password> createPasswords(Integer nPasswords, Integer length) {
        return repo.generateNPasswordsOfLength(nPasswords, length);
    }
}
