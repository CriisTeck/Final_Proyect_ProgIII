package controllers;

import models.FamilyGroupManager;
import persistence.WriterLog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerController implements Runnable, IObserved {
    private final ServerSocket serverSocket;
    private static final int PORT = 25850;
    private final FamilyGroupManager model;
    private final ArrayList<Client> clientList;
    private final ArrayList<LoginAssigment_Controller> activeLogin;
    private final WriterLog writerLog;

    public ServerController() throws IOException {
        serverSocket = new ServerSocket(PORT);
        model = new FamilyGroupManager();
        clientList = new ArrayList<>();
        activeLogin = new ArrayList<>();
        writerLog = new WriterLog();
        init();
    }

    public void init() {
        Thread thisThread = new Thread(this);
        thisThread.start();
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                activeLogin.add(new LoginAssigment_Controller(socket, model, writerLog));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int bound = activeLogin.size();
                Thread.sleep(1000);

                for (int i = 0; i < bound; i++) {
                    if (!activeLogin.get(i).isAlive()) {
                        clientList.add(activeLogin.get(i).getClient());
                        activeLogin.remove(i);
                    }
                }
            } catch (InterruptedException | IndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void notifyUser() {

    }

    @Override
    public void notifyAdmin() {

    }

    @Override
    public void notifyAllClients() {

    }
}
