package com.yjg.othertest;

import com.dingxin.scp.common.push.utils.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RSA {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVfisoOLrHQTZcAoQgKs36C0tOA4Kn6O6mYTxtBntO9dH/F+qYYXkNw8YJe2qDVIECeZSjgIZvsqItui9sJYNDAlIwW8QUguLjzrP/uJs9XjuniUvvDEEyTDBUOqUUr8164RUgGjt/7GhD5/EPnmir0hnji4bsP3bZX7QK6uOhUwIDAQAB";
        String testStr = "TEST0512";

        byte[] data = testStr.getBytes(Charset.forName("utf-8"));
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = doFinal(cipher, data, 128-11);
        //加密后的字符
        String string = new String(java.util.Base64.getEncoder().encode(result), StandardCharsets.UTF_8);

        System.out.println(string);
    }
    private static byte[] doFinal(Cipher cipher, byte[] data, int key_len) throws BadPaddingException, IllegalBlockSizeException {
        int inputLen = data.length, offset = 0;
        byte[] tmp;
        ByteArrayOutputStream out = new ByteArrayOutputStream(getTmpArrayLength(inputLen));
        while (inputLen > 0) {
            tmp = cipher.doFinal(data, offset, Math.min(key_len, inputLen));
            out.write(tmp, 0, tmp.length);
            offset += key_len;
            inputLen -= key_len;
        }
        return out.toByteArray();
    }
    private static int getTmpArrayLength(int L) {
        int S = 128;
        while (S < L) {
            S <<= 1;
        }
        return S;
    }
}
