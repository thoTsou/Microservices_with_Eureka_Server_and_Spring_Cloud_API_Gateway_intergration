package com.thotsou.user.register.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;

@Service
public final class PasswordStorageService {

    @Value("${aes.secret.password}")
    private String AES_SECRET_PASSWORD;

    @Value("${aes.secret.salt}")
    private String SALT;
    
    
    public String encrypt(String stringToEncrypt) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {
        return Base64.getEncoder()
                .encodeToString(this.initializeCipher(Cipher.ENCRYPT_MODE).doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    public String decrypt(String stringToDecrypt) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {
        return new String(
                this.initializeCipher(Cipher.DECRYPT_MODE).doFinal(Base64.getDecoder().decode(stringToDecrypt)),
                StandardCharsets.UTF_8
        );
    }

    private Cipher initializeCipher(int opMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {
        byte[] iv = new byte[16];
        int counter = 0;
        List<Integer> inteList = List.of(6,6,2,8,0,3,9,0,1,7,1,8,5,0,4,9);
        for(Integer integer : inteList) {
            iv[counter++] = integer.byteValue();
        }
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        PBEKeySpec spec = new PBEKeySpec(AES_SECRET_PASSWORD.toCharArray(), SALT.getBytes(StandardCharsets.UTF_8), 1000, 256);
        SecretKey tmp = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipherAESCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherAESCBC.init(opMode, secretKey, ivSpec);
        return cipherAESCBC;
    }
}
