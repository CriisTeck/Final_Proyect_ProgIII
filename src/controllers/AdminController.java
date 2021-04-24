package controllers;

import models.FamilyGroupManager;
import net.Connection;
import persistence.WriterLog;

public class AdminController extends Client{
    private FamilyGroupManager model;

    public AdminController(WriterLog writerLog, Connection connection, FamilyGroupManager model) {
        super(writerLog, connection);
        this.model = model;
        this.start();
    }

    @Override
    public void run() {
        super.run();
    }
}
