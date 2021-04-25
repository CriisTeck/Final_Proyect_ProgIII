package controllers;

import models.FamilyGroupManager;
import persistence.WriterLog;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class ServerController implements Runnable, IObserved {
    private ServerSocket serverSocket;
    private static final int PORT = 25850;
    private FamilyGroupManager model;
    private ArrayList<Client> clientList;
    private ArrayList<LoginAssigment_Controller> activeLogin;
    private WriterLog writerLog;
    private Thread thisThread;

    public ServerController() throws IOException {
        serverSocket = new ServerSocket(PORT);
        model = new FamilyGroupManager();
        clientList = new ArrayList<>();
        activeLogin = new ArrayList<>();
        writerLog = new WriterLog();
        System.out.println("Server iniciado");
        init();
    }

    public void init() {
        thisThread = new Thread(this);
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
        System.out.println("Hilo de login activo");
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
                System.out.println(activeLogin);
            } catch (InterruptedException | IndexOutOfBoundsException e) {
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
