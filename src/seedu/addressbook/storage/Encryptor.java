package seedu.addressbook.storage;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Encrypts and decrypts a string using AES
 */
public class Encryptor {

    private static String key = "0000000000000000"; // Placeholder key

    /**
     * Encrypts string using the AES algorithm
     *
     * @param toEncrypt string to be encrypted
     * @return encrypted string
     */
    public static String encrypt(String toEncrypt) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());

            return DatatypeConverter.printBase64Binary(encrypted);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypts a string using the AES algorithm
     *
     * @param toDecrypt string to be decrypted
     * @return decrypted string
     */
    public static String decrypt(String toDecrypt) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            byte[] decodedData = DatatypeConverter.parseBase64Binary(toDecrypt);
            byte[] decryptedData = cipher.doFinal(decodedData);

            return new String(decryptedData);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
