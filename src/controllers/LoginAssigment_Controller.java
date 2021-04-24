package controllers;

import models.FamilyGroupManager;
import models.User;
import net.Connection;
import persistence.WriterLog;
import utils.CodeRequest;
import utils.Constants;
import utils.TitleRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static utils.JSONUtils.JSONToString;
import static utils.JSONUtils.requestToJSON;

public class LoginAssigment_Controller extends Thread {
    private Client loginClient;
    private Socket socket;
    private FamilyGroupManager model;
    private WriterLog writerLog;
    private DataInputStream input;
    private DataOutputStream output;

    public LoginAssigment_Controller(Socket socket, FamilyGroupManager model, WriterLog writerLog) throws IOException {
        this.socket = socket;
        this.model = model;
        this.writerLog = writerLog;
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        this.start();
    }

    @Override
    public void run() {
        try {
            if(input.available() > 0){
            String code = readData();
            switch (code){
                case CodeRequest.REQUEST_SIGN_IN_CODE:
                    readCredentials();
                    break;
            }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readData() throws IOException {
        return ((String[]) JSONToString(input.readUTF(), String[].class))[0];
    }

    private void readCredentials() throws IOException {
        output.writeUTF(requestToJSON(CodeRequest.REQUEST_CREDENTIALS_CODE,TitleRequest.REQUEST_CREDENTIALS_TITLE));
        String[] credentials = (String[]) JSONToString(input.readUTF(), String[].class);
        if (!createClient(credentials[0], credentials[1])) {
            sendFailedLogin();
            createNewUser();
        }
    }

    private void sendFailedLogin() throws IOException {
        output.writeUTF(requestToJSON(CodeRequest.FAILED_LOGIN_INEXIST_USER_CODE, TitleRequest.FAILED_LOGIN_TITLE));
    }

    private void createNewUser() throws IOException {
        output.writeUTF(requestToJSON(CodeRequest.REQUEST_NEW_USER_CODE, TitleRequest.REQUEST_NEW_USER_TITLE));
    }

    private boolean createClient(String userName, String password) {
        User user = model.searchMember(userName + "@" + password);
        if (user == null) return false;
        loginClient = user.isAdmin() ? new AdminController(writerLog, new Connection(socket, writerLog), model) : new MemberController(writerLog, new Connection(socket, writerLog), user);
        return true;
    }

    private void sendPost() {

    }

    public Client getClient() {
        return loginClient;
    }
}
