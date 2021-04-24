package controllers;

import models.FamilyGroupManager;
import persistence.WriterLog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class ServerController implements Runnable, IObserved{
    private ServerSocket serverSocket;
    private static final int PORT = 25850;
    private FamilyGroupManager model;
    private ArrayList<Client> clientList;
    private ArrayList<LoginAssigment_Controller> activeLogin;
    private WriterLog writerLog;

    public ServerController() throws IOException {
        serverSocket = new ServerSocket(PORT);
        model = new FamilyGroupManager();
        clientList = new ArrayList<>();
        activeLogin = new ArrayList<>();
        writerLog = new WriterLog();
        init();
    }

    public void init(){
        Thread serverThis = new Thread(this);
        serverThis.start();
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                activeLogin.add(new LoginAssigment_Controller(socket,model, writerLog ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            IntStream.range(0, activeLogin.size()).filter(i -> !activeLogin.get(i).isAlive()).forEach(i -> {
                clientList.add(activeLogin.get(i).getClient());
                activeLogin.remove(i);
            });
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
