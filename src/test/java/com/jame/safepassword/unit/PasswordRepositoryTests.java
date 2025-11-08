package com.jame.safepassword.unit;

import com.jame.safepassword.model.Password;
import com.jame.safepassword.repository.in.PasswordRepository;
import com.jame.safepassword.repository.out.PasswordRepositoryImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordRepositoryTests {
   private static final int REPEATS = 20;// repite la prueba 20 veces

   @Autowired
   private PasswordRepository psw;

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
   void passwordIsAlphaNumeric() {
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
   void nPasswords() {
      int n = 10;
      Set<Password> passwords = psw.generateNPasswords(n);
      assertEquals(n, passwords.size());
      System.out.println("request for " + n + " passwords");
      System.out.println("number of passwords generated: ");
      int resto = 1;
      for (Password password : passwords) {
         int nPwd = passwords.size() - (passwords.size() - resto);
         System.out.printf("%d: %s", nPwd, password.value());
         System.out.println();
         resto++;
      }

   }

   @DisplayName("reassigning length of the list when {n} it's greater than 'MAX_NUM_PASS'")
   @Test
   void reassignment() {
      int n = 30;
      final int MAX = PasswordRepositoryImp.MAX_NUM_PASS;
      Set<Password> passwords = psw.generateNPasswords(n);
      assertEquals(MAX, passwords.size());
      System.out.println("'n' requested: " + n);
      System.out.println("Maximum allowed: " + MAX);
      System.out.println("Current 'n' of passwords: " + passwords.size());
   }

   @DisplayName("reassigning {length} and {n} when they are greater than {MAX_LENGTH} and {MAX_NUM_PASS}")
   @Test
   void reassignmentLengthAndN() {
      int n = 30;
      int length = 100;
      final int MAX_N = PasswordRepositoryImp.MAX_NUM_PASS;
      final int MAX_LENGTH = PasswordRepositoryImp.MAX_LENGTH;
      Set<Password> listPasswords = psw.generateNPasswordsOfLength(n, length);
      System.out.println("N passwords requested: " + n);
      System.out.println("Length of each password requested: " + length);
      System.out.println("--------");
      System.out.println("The maximum num of passwords that can be generate is: " + MAX_N);
      System.out.println("The maximum length allowed for each password is: " + MAX_LENGTH);
      listPasswords.parallelStream().forEach(p -> System.out.println(p.value()));
      assertAll(
              () -> assertEquals(MAX_N, listPasswords.size()),
              () -> assertTrue(
                      listPasswords
                              .stream()
                              .map(p -> p.value().length())
                              .allMatch(l -> l == MAX_LENGTH)
                      , "The length do not matching.")
      );
   }
}

