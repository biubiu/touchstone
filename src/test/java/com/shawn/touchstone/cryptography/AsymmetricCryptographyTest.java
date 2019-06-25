package com.shawn.touchstone.cryptography;


import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AsymmetricCryptographyTest {

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private static final int LEN = 1024;

    private AsymmetricCryptography cryptography;

    private static final String TEST_TEXT = "There is an existing API on SafeShare to create an organisation\n" +
            "\n" +
            "/api/v1/organisations  \n" +
            "\n" +
            "{\n" +
            "  \"name\":\"test org1\",\n" +
            "  \"quota\":1024,\n" +
            "  \"adminEmail\":\"org01@org1.com\",\n" +
            "  \"contactEmail\":\"org01@org1.com\"\n" +
            "}\n" +
            "    \n" +
            "\n" +
            "After the organisation is successfully created, two emails would be generated and sent to the admin email address: one is organisation creation notification and another is  Covata account creation notification if the user hasn’t previously registered. \n" +
            "\n" +
            "Authentication\n" +
            "\n" +
            "The API requires the authenticated user has organisation creation permission. Since the webform is submitted from a non SafeShare site and I assume there would be no SSO between these two sites, we could create a new endpoint reusing the same logic of the existing organisation creation API with an alternative security approach to sign and authenticate the REST requests.   \n" +
            "\n" +
            "Request authenticating proposal\n" +
            "We could generate a signature and post along with the webfrom. \n" +
            "Generating asymmetric keys, the client side (web form) uses the secret key to generate the signature and server side (Safeshare) uses the public to validate the signature\n" +
            "\n" +
            "ContentToSign \n" +
            "this would be a string based on selected request elements (could be request body, url, .etc)\n" +
            "\n" +
            "Singing Key \n" +
            "- dataKey = HMAC-SHA256(secrectKey, timestamp)\n" +
            "- we could add more functions to rehash the dataKey\n" +
            "- signingKey = HMAC-SHA256(dataKey, “org_request“)\n" +
            "\n" +
            "Signature \n" +
            " signature = Hex(HMAC-SHA256(SigningKey,ContentToSign))\n" +
            "\n" +
            "CORS \n" +
            "\n" +
            "As the web form is submitted from a non SafeShare website and calling the API is cross the domain, we need to tweak CORS settings on SafeShare backend side.";

    @Before
    public void setup() throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        EncryptionKeys keys = new EncryptionKeys(LEN);
        this.privateKey = keys.getPrivateKey();
        this.publicKey = keys.getPublicKey();
        cryptography = new AsymmetricCryptography(publicKey, keys.getSecretKey());
    }

    @Test
    public void testPublicPrivateDecrypt() throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        String encryptedContent = cryptography.encrypt(TEST_TEXT);
        String decryptedContent = cryptography.decrypt(encryptedContent, cryptography.getSecret(), privateKey);
        assertThat(decryptedContent, is(TEST_TEXT));
    }
}