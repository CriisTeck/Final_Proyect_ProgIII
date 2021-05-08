package models;

import structures.stack.Stack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Calculator {
    private Stack<Article> articleList;
    private final Stack<Article> deletedArticles;

    public Calculator() {
        articleList = new Stack<>(Article::compare);
        deletedArticles = new Stack<>(Article::compare);
    }

    public void addArticle(Article article) {
        articleList.push(article);
    }

    public int calculateTotal() {
        Iterator<Article> iterator = articleList.iterator();
        int counterTotal = 0;
        while (iterator.hasNext()) {
            Article art = iterator.next();
            counterTotal += art.getCost()*art.getQuantity();
        }
        return counterTotal;
    }

    public void finishCalculator() {
        articleList = new Stack<>(Article::compare);
    }

    public Expense getBuyExpense() {
        return new Expense(calculateTotal(), LocalDateTime.now(), "");
    }

    public ArrayList<Article> getAllArticlesAsList() {
        Iterator<Article> iterator = articleList.iterator();
        ArrayList<Article> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    public void deleteLastArticle() {
        Article article = articleList.pop();
        if (article != null)
            deletedArticles.push(article);
    }

    public void restoreArticle() {
        Article article = deletedArticles.pop();
        if (article != null)
            articleList.push(article);
    }
}
