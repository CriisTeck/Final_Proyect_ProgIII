package controllers;

import models.FamilyGroupManager;
import models.User;
import net.Connection;
import persistence.WriterLog;
import utils.CodeRequest;
import utils.MessageRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberController extends Client {
    private final User user;
    private boolean state;

    public MemberController(WriterLog writerLog, Connection connection, User user, FamilyGroupManager model) {
        super(writerLog, connection, model);
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
            case MessageRequest.REQUEST_SIGN_OUT:
                signOut();
                break;
        }
    }

    private void signOut() throws IOException {
        conn.closeSocket();
        state = false;
        model.write();
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
