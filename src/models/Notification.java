package models;

public class Notification {
    private int id;
    private String title;
    private String content;
    private String actionCoe;
    private Object[] dataParameters;

    public Notification(Object[] dataParameters, String actionCoe, String content, String title) {
        this.dataParameters = dataParameters;
        this.actionCoe = actionCoe;
        this.content = content;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActionCoe() {
        return actionCoe;
    }

    public void setActionCoe(String actionCoe) {
        this.actionCoe = actionCoe;
    }

    public Object[] getDataParameters() {
        return dataParameters;
    }

    public void setDataParameters(Object[] dataParameters) {
        this.dataParameters = dataParameters;
    }

    public static int compare(Notification o, Notification o1){
        return Integer.compare(o.getId(),o1.getId());
    }
}
