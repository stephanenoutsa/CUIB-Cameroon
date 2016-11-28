package com.stephnoutsa.cuib.utils;

import android.util.Base64;

import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by stephnoutsa on 10/14/16.
 */
public class HashPassword {

    public HashPassword() {

    }

    public String hash(String password) {
        String hPassword;
        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory;
        byte[] hash = null;

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        hPassword = Base64.encodeToString(hash, Base64.DEFAULT);

        return hPassword;
    }

}
