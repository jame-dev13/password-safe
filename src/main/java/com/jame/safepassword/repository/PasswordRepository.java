package com.jame.safepassword.repository;
import com.jame.safepassword.model.Password;
import org.springframework.stereotype.Repository;
import java.security.SecureRandom;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.Collections;

@Repository
public class PasswordRepository {
    private static final int BASE_LENGTH = 16;
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHJIKLMNOPQRSTUVWXYZ";
    private static final String NUMS = "0123456789";
    private static final String SPEC = "!\"#$%&'()*+,-./:;<=>?@[]^_{|}~";
    private static final String pool = LOWER + UPPER + NUMS;
    public static final int MAX_LENGTH = 92;
    public static final int MIN_LENGTH = 8;
    public static final int MAX_ALPHA_NUM_LENGTH = 62;
    public static final int MAX_NUM_PASS = 15;
    public static final int MIN_NUM_PASS = 2;
    private static final SecureRandom random = new SecureRandom();

    /**
     *
     * @param pool
     * @param length
     * @param alphaNum
     * @return a List of Characters to make a password based on the params, the pool is
     * going to assign depends on the flag: alphaNum, if this is true there only be
     * alphanumeric characters in the list, otherwise the {@value SPEC} is going to concat.
     * the length param will be used to set in the public methods to set the length of the password.
     */
    private List<Character> createPassword(String pool, int length, boolean alphaNum){
        List<Character> current = addAtLeastOne(alphaNum);
        Set<Character> used = new HashSet<>(current);
        pool = (alphaNum) ? pool : pool.concat(SPEC);
        char[] chars = pool.toCharArray();
        while(current.size() < length){
            int index = random.nextInt(chars.length);
            char c = chars[index];
            if(!used.contains(c)){
                used.add(c);
                current.add(c);
            }
        }
        Collections.shuffle(current, random);
        return current;
    }

    /**
     *
     * @return a Password created of a random selection of
     * alphanumeric and special characters
     */
    public String generatePassword() {
        List<Character> characters = createPassword(pool, BASE_LENGTH, false);
        return characters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
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
    public String generatePassword(Integer length){
        length = (length > MAX_LENGTH) ? MAX_LENGTH: length;
        List<Character> characters = createPassword(pool, length, false);
        return characters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     *
     * @return a Password created using only alphanumeric and special characters
     */
    public String generatePasswordAlphaNum(){
        List<Character> characters = createPassword(pool, BASE_LENGTH, true);
        return characters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     * @param length
     * @return a Password created using only alphanumeric and special characters with variable length
     * @apiNote minimum length: {@value MIN_LENGTH} maximum: {@value MAX_ALPHA_NUM_LENGTH}
     * if length > {@value MAX_ALPHA_NUM_LENGTH} then truncate the length to {@value MAX_ALPHA_NUM_LENGTH}
     * if length < {@value MIN_LENGTH} then length = {@value MIN_LENGTH}.
     */
    public String generatePasswordAlphaNum(Integer length){
        length = (length > MAX_ALPHA_NUM_LENGTH)? MAX_ALPHA_NUM_LENGTH: length;
        List<Character> characters = createPassword(pool, length, true);
        return characters.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
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
    public List<Password> generateNPasswords(Integer n){
        n = (n < MIN_NUM_PASS) ? MIN_NUM_PASS : (n > MAX_NUM_PASS) ? MAX_NUM_PASS: n;
        List<Password> passwords = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            passwords.add(new Password(generatePassword()));
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
    public List<Password> generateNPasswordsOfLength(Integer n, Integer length){
        n = (n < MIN_NUM_PASS) ? MIN_NUM_PASS : (n > MAX_NUM_PASS) ? MAX_NUM_PASS: n;
        length = (length < MIN_LENGTH) ? MIN_LENGTH : (length > MAX_LENGTH) ? MAX_LENGTH : length;
        List<Password> passwords = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            passwords.add(new Password(generatePassword(length)));
        return passwords;
    }

    /**
     *
     * @param alphaNum
     * @return a List of Characters using at least one alphanumeric character (num, lower, upper) and the especial one
     * only if the param is false.
     */
    private List<Character> addAtLeastOne(boolean alphaNum){
        List<Character> lista = new ArrayList<>();
        if(alphaNum){
            lista.add(LOWER.charAt(random.nextInt(LOWER.length())));
            lista.add(UPPER.charAt(random.nextInt(UPPER.length())));
            lista.add(NUMS.charAt(random.nextInt(NUMS.length())));
        }else {
            lista.add(LOWER.charAt(random.nextInt(LOWER.length())));
            lista.add(UPPER.charAt(random.nextInt(UPPER.length())));
            lista.add(NUMS.charAt(random.nextInt(NUMS.length())));
            lista.add(SPEC.charAt(random.nextInt(SPEC.length())));
        }
        return lista;
    }
}
