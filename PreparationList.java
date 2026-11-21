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

    public int getItemCount() {
        return itemCount;
    }

    // 준비물 배열까지 독립적으로 복사하는 메소드
    public PreparationList copy() {
        PreparationList copy = new PreparationList(listName);
        System.arraycopy(items, 0, copy.items, 0, itemCount);
        copy.itemCount = itemCount;
        return copy;
    }

    public void addItem(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("준비물 이름을 입력해 주세요.");
        }
        itemName = itemName.trim();
        for (int i = 0; i < itemCount; i++) {
            if (items[i].equals(itemName)) {
                throw new IllegalArgumentException("이미 존재하는 준비물 이름입니다.");
            }
        }
        if (itemCount >= items.length) {
            throw new IllegalArgumentException("최대 준비물 개수(50)를 초과할 수 없습니다.");
        }
        items[itemCount] = itemName;
        itemCount++;
    }
}
