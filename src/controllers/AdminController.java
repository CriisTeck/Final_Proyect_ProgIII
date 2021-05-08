package controllers;

import exceptions.EmailAlreadyRegisteredException;
import exceptions.UserAlreadyExistsException;
import models.FamilyGroupManager;
import models.TypeAccount;
import models.User;
import net.Connection;
import persistence.WriterLog;
import utils.CodeRequest;
import utils.JSONUtils;
import utils.MessageRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminController extends Client {
    private final User user;
    private boolean state;

    public AdminController(WriterLog writerLog, Connection connection, FamilyGroupManager model, User user) {
        super(writerLog, connection);
        this.model = model;
        this.user = user;
        state = true;
        this.start();
    }

    @Override
    public void run() {
        try {
            sendInitialData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (state) {
            try {
                if (conn.avalaible()) {
                    String[] code = conn.readRequest();
                    switch (code[0]) {
                        case CodeRequest.REQUEST:
                            processRequest(code[1]);
                            break;
                        case CodeRequest.ADVICE:
                            processAdvice(code[1]);
                            break;

                    }
                }

            } catch (Exception e) {
                try {
                    writerLog.writeLog("["+ LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE).replace(':','_')+"] - " + e.getLocalizedMessage()+"\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    private void sendInitialData() throws IOException {
        conn.sendUserData(user.getName(), user.calculateTotalExpenses(), user.getMaximumMount(), user.getCounterNotifications());
    }

    private void processAdvice(String s) {
    }

    private void processRequest(String s) throws
            Exception {
        switch (s) {
            case MessageRequest.REQUEST_SEND_INITIAL_DATA:
                sendInitialData();
                break;
            case MessageRequest.REQUEST_DATA_CALCULATOR:
                sendDataCalculator();
                break;
            case MessageRequest.REQUEST_ADD_ARTICLE:
                addArticleToCalculator();
                break;
            case MessageRequest.REQUEST_UNDO_ARTICLE:
                undoArticleInCalculator();
                break;
            case MessageRequest.REQUEST_REDO_ARTICLE:
                redoArticleInCalculator();
                break;
            case MessageRequest.REQUEST_DO_EXPENSE:
                doExpenseInCalculator();
                break;
            case MessageRequest.REQUEST_TOTAL_CALCULATOR:
                totalCalculator();
                break;
            case MessageRequest.REQUEST_DATA_EXPENSES:
                sendDataExpenses();
                break;
            case MessageRequest.REQUEST_ADD_EXPENSE:
                addExpense();
                break;
            case MessageRequest.REQUEST_TOTAL_EXPENSES:
                totalExpenses();
                break;
            case MessageRequest.REQUEST_PAY_DEBT:
                payDebt();
                break;
            case MessageRequest.REQUEST_TOTAL_DEBT:
                sendDebt();
                break;
            case MessageRequest.REQUEST_DATA_USERS:
            case MessageRequest.REQUEST_EDIT_MOUNT:
                sendDataUsers();
                break;
            case MessageRequest.REQUEST_ADD_USER:
                addUser();
                break;
            case MessageRequest.REQUEST_DATA_EDIT_USER:
                sendDataEditUser();
                break;
            case MessageRequest.REQUEST_EDIT_USER:
                editUser();
                break;
            case MessageRequest.REQUEST_EDIT_MOUNT_USER:
                editMount();
                break;
            case MessageRequest.REQUEST_EDIT_MOUNT_MOUNT:
                editMountMount();
                break;
            case MessageRequest.REQUEST_DELETE_USER:
                deleteUser();
                break;
            case MessageRequest.REQUEST_SIGN_OUT:
                signOut();
        }
    }

    private void signOut() throws IOException {
        conn.closeSocket();
        state = false;
        model.write();
    }

    private void deleteUser() throws Exception {
        model.removeMember((String) JSONUtils.objectFromJSON(conn.readString(),String.class));
        conn.sendSuccesfullDeleteUser();
        model.write();
    }

    private void editMountMount() throws IOException {
        String[] data = conn.readData();
        User user = model.searchMember(data[0]);
        if (user != null)
            conn.sendArray(new String[]{user.getName(), String.valueOf(user.getMaximumMount())});
        else
            conn.sendArray(new String[]{"",""});
    }

    private void editMount() throws IOException {
        String[] data = conn.readData();
        User us = model.searchMember(data[0]);
        if (us != null) {
            us.setMaximumMount(Long.parseLong(data[1]));
            conn.sendSuccesfullMountUser();
            model.write();
        }
    }

    private void editUser() throws IOException, EmailAlreadyRegisteredException, UserAlreadyExistsException {
        String id = conn.readString();
        User user = conn.readUser();
        User us = model.searchMember(id);
        if (us == null) {
            us = new User(user.getid(), user.getName(), user.getEmail(), TypeAccount.valueOf(user.getRole()));
            model.addUser(us);
        } else {
            us.setId(user.getid());
            us.setName(user.getName());
            us.setEmail(user.getEmail());
            us.setRole(user.getRole());
        }
        model.write();
        conn.sendSuccesfullEditUser();
    }

    private void sendDataEditUser() throws IOException {
        User user = model.searchMember(conn.readString());
        conn.sendUser(user);
    }

    private void addUser() throws IOException, EmailAlreadyRegisteredException, UserAlreadyExistsException {
        model.addUser(conn.readUser());
        conn.sendSuccesfullAddUser();
    }

    private void sendDataUsers() throws IOException {
        conn.sendAllUsers(model.getAllUsers());
    }

    private void payDebt() throws IOException {
        user.payDebt();
        model.write();
        sendDebt();
        sendInitialData();
    }

    private void sendDebt() throws IOException {
        user.calculateDebt();
        conn.sendInt(user.getTotalDebt());
    }

    private void totalExpenses() throws IOException {
        conn.sendInt(user.getTotalExpenses());

    }

    private void addExpense() throws IOException {
        user.addExpense(conn.readExpense());
        model.write();
        conn.sendSuccesfullAddExpense();
    }

    private void sendDataExpenses() throws IOException {
        conn.sendDataExpense(user.getAllExpenses());
    }

    private void totalCalculator() throws IOException {
        conn.sendInt(user.getBuyCalculator().calculateTotal());
        model.write();
    }

    private void doExpenseInCalculator() throws IOException {
        user.addExpense(user.getBuyCalculator().getBuyExpense());
        user.getBuyCalculator().finishCalculator();
        model.write();
        conn.sendSuccesfullDoExpense();
        sendInitialData();
    }

    private void redoArticleInCalculator() throws IOException {
        user.getBuyCalculator().restoreArticle();
        model.write();
        conn.sendSuccesfullCalculator();
    }

    private void undoArticleInCalculator() throws IOException {
        user.getBuyCalculator().deleteLastArticle();
        model.write();
        conn.sendSuccesfullCalculator();
    }

    private void addArticleToCalculator() throws IOException {
        user.getBuyCalculator().addArticle(conn.readArticle());
        model.write();
        conn.sendSuccesfullCalculator();

    }

    private void sendDataCalculator() throws IOException {
        conn.sendDataCalculator(user.getBuyCalculator().getAllArticlesAsList());
    }
}
