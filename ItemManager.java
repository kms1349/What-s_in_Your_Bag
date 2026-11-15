package project;

public class ItemManager {

    // 준비물 추가 메소드
    public void addItemMenu(PreparationList list, String itemName) {
        // 공백 입력 검사
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("준비물 이름을 입력해 주세요.");
        }
        
        list.addItem(itemName);
    }
}
