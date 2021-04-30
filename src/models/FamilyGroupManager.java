package models;

import exceptions.IncorrectEmailException;
import structures.treeavl.Tree;
import utils.Constants;
import utils.EmailSender;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FamilyGroupManager {
    private Tree<User> userList;

    public FamilyGroupManager() {
        userList = new Tree<>(User::compare);
    }

    public void addUser(User user) {
        userList.insert(user);
    }

    public User createMember(String id, String name, String emailUser) {
        return new User(id, name, emailUser, TypeAccount.MEMBER);
    }

    public User createAdmin(String id, String name, String emailUser) throws Exception {
        if (!isThereAdmin()) return new User(id, name, emailUser, TypeAccount.ADMIN);
        else throw new Exception("CREAR EXCEPCION");
    }

    public User removeMember(String id) throws Exception {
        User userToDelete = searchMember(id);
        userList.remove(userToDelete);
        return userToDelete;
    }

    public User searchMember(String id) {
        return userList.searchData(new User(id, "", "",null));
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

    public void sendEmailRecover(String email) throws MessagingException, IOException, IncorrectEmailException {
        EmailSender.sendEmail(email, Constants.SUBJECT_RECOVER_EMAIL, Constants.CONTENT_RECOVER_EMAIL + findUserPass(email));
    }

    private String findUserPass(String email) throws IncorrectEmailException {
//        try {
        return userList.getInorder().stream().map(User::getEmail).filter(oEmail -> oEmail.equals(email)).toString();

//        }catch (Exception e){
//            throw new IncorrectEmailException();
//        }
    }
}
