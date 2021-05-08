package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Notification;
import models.User;
import structures.treeavl.Tree;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Comparator;

import static utils.StringConstants.NAME_FILE_E_C;
import static utils.EncrypterString.*;

public class JSONUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Object objectFromJSON(String readUTF, Class<?> typeClass) {
        return gson.fromJson(readUTF, typeClass);
    }

    public static User userFromJSON(String jsonString, Class<User> typeClass) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Comparator.class, new ComparatorCreator(Comparator.comparingInt(Notification::getId)));
        Gson customGson = gsonBuilder.create();
        return customGson.fromJson(jsonString, typeClass);
    }

    public static String requestToJSON(String requestCode, String title) {
        return gson.toJson(new String[]{requestCode, title}, String[].class);
    }

    public static String userEmailFromJSONFile() throws IOException {
        String ad = desencrypt(NAME_FILE_E_C);
        FileReader fr = new FileReader(ad);
        BufferedReader br = new BufferedReader(fr);
        String mss;
        mss = desencrypt(br.readLine());
        return gson.fromJson(mss, String[].class)[0];
    }

    public static String userPassEmailFromJSONFile() throws IOException {
        String ad = desencrypt(NAME_FILE_E_C);
        FileReader fr = new FileReader(ad);
        BufferedReader br = new BufferedReader(fr);
        String mss;
        mss = desencrypt(br.readLine());
        return gson.fromJson(mss, String[].class)[1];
    }

    public static String exceptionToJSON(String exception, String message) {
        return gson.toJson(new String[]{exception, message});
    }

    public static String objectToJSON(Object objects, Class<?> className) {
        return gson.toJson(objects, className);
    }

    public static Tree readDataFromJson(String toString) {
        Type type = new TypeToken<Tree<User>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Comparator.class, new ComparatorUserCreator(Comparator.comparing(User::getid)));

        return gsonBuilder.create().fromJson(toString, type);
    }
}
