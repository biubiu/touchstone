package com.shawn.touchstone.cryptography;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.BaseEncoding;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * The RSA algorithm can only encrypt data that has a maximum byte length of the RSA key length in bits divided with
 * eight minus eleven padding bytes, i.e. number of maximum bytes = key length in bits / 8 - 11.
 * <p>
 * Encrypt the data with a symmetric key, and encrypt that key with RSA
 * <p>
 * 1. Generate a symmetric key
 * 2. Encrypt the data with the symmetric key
 * 3. Encrypt the symmetric key with rsa
 * 4. send the encrypted key and the data
 * 5. Decrypt the encrypted symmetric key with rsa
 * 6. decrypt the data with the symmetric key
 */
public class AsymmetricCryptography {

    private Cipher rsaCipher;

    private Cipher aesCipher;

    private SecretKey secretKey;

    private byte[] secrets;

    private static BaseEncoding BASE64 = BaseEncoding.base64();

    public AsymmetricCryptography(PublicKey publicKey, SecretKey secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        this.aesCipher = Cipher.getInstance("AES");
        this.rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        this.rsaCipher.init(Cipher.PUBLIC_KEY, publicKey);
        this.secretKey = secretKey;
        this.secrets = rsaCipher.doFinal(secretKey.getEncoded());
    }

    public String encrypt(String msg) throws InvalidKeyException {
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        try {
            return BASE64.encode(aesCipher.doFinal(msg.getBytes(Charsets.UTF_8)));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("encrypted error: " + Throwables.getStackTraceAsString(e));
        }
    }

    public byte[] getSecret() {
        return secrets;
    }

    public String decrypt(String msg, byte[] secret, PrivateKey key)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        rsaCipher.init(Cipher.PRIVATE_KEY, key);
        byte[] decryptedKey = rsaCipher.doFinal(secret);
        SecretKey secretKey = new SecretKeySpec(decryptedKey, 0, decryptedKey.length, "AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(aesCipher.doFinal(BASE64.decode(msg)), Charsets.UTF_8);
    }
}
