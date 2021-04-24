package controllers;

import net.Connection;
import persistence.WriterLog;

public abstract class Client extends Thread{
    private WriterLog writerLog;
    private Connection connection;

    public Client(WriterLog writerLog, Connection connection) {
        this.writerLog = writerLog;
        this.connection = connection;
    }

}
