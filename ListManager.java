package project;

public class ListManager {
    private PreparationList[] lists = new PreparationList[50];
    private int listCount = 0;

    // 준비물 목록 추가 메소드
    public void addList(String listName) {
        if (listCount >= lists.length) {
            throw new IllegalArgumentException("최대 목록 개수(50)을 초과하였습니다.\n더 이상 목록을 추가할 수 없습니다.");
        }

        for (int i = 0; i < listCount; i++) {
            if (lists[i].getListName().equals(listName)) {
                throw new IllegalArgumentException("이미 존재하는 목록 이름입니다.");
            }
        }

        lists[listCount] = new PreparationList(listName);
        listCount++;
    }

    // 준비물 목록 이름 변경 메소드
    public void renameList(int index, String newName) {
        if (index < 0 || index >= listCount) {
            throw new IllegalArgumentException("잘못된 목록 번호입니다.\n");
        }
        lists[index].setListName(newName);
    }

    // 준비물 목록 삭제 메소드
    public void deleteList(int index) {
    }
}
