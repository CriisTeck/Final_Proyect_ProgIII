package controllers;

import net.Connection;
import persistence.WriterLog;

public abstract class Client extends Thread{
    protected WriterLog writerLog;
    protected Connection connection;

    public Client(WriterLog writerLog, Connection connection) {
        this.writerLog = writerLog;
        this.connection = connection;
    }

}
