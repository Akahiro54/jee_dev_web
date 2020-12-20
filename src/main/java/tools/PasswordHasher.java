package tools;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordHasher {


    private static final byte[] salt = {-115, 60, -116, 73, 76, -19, -84, 39, -44, 119, 10, -59, 55, -50, -46, 10};

    /**
     * Hashes the password given in parameters using PBKDF2 Algorithm and class salt.
     * @param password String representing the password
     * @return array byte representing the hashed password
     * @throws NoSuchAlgorithmException if the PBKDF2 Algorithm isn't found
     * @throws InvalidKeySpecException if the KeySpec is incorrect
     */
    public static byte[] getPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return hash ;
    }


}
