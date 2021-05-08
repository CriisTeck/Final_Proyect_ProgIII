package utils;

import com.google.gson.InstanceCreator;
import models.Notification;

import java.lang.reflect.Type;
import java.util.Comparator;

public class ComparatorCreator implements InstanceCreator<Comparator> {
    private final Comparator<Notification> user;

    public ComparatorCreator(Comparator<Notification> context) {
        this.user = context;
    }

    @Override
    public Comparator<Notification> createInstance(Type type) {
        return user;
    }
}