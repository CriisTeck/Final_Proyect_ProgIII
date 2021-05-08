package models;

import structures.stack.Stack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private String id;
    private String name;
    private final Stack<Notification> listNotifications;
    private final Calculator buyCalculator;
    private final ArrayList<Expense> expenseList;
    private long debt;
    private long maxAmountToExpense;
    private TypeAccount role;
    private String emailUser;
    private int totalExpense;

    public User(String id, String name, String emailUser, TypeAccount role) {
        this.id = id;
        this.name = name;
        this.listNotifications = new Stack<>(Notification::compare);
        this.buyCalculator = new Calculator();
        this.debt = 0;
        this.maxAmountToExpense = 0;
        this.role = role;
        this.emailUser = emailUser;
        expenseList = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenseList.add(expense);
    }

    public Expense createExpense(int id, LocalDateTime date, String description) {
        return new Expense(id, date, description);
    }

    public void payDebt() {
        maxAmountToExpense = calculateTotalExpenses();
    }

    public boolean isAdmin() {
        return role.name().equals("ADMIN");
    }

    public long calculateTotalExpenses() {
        totalExpense = expenseList.stream().map(Expense::getMount).reduce(Integer::sum).orElse(0);
        return totalExpense;
    }

    public static int compare(User t1, User t) {
        return t1.id.compareTo(t.id);
    }

    public String getid() {
        return id;
    }

    public Calculator getBuyCalculator() {
        return buyCalculator;
    }

    public String getEmail() {
        return emailUser;
    }

    public String getName() {
        return name;
    }

    public long getMaximumMount() {
        return maxAmountToExpense;
    }

    public Notification[] getCounterNotifications() {
        Iterator<Notification> iterator = listNotifications.iterator();
        List<Notification> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list.toArray(new Notification[0]);
    }

    public ArrayList<Expense> getAllExpenses() {
        return expenseList;
    }

    public int getTotalExpenses() {
        return expenseList.stream().mapToInt(Expense::getMount).sum();
    }

    public int getTotalDebt() {
        return (int) debt;
    }

    public void calculateDebt() {
        debt = maxAmountToExpense < calculateTotalExpenses()
                ? Math.abs(maxAmountToExpense - calculateTotalExpenses())
                : 0;
    }


    public long getTotalExpense() {
        return totalExpense;
    }

    public String getRole() {
        return role.name();
    }

    public void setId(String getid) {
        this.id = getid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.emailUser = email;
    }

    public void setRole(String role) {
        this.role = TypeAccount.valueOf(role);
    }

    public void setMaximumMount(long datum) {
        this.maxAmountToExpense = datum;
    }
}
