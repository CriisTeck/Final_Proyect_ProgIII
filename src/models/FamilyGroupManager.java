package models;

import exceptions.EmailAlreadyRegisteredException;
import exceptions.EmailNotRegisteredException;
import exceptions.IncorrectEmailException;
import exceptions.UserAlreadyExistsException;
import structures.treeavl.Tree;
import utils.StringConstants;
import utils.EmailSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;

public class FamilyGroupManager {
    private Tree<User> userList;

    public FamilyGroupManager() {
        userList = new Tree<>(User::compare);
    }

    public void addUser(User user) throws UserAlreadyExistsException, EmailAlreadyRegisteredException {
        if (verifyUserRegistered(user)) userList.insert(user);
        else throw new UserAlreadyExistsException();
    }

    private boolean verifyUserRegistered(User user) throws UserAlreadyExistsException, EmailAlreadyRegisteredException {
        ArrayList<User> users = userList.getInorder();
        for (User value : users) {
            if (value.getid().split("@")[0].equals(user.getid().split("@")[0]))
                throw new UserAlreadyExistsException();
            if (value.getEmail().equals(user.getEmail()))
                throw new EmailAlreadyRegisteredException();
        }
        return true;
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
        return userList.searchData(new User(id, "", "", null));
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

    public void sendEmailRecover(String email) throws MessagingException, IOException, IncorrectEmailException, EmailNotRegisteredException {
        String[] data = findUserPass(email);
        EmailSender.sendEmail(email, StringConstants.SUBJECT_RECOVER_EMAIL, String.format(StringConstants.CONTENT_RECOVER_EMAIL, data[0], data[1]));
    }

    private String[] findUserPass(String email) throws EmailNotRegisteredException {
        for (User user : userList.getInorder()) {
            String em = user.getEmail();
            if (user.getEmail().equals(email))
                return user.getid().split("@");
        }
        throw new EmailNotRegisteredException();
    }
}
