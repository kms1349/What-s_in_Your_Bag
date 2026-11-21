package project;

public class ItemManager {

    // 준비물 추가 메소드
    public void addItemMenu(PreparationList list, String itemName) {
        // 공백 입력 검사를 포함한 준비물 검사는 PreparationList에서 수행
        list.addItem(itemName);
    }
}
