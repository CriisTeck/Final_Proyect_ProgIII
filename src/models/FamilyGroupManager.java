package models;

import exceptions.EmailAlreadyRegisteredException;
import exceptions.EmailNotRegisteredException;
import exceptions.UserAlreadyExistsException;
import persistence.JSONUserInfo;
import structures.treeavl.Tree;
import utils.StringConstants;
import utils.EmailSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;

public class FamilyGroupManager {
    private Tree<User> userList;
    private final JSONUserInfo file;

    public FamilyGroupManager() throws IOException {
        file = new JSONUserInfo();
        userList = read();
        if (userList == null)
            userList = new Tree<>(User::compare);
    }

    public void addUser(User user) throws UserAlreadyExistsException, EmailAlreadyRegisteredException, IOException {
        if (verifyUserRegistered(user)) {
            userList.insert(user);
            write();
        } else throw new UserAlreadyExistsException();
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

    public void removeMember(String id) throws Exception {
        User userToDelete = searchMember(id);
        userList.remove(userToDelete);
        write();
    }

    public User searchMember(String id) {
        return userList.searchData(new User(id, "", "", null));
    }

    public boolean isThereAdmin() {
        return userList.getInorder().stream().anyMatch(User::isAdmin);
    }

    public void sendEmailRecover(String email) throws MessagingException, IOException, EmailNotRegisteredException {
        String[] data = findUserPass(email);
        EmailSender.sendEmail(email, StringConstants.SUBJECT_RECOVER_EMAIL, String.format(StringConstants.CONTENT_RECOVER_EMAIL, data[0], data[1]));
    }

    private String[] findUserPass(String email) throws EmailNotRegisteredException {
        for (User user : userList.getInorder()) {
            if (user.getEmail().equals(email))
                return user.getid().split("@");
        }
        throw new EmailNotRegisteredException();
    }

    public ArrayList<User> getAllUsers() {
        userList.getInorder().forEach(User::calculateDebt);
        return userList.getInorder();
    }

    public void write() throws IOException {
        file.writeJSONFile(userList);
    }

    private Tree<User> read() throws IOException {
        return file.readJSONFile();
    }
}
