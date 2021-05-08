package utils;

import com.google.gson.InstanceCreator;
import models.User;

import java.lang.reflect.Type;
import java.util.Comparator;

public class ComparatorUserCreator implements InstanceCreator<Comparator> {
    private final Comparator<User> user;

    public ComparatorUserCreator(Comparator<User> context) {
        this.user = context;
    }

    @Override
    public Comparator<User> createInstance(Type type) {
        return user;
    }
}