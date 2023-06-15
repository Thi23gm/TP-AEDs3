package Structs.Criptografia;

import Structs.filme;

public class CifraDeFluxo {
    static String key = "mastersafe";

    public static String encrypt(String message, String key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char messageChar = message.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char encryptedChar = (char) (messageChar ^ keyChar);
            encrypted.append(encryptedChar);
        }
        return encrypted.toString();
    }

    public static String decrypt(String encryptedMessage, String key) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char encryptedChar = encryptedMessage.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char decryptedChar = (char) (encryptedChar ^ keyChar);
            decrypted.append(decryptedChar);
        }
        return decrypted.toString();
    }

    public static filme encryptFilme(filme f){
        if(f == null){
            return null;
        }
        if(f.getName() != null){
            f.setName(encrypt(f.getName(), key));
        }if(f.getType() != null){
            f.setType(encrypt(f.getType(), key));
        }if(f.getCountry() != null){
            f.setCountry(encrypt(f.getCountry(), key));
        }if(f.getDescription() != null){
            f.setDescription(encrypt(f.getDescription(), key));
        }if(f.getDirector() != null){
            f.setDirector(encrypt(f.getDirector(), key));
        }if(f.getDateAdded() != null){
            f.setDateAdded(encrypt(f.getDateAdded(), key));
        }if(f.getRating() != null){
            f.setRating(encrypt(f.getRating(), key));
        }

        return f;
    }

    public static filme decryptFilme(filme f){
        if(f == null){
            return null;
        }
        if(f.getName() != null){
            f.setName(decrypt(f.getName(), key));
        }if(f.getType() != null){
            f.setType(decrypt(f.getType(), key));
        }if(f.getCountry() != null){
            f.setCountry(decrypt(f.getCountry(), key));
        }if(f.getDescription() != null){
            f.setDescription(decrypt(f.getDescription(), key));
        }if(f.getDirector() != null){
            f.setDirector(decrypt(f.getDirector(), key));
        }if(f.getDateAdded() != null){
            f.setDateAdded(decrypt(f.getDateAdded(), key));
        }if(f.getRating() != null){
            f.setRating(decrypt(f.getRating(), key));
        }

        return f;
    }
}