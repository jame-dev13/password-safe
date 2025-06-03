package com.jame.safepassword.rest;

import com.jame.safepassword.model.Password;
import com.jame.safepassword.servicio.ServiciePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class PasswordRest {

    @Autowired
    private ServiciePassword servicio;

    @GetMapping("/password")
    public ResponseEntity<Password> getNormalPassword(){
        Password password = servicio.createPassword();
        if(password != null)
            return ResponseEntity.ok(password);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/password/length")
    public ResponseEntity<Password> getLengthPassword(@RequestParam Integer length){
        Password password = servicio.createPassword(length);
        if(password != null)
            return ResponseEntity.ok(password);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/password/alphaNum")
    public ResponseEntity<Password> getPasswordAlphaNum(){
        Password password = servicio.createPasswordAlphaNum();
        if(password != null)
            return ResponseEntity.ok(password);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/password/alphaNum/length")
    public ResponseEntity<Password> getPasswordAlphaNumLength(@RequestParam Integer length){
        Password password = servicio.createPasswordAlphaNum(length);
        if(password != null)
            return ResponseEntity.ok(password);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/passwords/num")
    public List<Password> getPasswords(@RequestParam Integer num)
    {
        if(num >= 1)
            return servicio.createPasswords(num);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El valor debe de ser mayor a 1.");
    }

    @GetMapping("/passwords/num/length")
    public List<Password> getPasswords(@RequestParam Integer num, @RequestParam Integer length)
    {
        if(num >= 1 && length > 7)
            return servicio.createPasswords(num, length);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El valor debe de ser mayor a 1 y la longitud mayor a 7");
    }
}
