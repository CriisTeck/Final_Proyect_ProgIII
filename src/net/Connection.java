package net;

import persistence.WriterLog;

import java.io.*;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private WriterLog writerLog;

    public Connection(Socket socket, WriterLog writerLog) {
        this.socket = socket;
        this.writerLog = writerLog;
    }

    public String readData(){
        return "";
    }

    public void sendData(Object data){

    }

    public void closeSocket() throws IOException {
        socket.close();
    }

    public boolean isRunning() throws IOException {
        return socket.getInetAddress().isReachable(100);
    }


}
