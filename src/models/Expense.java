package models;

import java.time.LocalDateTime;

public class Expense {
    private final int mount;
    private final LocalDateTime date;

    public Expense(int mount, LocalDateTime date, String description) {
        this.mount = mount;
        this.date = date;
    }

    public int getMount() {
        return mount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
