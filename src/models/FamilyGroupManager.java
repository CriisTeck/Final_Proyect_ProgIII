package models;

import structures.treeavl.Tree;

import java.util.ArrayList;

public class FamilyGroupManager {
    private Tree<User> userList;

    public FamilyGroupManager() {
        userList = new Tree<>(User::compare);
    }

    public void addUser(User user) {
        userList.insert(user);
    }

    public User createMember(String id, String name) {
        return new User(id, name, TypeAccount.MEMBER);
    }

    public User createAdmin(String id, String name) throws Exception {
        if (!isThereAdmin()) return new User(id, name, TypeAccount.ADMIN);
        else throw new Exception("CREAR EXCEPCION");
    }

    public User removeMember(String id) throws Exception {
        User userToDelete = searchMember(id);
        userList.remove(userToDelete);
        return userToDelete;
    }

    public User searchMember(String id) {
        return userList.searchData(new User(id, "", null));
    }

    public boolean isThereAdmin() {
        return userList.getInorder().stream().anyMatch(User::isAdmin);
    }

    public void acceptDebtPayment() {

    }

    public void denyDebtPayment() {

    }

    public void setMountMaximumGeneral() {

    }

    public void denyMember() {

    }

    public void acceptMember() {

    }
}
