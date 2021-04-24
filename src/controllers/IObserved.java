package controllers;

public interface IObserved {

    void notifyUser();
    void notifyAdmin();
    void notifyAllClients();
}
