package Structs.Criptografia;

import java.security.*;
import javax.crypto.*;

import Structs.filme;
import java.util.Base64;

public class RSA {
    private static final String RSA_ALGORITHM = "RSA";

    private static byte[] encrypt(String plaintext, PublicKey publicKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plaintext.getBytes());
    }

    private static byte[] decrypt(String encryptedText, PrivateKey privateKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        byte[] encryptedData = Base64.getDecoder().decode(encryptedText);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }

    public static filme encryptFilme (filme f, PublicKey publicKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{

        if(f == null){
            return null;
        }

        if(f.getName() != null){
            f.setName(Base64.getEncoder().encodeToString(encrypt(f.getName(), publicKey)));
        }if(f.getType() != null){
            f.setType(Base64.getEncoder().encodeToString(encrypt(f.getType(), publicKey)));
        }if(f.getCountry() != null){
            f.setCountry(Base64.getEncoder().encodeToString(encrypt(f.getCountry(), publicKey)));
        }if(f.getDescription() != null){
            f.setDescription(Base64.getEncoder().encodeToString(encrypt(f.getDescription(), publicKey)));
        }if(f.getDirector() != null){
            f.setDirector(Base64.getEncoder().encodeToString(encrypt(f.getDirector(), publicKey)));
        }if(f.getDateAdded() != null){
            f.setDateAdded(Base64.getEncoder().encodeToString(encrypt(f.getDateAdded(), publicKey)));
        }if(f.getRating() != null){
            f.setRating(Base64.getEncoder().encodeToString(encrypt(f.getRating(), publicKey)));
        }

        return f;
    }

    public static filme decryptFilme( filme f, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{

        if(f == null){
            return null;
        }

        if(f.getName() != null){
            f.setName(new String(decrypt(f.getName(), privateKey)));
        }if(f.getType() != null){
            f.setType(new String(decrypt(f.getType(), privateKey)));
        }if(f.getCountry() != null){
            f.setCountry(new String(decrypt(f.getCountry(), privateKey)));
        }if(f.getDescription() != null){
            f.setDescription(new String(decrypt(f.getDescription(), privateKey)));
        }if(f.getDirector() != null){
            f.setDirector(new String(decrypt(f.getDirector(), privateKey)));
        }if(f.getDateAdded() != null){
            f.setDateAdded(new String(decrypt(f.getDateAdded(), privateKey)));
        }if(f.getRating() != null){
            f.setRating(new String(decrypt(f.getRating(), privateKey)));
        }

        return f;
    }
}
