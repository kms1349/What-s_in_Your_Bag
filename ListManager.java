package project;

public class ListManager {
    private PreparationList[] lists = new PreparationList[50];
    private int listCount = 0;

    // listCount 얻는 메소드
    public int getListCount() {
        return listCount;
    }
    
    // 특정 인덱스의 list 얻는 메소드
    public PreparationList getList(int index) {
        if (index < 0 || index >= listCount) {
            throw new IllegalArgumentException("존재하지 않는 목록 번호입니다.");
        }
        return lists[index];
    }

    // 준비물 목록 표시 메소드
    public void showList() {
         System.out.println("[준비물 목록]");

         // 생성된 목록이 없는 경우
        if (listCount == 0) {
            System.out.println("생성된 준비물 목록이 없습니다.");
        }
        // 생성된 목록이 있는 경우
        else {
            for (int i = 0; i < listCount; i++) {
                // 해당 번호의 준비물 목록을 list로 가져옴
                PreparationList list = lists[i];
                // 목록 번호와 이름 출력
                System.out.println((i + 1) + ". " + list.getListName());
            }
        }

        System.out.println("------------------------------");
    }

    // 준비물 목록 추가 메소드
    public PreparationList addList(String listName) {
        if (listCount >= lists.length) {
            throw new IllegalArgumentException("최대 목록 개수(50)을 초과하였습니다.\n더 이상 목록을 추가할 수 없습니다.");
        }

        listName = validateListName(listName);

        PreparationList newList = new PreparationList(listName);
        lists[listCount] = newList;
        listCount++;

        return newList;
    }

    // 목록 생성과 이름 변경 시 공백 및 중복 검사 메소드
    private String validateListName(String listName) {
        if (listName == null || listName.trim().isEmpty()) {
            throw new IllegalArgumentException("목록 이름을 입력해 주세요.");
        }

        listName = listName.trim();
        for (int i = 0; i < listCount; i++) {
            if (lists[i].getListName().equals(listName)) {
                throw new IllegalArgumentException("이미 존재하는 목록 이름입니다.");
            }
        }

        return listName;
    }

    // 준비물 목록 이름 변경 메소드
    public void renameList(int index, String newName) {
        if (index < 0 || index >= listCount) {
            throw new IllegalArgumentException("잘못된 목록 번호입니다.\n");
        }
        newName = validateListName(newName);
        lists[index].setListName(newName);
    }

    // 준비물 목록 삭제 메소드
    public void deleteList(int index) {
        if (index < 0 || index >= listCount) {
            throw new IllegalArgumentException("잘못된 목록 번호입니다.\n");
        }
    
        // 삭제할 위치 뒤의 목록들을 한 칸씩 앞으로 이동
        for (int i = index; i < listCount - 1; i++) {
            lists[i] = lists[i + 1];
        }

        // 마지막 위치 초기화
        lists[listCount - 1] = null;

        // 전체 목록 개수 감소
        listCount--;
    }
}
