package utils;

import com.google.gson.Gson;

public class JSONUtils {
    private static final Gson gson = new Gson();

    public static Object JSONToString(String readUTF, Class<?> typeClass) {
        return gson.fromJson(readUTF, typeClass);
    }

    public static String requestToJSON(String requestCode, String title){
        return gson.toJson(new String[]{requestCode,title},String[].class);
    }
}
