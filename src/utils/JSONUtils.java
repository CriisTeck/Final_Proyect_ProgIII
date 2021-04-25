package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Notification;
import models.User;

import java.util.Comparator;

public class JSONUtils {
    private static final Gson gson = new Gson();

    public static Object objectFromJSON(String readUTF, Class<?> typeClass) {
        return gson.fromJson(readUTF, typeClass);
    }

    public static User userFromJSON(String jsonString, Class<User> typeClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Comparator.class, new ComparatorCreator(Comparator.comparingInt(Notification::getId)));
        Gson customGson = gsonBuilder.create();

        return customGson.fromJson(jsonString,typeClass);
    }

    public static String requestToJSON(String requestCode, String title) {
        return gson.toJson(new String[]{requestCode, title}, String[].class);
    }
}
