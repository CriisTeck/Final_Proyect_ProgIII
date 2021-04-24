package utils;

public class EncrypterString {

    public static String encrypt(String toEncrypt){
        int s = toEncrypt.length();
        int mod = 10%s;
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s; i++) {
            ss.append((char) (toEncrypt.charAt(i) + mod));
        }
        return ss.toString();
    }

    public static String desencrypt(String toDesencrypt) {
        int s = toDesencrypt.length();
        int mod = 10%s;
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s; i++) {
            ss.append((char)(toDesencrypt.charAt(i) - mod));
        }
        return ss.toString();
    }

}
