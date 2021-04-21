package models;

import structures.stack.Stack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private Stack<Notification> listNotifications;
    private Calculator buyCalculator;
    private List<Expense> expenseList;
    private long debt;
    private long maxAmountToExpense;
    private TypeAccount role;

    public User(int id, String name, TypeAccount role) {
        this.id = id;
        this.name = name;
        this.listNotifications = new Stack<>(Notification::compare);
        this.buyCalculator = new Calculator();
        this.debt = 0;
        this.maxAmountToExpense = 0;
        this.role = role;
        expenseList = new ArrayList<>();
    }

    public void acceptNotification(){

    }

    public void addExpense(Expense expense){
        expenseList.add(expense);
    }

    public Expense createExpense(int id, LocalDateTime date, TypeExpense typeExpense,String description){
        return new Expense(id, date, typeExpense,description);
    }

    public void addDebt(Expense expense){
        long total = calculateTotalExpenses() + expense.getMount();
        debt = Math.abs(maxAmountToExpense - total);
    }

    public Calculator getBuyCalculator(){
        return buyCalculator;
    }

    public void payDebt(){
        debt = 0;
    }

    public boolean isAdmin(){
        return role.name().equals("ADMIN");
    }

    public long calculateTotalExpenses(){
        return expenseList.stream().map(Expense::getMount).reduce(Integer::sum).get();
    }


    public static int compare(User t1, User t) {
        return Integer.compare(t1.id,t.id);
    }
}