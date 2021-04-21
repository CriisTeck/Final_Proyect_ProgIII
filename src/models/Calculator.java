package models;

import structures.stack.Stack;

import java.time.LocalDateTime;
import java.util.Iterator;

public class Calculator {
    private Stack<Article> articleList;

    public Calculator() {
        articleList = new Stack<>(Article::compare);
    }

    public void addArticle(Article article){
        articleList.push(article);
    }

    public Article createArticle(int idArticle, String name, int cost){
        return new Article(idArticle,name,cost);
    }

    public Article removeLastArticle(){
        return articleList.pop();
    }

    public int calculateTotal(){
        Iterator<Article> iterator = articleList.iterator();
        int counterTotal = 0;
        while (iterator.hasNext())
            counterTotal += iterator.next().getCost();
        return counterTotal;
    }

    public void finishCalculator(){
        articleList = new Stack<>(Article::compare);
    }

    public Expense getBuyExpense(String description){
        return new Expense(calculateTotal(), LocalDateTime.now(),TypeExpense.COMPRA,"");
    }
}
