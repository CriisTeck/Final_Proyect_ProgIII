package utils;

import java.util.stream.IntStream;

public class EncrypterString {

    public static String encrypt(String toEncrypt){
        int s = toEncrypt.length();
        int sum = doSum(toEncrypt) / s+1;
        int mod = 1%sum;
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s; i++) {
            ss.append((char) (toEncrypt.charAt(i) + mod));
        }
        return ss.toString();
    }

    private static int doSum(String toEncrypt) {
        return IntStream.range(0, toEncrypt.length()).sum();
    }

    public static String desencrypt(String toDesencrypt) {
        int s = toDesencrypt.length();
        int sum = doSum(toDesencrypt) / s+1;
        int mod = 1%sum;
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s; i++) {
            ss.append((char)(toDesencrypt.charAt(i) - mod));
        }
        return ss.toString();
    }

}
