package com.jame.safepassword.repository.out;

import com.jame.safepassword.model.Password;
import com.jame.safepassword.repository.in.PasswordRepository;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PasswordRepositoryImp implements PasswordRepository {
   private static final int BASE_LENGTH = 16;
   private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
   private static final String UPPER = "ABCDEFGHJIKLMNOPQRSTUVWXYZ";
   private static final String NUMS = "0123456789";
   private static final String SPEC = "!\"#$%&'()*+,-./:;<=>?@[]^_{|}~";
   private static final String POOL = LOWER + UPPER + NUMS;
   public static final int MAX_LENGTH = 92;
   public static final int MIN_LENGTH = 8;
   public static final int MAX_ALPHA_NUM_LENGTH = 62;
   public static final int MAX_NUM_PASS = 15;
   public static final int MIN_NUM_PASS = 2;
   private static final SecureRandom random = new SecureRandom();

   private String buildPassword(int length, boolean alphaNum) {
      List<Character> characters = createPassword(length, alphaNum);
      return characters.stream()
              .map(String::valueOf)
              .collect(Collectors.joining());
   }

   /**
    *
    * @return a Password created of a random selection of
    * alphanumeric and special characters
    */
   public String generatePassword() {
      return buildPassword(BASE_LENGTH, false);
   }

   /**
    *
    * @param length Integer
    * @return a Password created of a random selection of alphanumeric
    * and special charcaters with variable length.
    * @apiNote there is a minimum length of: {@value MIN_LENGTH} and his maximum {@value MAX_LENGTH}
    * if length > {@value MAX_LENGTH} then truncate the length to {@value MAX_LENGTH}
    * if length < {@value MIN_LENGTH} then length = {@value MIN_LENGTH}.
    */
   public String generatePassword(Integer length) {
      return buildPassword(length, false);
   }

   /**
    *
    * @return a Password created using only alphanumeric and special characters
    */
   public String generatePasswordAlphaNum() {
      return buildPassword(BASE_LENGTH, true);
   }

   /**
    * @param length
    * @return a Password created using only alphanumeric and special characters with variable length
    * @apiNote minimum length: {@value MIN_LENGTH} maximum: {@value MAX_ALPHA_NUM_LENGTH}
    * if length > {@value MAX_ALPHA_NUM_LENGTH} then truncate the length to {@value MAX_ALPHA_NUM_LENGTH}
    * if length < {@value MIN_LENGTH} then length = {@value MIN_LENGTH}.
    */
   public String generatePasswordAlphaNum(Integer length) {
      return buildPassword(length, true);
   }

   /**
    *
    * @param n
    * @return a List of passwords of 'n' size, witch each password has a length of {@value BASE_LENGTH}
    * @apiNote minimum of 'n': {@value MIN_NUM_PASS} and maximum: {@value MAX_NUM_PASS}
    * if n < {@value MIN_NUM_PASS} then n = {@value MIN_NUM_PASS};
    * if n > {@value MAX_NUM_PASS} then n = {@value MAX_NUM_PASS};
    * other ways it continues with n.
    */
   public Set<Password> generateNPasswords(Integer n) {
      n = (n < MIN_NUM_PASS) ? MIN_NUM_PASS : Math.min(n, MAX_NUM_PASS);
      Set<Password> passwords = new HashSet<>(n);
      for (int i = 0; i < n; i++) {
         passwords.add(new Password(generatePassword()));
      }
      return passwords;
   }

   /**
    *
    * @param n
    * @param length
    * @return a List of Passwords of 'n' size and each password having a length variable
    * @apiNote minimum length: {@value MIN_LENGTH} maximum: {@value MAX_ALPHA_NUM_LENGTH}
    * and minimum 'n': {@value MIN_NUM_PASS} maximum: {@value MAX_NUM_PASS}
    * if length > {@value MAX_LENGTH} then truncate the length to {@value MAX_LENGTH}
    * if length < {@value MIN_LENGTH} then length = {@value MIN_LENGTH}.
    * Same logic applies to 'n'.
    */
   public Set<Password> generateNPasswordsOfLength(Integer n, Integer length) {
      n = (n < MIN_NUM_PASS) ? MIN_NUM_PASS : Math.min(n, MAX_NUM_PASS);
      length = (length < MIN_LENGTH) ? MIN_LENGTH : Math.min(length, MAX_LENGTH);
      Set<Password> passwords = new HashSet<>(n);
      for (int i = 0; i < n; i++) {
         passwords.add(new Password(generatePassword(length)));
      }
      return passwords;
   }

   /**
    * @param alphaNum boolean flag that indicates if the password is only Alphanumeric characters.
    * @return a List of Characters using at least one alphanumeric character (num, lower, upper) and the especial one
    * only if the param is false.
    */
   private List<Character> addAtLeastOne(boolean alphaNum) {
      List<Character> characters = new ArrayList<>();
      if (alphaNum) {
         characters.add(LOWER.charAt(random.nextInt(LOWER.length())));
         characters.add(UPPER.charAt(random.nextInt(UPPER.length())));
         characters.add(NUMS.charAt(random.nextInt(NUMS.length())));
         return characters;
      }
      characters.add(LOWER.charAt(random.nextInt(LOWER.length())));
      characters.add(UPPER.charAt(random.nextInt(UPPER.length())));
      characters.add(NUMS.charAt(random.nextInt(NUMS.length())));
      characters.add(SPEC.charAt(random.nextInt(SPEC.length())));
      return characters;
   }

   /**
    * Call to {@linkplain #addAtLeastOne(boolean)}  to add at least one each category character (lower,
    * upper, numeric) and special character if the flag is true. After that it use a {@code Set<Character>}
    * to build the password with no repeated characters.
    * @param length   the maximum length for the password.
    * @param alphaNum flag to know if the password going to be alphanumeric only.
    * @return List of characters of a specific length, alphanumeric or containing special characters
    */
   private List<Character> createPassword(int length, boolean alphaNum) {
      length = (length < MIN_LENGTH) ? MIN_LENGTH : Math.min(length, MAX_LENGTH);
      List<Character> current = addAtLeastOne(alphaNum);
      Set<Character> used = new HashSet<>(current);
      String pool = (alphaNum) ? POOL : POOL.concat(SPEC);

      char[] chars = pool.toCharArray();
      while (current.size() < length) {
         int index = random.nextInt(chars.length);
         char c = chars[index];
         if (used.add(c)) {
            current.add(c);
         }
      }
      Collections.shuffle(current, random);
      return current;
   }
}
