package models;

import java.time.LocalDateTime;

public class Expense {
    private int mount;
    private LocalDateTime date;
    private TypeExpense typeExpense;
    private String description;

    public Expense(int mount, LocalDateTime date, TypeExpense typeExpense, String description) {
        this.mount = mount;
        this.date = date;
        this.typeExpense = typeExpense;
        this.description = description;
    }

    public int getMount() {
        return mount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TypeExpense getTypeExpense() {
        return typeExpense;
    }

    public String getDescription() {
        return description;
    }
}
