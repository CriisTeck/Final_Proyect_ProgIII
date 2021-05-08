package controllers;

import models.FamilyGroupManager;
import net.Connection;
import persistence.WriterLog;

public abstract class Client extends Thread{
    protected WriterLog writerLog;
    protected Connection conn;
    protected FamilyGroupManager model;

    public Client(WriterLog writerLog, Connection connection) {
        this.writerLog = writerLog;
        this.conn = connection;
    }


    public Client(WriterLog writerLog, Connection connection, FamilyGroupManager model) {
        this.writerLog = writerLog;
        this.conn = connection;
        this.model = model;
    }
}
