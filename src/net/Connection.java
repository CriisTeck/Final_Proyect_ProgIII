package net;

import models.Article;
import models.Expense;
import models.Notification;
import models.User;
import persistence.WriterLog;
import utils.CodeRequest;
import utils.JSONUtils;
import utils.MessageRequest;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static utils.JSONUtils.*;

public class Connection {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public Connection(Socket socket, WriterLog writerLog) throws IOException {
        this.socket = socket;
        output = new DataOutputStream(socket.getOutputStream());
        input = new DataInputStream(socket.getInputStream());
    }

    public String[] readRequest() throws IOException {
        return (String[]) JSONUtils.objectFromJSON(input.readUTF(), String[].class);
    }

    public void sendData(Object data) {

    }

    public void closeSocket() throws IOException {
        socket.close();
    }

    public boolean isRunning() throws IOException {
        return socket.getInetAddress().isReachable(100);
    }


    public void sendUserData(String name, long l, long maximumMount, Notification[] counterNotifications) throws IOException {
        output.writeUTF(requestToJSON(CodeRequest.REQUEST, MessageRequest.REQUEST_SEND_INITIAL_DATA));
        output.writeUTF(objectToJSON(new Object[]{name, l, maximumMount, counterNotifications}, Object[].class));
    }

    public void sendDataCalculator(ArrayList<Article> allArticlesAsList) throws IOException {
        output.writeUTF(objectToJSON(allArticlesAsList, allArticlesAsList.getClass()));
    }

    public boolean avalaible() throws IOException {
        return input.available() > 0;
    }

    public Article readArticle() throws IOException {
        return (Article) JSONUtils.objectFromJSON(input.readUTF(), Article.class);
    }

    public void sendSuccesfull() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_OPERATION_SUCCESFULL));
    }

    public void sendSuccesfullCalculator() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_CALCULATOR_SUCCESFULL));
    }

    public void sendSuccesfullDoExpense() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_CALCULATOR_DO_EXPENSE_SUCCESFULL));
    }

    public void sendInt(int number) throws IOException {
        output.writeUTF(JSONUtils.objectToJSON(number, int.class));
    }

    public void sendDataExpense(ArrayList<Expense> allExpenses) throws IOException {
        output.writeUTF(JSONUtils.objectToJSON(allExpenses, ArrayList.class));
    }

    public Expense readExpense() throws IOException {
        return (Expense) JSONUtils.objectFromJSON(input.readUTF(), Expense.class);
    }

    public void sendSuccesfullAddExpense() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_EXPENSE_ADD_SUCCESFULL));
    }

    public void sendAllUsers(ArrayList<User> allUsers) throws IOException {
        output.writeUTF(JSONUtils.objectToJSON(allUsers, ArrayList.class));
    }

    public User readUser() throws IOException {
        return JSONUtils.userFromJSON(input.readUTF(), User.class);
    }

    public void sendSuccesfullAddUser() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_USER_ADD_SUCCESFULL));
    }

    public String readString() throws IOException {
        return (String) JSONUtils.objectFromJSON(input.readUTF(), String.class);
    }

    public void sendUser(User user) throws IOException {
        output.writeUTF(JSONUtils.objectToJSON(user, User.class));
    }

    public void sendSuccesfullEditUser() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_USER_EDIT_SUCCESFULL));
    }

    public String[] readData() throws IOException {
        return (String[]) JSONUtils.objectFromJSON(input.readUTF(), String[].class);
    }

    public void sendArray(String[] data) throws IOException {
        output.writeUTF(JSONUtils.objectToJSON(data, String[].class));
    }

    public void sendSuccesfullMountUser() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_USER_MOUNT_SUCCESFULL));
    }

    public void sendSuccesfullDeleteUser() throws IOException {
        output.writeUTF(JSONUtils.requestToJSON(CodeRequest.ADVICE, MessageRequest.ADVICE_USER_DELETED_SUCCESFULL));
    }
}
