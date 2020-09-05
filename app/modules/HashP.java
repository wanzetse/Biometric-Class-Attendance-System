package modules;

import org.mindrot.jbcrypt.BCrypt;

public class HashP {
    public  static String haspP(String plainText){

        return BCrypt.hashpw(plainText,BCrypt.gensalt());
    }
    public static boolean checkP(String plainText,String hashedText) {
        if (plainText.length() < 1 || plainText == null) {

            return false;
        }
        return BCrypt.checkpw(plainText,hashedText);
    }
}
