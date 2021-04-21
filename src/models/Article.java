package models;

public class Article {
    private int idArticle;
    private String name;
    private int cost;

    public Article(int idArticle, String name, int cost) {
        this.cost = cost;
        this.name = name;
        this.idArticle = idArticle;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public static int compare(Article o, Article o1) {
        return Integer.compare(o.getIdArticle(),o1.getIdArticle());
    }
}
