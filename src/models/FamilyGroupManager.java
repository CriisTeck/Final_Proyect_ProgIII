package models;

import structures.treeavl.Tree;

public class FamilyGroupManager {
    private Tree<User> userList;

    public FamilyGroupManager() {
        userList = new Tree<>(User::compare);
    }

    public void addUser(User user){
        userList.insert(user);
    }


}
