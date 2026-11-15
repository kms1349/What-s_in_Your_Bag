package project;

public class PreparationList {
    private String listName;
    private String[] items = new String[50];
    private int itemCount = 0;

    public PreparationList(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String newName) {
        this.listName = newName;
    }

    public String[] getItems() {
        return items;
    }
}
