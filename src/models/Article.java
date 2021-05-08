package models;

public class Article {
    private final int quantity;
    private final int cost;


    private final String name;

    public Article(int quantity, String name, int cost) {
        this.cost = cost;
        this.quantity = quantity;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }

    public static int compare(Article o, Article o1) {
        return Integer.compare(o.getQuantity(),o1.getQuantity());
    }

}
