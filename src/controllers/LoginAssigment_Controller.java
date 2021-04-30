package controllers;

import exceptions.IncorrectEmailException;
import models.FamilyGroupManager;
import models.User;
import net.Connection;
import persistence.WriterLog;
import utils.CodeRequest;
import utils.JSONUtils;
import utils.TitleRequest;

import javax.mail.MessagingException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import static utils.JSONUtils.*;

public class LoginAssigment_Controller extends Thread {
    private Client loginClient;
    private Socket socket;
    private FamilyGroupManager model;
    private WriterLog writerLog;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean state;

    public LoginAssigment_Controller(Socket socket, FamilyGroupManager model, WriterLog writerLog) throws IOException {
        this.socket = socket;
        this.model = model;
        this.writerLog = writerLog;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        state = true;
        this.start();
    }

    @Override
    public void run() {
        System.out.println("Login activado");
        try {
            while (state){
                if (input.available() > 0) {
                    String code = readData();
                    switch (code) {
                        case CodeRequest.REQUEST_SIGN_IN_CODE:
                            readCredentials();
                            break;
                        case CodeRequest.REQUEST_NEW_USER_CODE:
                            createNewUser();
                            break;
                        case CodeRequest.REQUEST_CLOSE_CONEXION_CODE:
                            socket.close();
                            break;
                        case CodeRequest.REQUEST_SEND_EMAIL_RECOVER_CODE:
                            sendRecoverEmail();
                            break;
                    }
                }
                if(socket.isClosed())
                    state = false;
            }
        } catch (IOException | MessagingException | IncorrectEmailException e) {
            e.printStackTrace();
        }
    }


    private void createNewUser() throws IOException {
        output.writeUTF(model.isThereAdmin()
                ? requestToJSON(CodeRequest.THERE_IS_ADMIN_CODE, TitleRequest.THERE_IS_ADMIN_TITLE)
                : requestToJSON(CodeRequest.THERE_ISNT_ADMIN_CODE, TitleRequest.THERE_ISNT_ADMIN_TITLE));
        User newUser = userFromJSON(input.readUTF(), User.class);
        model.addUser(newUser);
        readCredentials(newUser.getid());
    }

    private void readCredentials(String getId) {
        String[] credentials = getId.split("@");
        if (createClient(credentials[0], credentials[1])) state = false;
    }

    private String readData() throws IOException {
        return ((String[]) objectFromJSON(input.readUTF(), String[].class))[0];
    }

    private void readCredentials() throws IOException {
        String[] credentials = (String[]) objectFromJSON(input.readUTF(), String[].class);
        System.out.println(Arrays.toString(credentials));
        if (!createClient(credentials[0], credentials[1])) sendFailedLogin();
        else state = false;

    }

    private void sendFailedLogin() throws IOException {
        output.writeUTF(requestToJSON(CodeRequest.FAILED_LOGIN_INEXIST_USER_CODE, TitleRequest.FAILED_LOGIN_TITLE));
    }

    private boolean createClient(String userName, String password) {
        boolean result = true;
        User user = model.searchMember(userName + "@" + password);
        if (user == null) result = false;
        else loginClient = user.isAdmin()
                ? new AdminController(writerLog, new Connection(socket, writerLog), model)
                : new MemberController(writerLog, new Connection(socket, writerLog), user);
        return result;
    }

    public Client getClient() {
        return loginClient;
    }

    private void sendRecoverEmail() throws IOException, MessagingException, IncorrectEmailException {
        String email = (String) JSONUtils.objectFromJSON(input.readUTF(), String.class);
        model.sendEmailRecover(email);
    }
}
