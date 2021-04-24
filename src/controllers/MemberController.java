package controllers;

import models.User;
import net.Connection;
import persistence.WriterLog;

public class MemberController extends Client {
    private User model;

    public MemberController(WriterLog writerLog, Connection connection, User model) {
        super(writerLog, connection);
        this.model = model;
        this.start();
    }

    @Override
    public void run() {
        super.run();
    }
}
