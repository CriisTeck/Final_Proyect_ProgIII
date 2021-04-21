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

    public User createMember(int id, String name){
        return new User(id,name,TypeAccount.MEMBER);
    }

    public User createAdmin(int id,String name) throws Exception {
        if(!isThereAdmin()) return new User(id,name,TypeAccount.ADMIN);
        else throw new Exception("CREAR EXCEPCION");
    }

    public User removeMember(int id) throws Exception {
        User userToDelete = searchMember(id);
        userList.remove(userToDelete);
        return userToDelete;
    }

    private User searchMember(int id) {
        return userList.searchData(new User(id, "",null));
    }

    private boolean isThereAdmin() {
        return userList.getInorder().stream().anyMatch(User::isAdmin);
    }

    public void acceptDebtPayment(){

    }

    public void denyDebtPayment() {

    }

    public void setMountMaximumGeneral() {

    }

    public void denyMember() {

    }

    public void acceptMember(){

    }


}
