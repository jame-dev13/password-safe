package com.jame.safepassword.unit;

import static org.junit.jupiter.api.Assertions.*;

import com.jame.safepassword.model.Password;
import com.jame.safepassword.repository.PasswordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PasswordRepositoryTests {
    private static final int REPEATS = 20;// repite la prueba 20 veces

    private final PasswordRepository psw = new PasswordRepository();

    @Test
    @DisplayName("Passwords are not null.")
    void passwordsNotNull() {
        assertAll("All methods are returning something",
                () -> assertNotNull(psw.generatePassword()),
                () -> assertNotNull(psw.generatePassword(30)),
                () -> assertNotNull(psw.generatePasswordAlphaNum()),
                () -> assertNotNull(psw.generatePasswordAlphaNum(30)),
                () -> assertNotNull(psw.generateNPasswords(10)),
                () -> assertNotNull(psw.generateNPasswordsOfLength(5, 200))
        );
    }


    @DisplayName("The password contains at least one lowercase character, one uppercase character, one digit, and one special character.")
    @RepeatedTest(value = REPEATS,
            name = "{displayName} (iteration {currentRepetition}/{totalRepetitions})")
    void passwordHasEveryCharCategory() {
        String pwd = psw.generatePassword();
        assertAll(
                () -> assertTrue(pwd.chars().anyMatch(Character::isLowerCase),
                        "Missing lower case character"),
                () -> assertTrue(pwd.chars().anyMatch(Character::isUpperCase),
                        "Missing upper case character"),
                () -> assertTrue(pwd.chars().anyMatch(Character::isDigit),
                        "Missing digit"),
                () -> assertTrue(pwd.chars().anyMatch(c -> !Character.isLetterOrDigit(c)),
                        "Missing special character")
        );
        System.out.println(pwd);
    }

    @DisplayName("The password contains only alpha numeric characters")
    @Test
    void passwordIsAlphaNumeric(){
        String pwdAplhaNum = psw.generatePasswordAlphaNum();
        assertTrue(
                pwdAplhaNum
                .chars()
                .allMatch(Character::isLetterOrDigit),
                "Contains spec characters"
        );
        System.out.println(pwdAplhaNum);
    }

    @DisplayName("{n} passwords generated.")
    @Test
    void nPasswords(){
        int n = 10;
        List<Password> passwords = psw.generateNPasswords(n);
        assertEquals(n, passwords.size());
        System.out.println("request for " + n + " passwords");
        System.out.println("number of passwords generated: ");
        for (int i = 0; i < passwords.size(); i++) {
            System.out.println("n: " + (i + 1) + " -> " + passwords.get(i).value());
        }
    }

    @DisplayName("reassigning length of the list when {n} it's greater than 'MAX_NUM_PASS'")
    @Test
    void reassignment(){
        int n = 30;
        final int MAX = PasswordRepository.MAX_NUM_PASS;
        List<Password> passwords = psw.generateNPasswords(n);
        assertEquals(MAX, passwords.size());
        System.out.println("'n' requested: " + n);
        System.out.println("Maximum allowed: " + MAX);
        System.out.println("Current 'n' of passwords: " + passwords.size());
    }

    @DisplayName("reassigning {length} and {n} when they are greater than {MAX_LENGTH} and {MAX_NUM_PASS}")
    @Test
    void reassignmentLengthAndN(){
        int n = 30;
        int length = 100;
        final int MAX_N = PasswordRepository.MAX_NUM_PASS;
        final int MAX_LENGTH = PasswordRepository.MAX_LENGTH;
        List<Password> listPasswords = psw.generateNPasswordsOfLength(n, length);
        System.out.println("N passwords requested: " + n);
        System.out.println("Length of each password requested: " + length);
        System.out.println("--------");
        System.out.println("The maximum num of passwords that can be generate is: " + MAX_N);
        System.out.println("The maximum length allowed for each password is: " + MAX_LENGTH);
        assertAll(
                () ->  assertEquals(MAX_N, listPasswords.size()),
                () -> assertTrue(
                        listPasswords
                                .stream()
                                .map(p -> p.value().length())
                                .allMatch(l -> l == MAX_LENGTH)
                , "The length do not matching.")
        );
    }
}

