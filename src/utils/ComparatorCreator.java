package utils;

import com.google.gson.InstanceCreator;
import models.Notification;

import java.lang.reflect.Type;
import java.util.Comparator;

public class ComparatorCreator implements InstanceCreator<Comparator> {
    private Comparator<Notification> user;

    public ComparatorCreator(Comparator<Notification> context) {
        this.user = context;
    }

    @Override
    public Comparator<Notification> createInstance(Type type) {
        Comparator<Notification> not = user;
        return user;
    }

//    @Override
//    public UserContext createInstance(Type type) {
//        // create new object with our additional property
//        UserContext userContext = new UserContext(context);
//
//        // return it to gson for further usage
//        return userContext;
//    }
}