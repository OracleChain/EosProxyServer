package com.oraclechain.eosio.crypto.util;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CryptUtil {

    public static byte[] aesEncrypt( byte[] key, byte[] data, byte[] iv ) {

        byte[] encrypted = null;

        try {

            SecretKey secureKey = new SecretKeySpec(key, "AES");
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

            c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv));

            encrypted = c.doFinal(data);

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return encrypted;
    }

    public static byte[] aesDecrypt( byte[] key, byte[] data,  byte[] iv) {
        byte[] decrypted = null;
        try {
            SecretKey secureKey = new SecretKeySpec(key, "AES");
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

            if ( iv.length > 16 ) {
                iv = Arrays.copyOf( iv, 16 ); // aes/cbc 에선 iv 는 16-byte !
            }

            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv));

            decrypted = c.doFinal(data);

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return decrypted;
    }
}