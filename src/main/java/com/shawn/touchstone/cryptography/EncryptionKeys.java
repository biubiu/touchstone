/*
 * Copyright (C) 2019 Covata Limited or its affiliates
 *
 * Information contained within this file cannot be copied,
 * distributed and/or practised without the written consent of
 * Covata Limited or its affiliates.
 */

package com.shawn.touchstone.cryptography;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptionKeys {

    private KeyPairGenerator keyPairGenerator;

    private KeyPair pair;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    private SecretKey secretKey;

    public EncryptionKeys(int len) throws NoSuchAlgorithmException {
        this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        this.keyPairGenerator.initialize(len);
        createKey();
        createSecret();
    }

    private void createKey() {
        this.pair = this.keyPairGenerator.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    private void createSecret() throws NoSuchAlgorithmException {
        KeyGenerator aesGenerator = KeyGenerator.getInstance("AES");
        aesGenerator.init(128);
        this.secretKey = aesGenerator.generateKey();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
