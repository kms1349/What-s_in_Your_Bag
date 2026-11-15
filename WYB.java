package project;
import java.util.Scanner;
// What's in Your Bag 상황별 준비물 관리 프로그램

public class WYB {
    // 사용자 입력을 위한 Scanner
    private Scanner sc = new Scanner(System.in);

    // MainMenu 메인 메뉴 클래스
    public class MainMenu {
        // 메인 메뉴 기능 목록
        private String[] mainMenu = { // 타 클래스에서 수정 불가하게 private로 함
            "[메인 메뉴]",
            "a. 새로운 준비물 목록 추가",
            "b. 준비물 목록 이름 변경",
            "c. 기존 준비물 목록 삭제",
            "0. 프로그램 종료"
        };

        // 메인 메뉴 출력 메소드
        public void showMainMenu() {
            System.out.println("========================");
            System.out.println("What's in Your Bag?");
            System.out.println("상황별 준비물 관리 프로그램");
            System.out.println("------------------------");
            
            // Todo 준비물 목록 출력

            for (String item : mainMenu) {
                System.out.println(item);
            }

            System.out.println("========================");
            System.out.print("목록 번호 또는 메뉴 알파벳을 입력: ");
        }

        // 메인 메뉴 실행(프로그램 시작 시 실행)
        public void runMenu() {
            while (true) {
                showMainMenu();
                String answer = sc.nextLine().trim();
                try {
                    switch(answer) {
                        case "a":
                            System.out.println("준비물 목록을 생성\n");
                            // 목록 생성 기능 실행
                            addListMenu();
                            break;
                        case "b":
                            System.out.println("목록의 이름을 변경\n");
                            // 목록 이름 변경 기능 실행
                            renameListMenu();
                            break;
                        case "c":
                            System.out.println("준비물 목록을 삭제\n");
                            // 목록 삭제 기능 실행
                            deleteListMenu();
                            break;
                        case "0":
                            System.out.println("프로그램을 종료합니다\n");
                            return;
                        
                        default:
                            throw new IllegalArgumentException(
                                "메뉴에 존재하지 않는 입력입니다.\n올바른 숫자나 알파벳을 입력해 주세요.\n"
                            );
                    }
                } 
                catch (IllegalArgumentException e) {
                    System.out.println("[입력 오류] " + e.getMessage());
                }
            }
        }

        // 준비물 목록 생성 기능
        public void addListMenu() {
            System.out.println("-----준비물 목록 추가-----");

            System.out.println("준비물 목록 이름: ");
            String listName = sc.nextLine().trim();

            try{
                listManager.addList(listName);
                 System.out.println("[안내] 준비물 목록이 생성되었습니다.");
                
                } catch (IllegalArgumentException e) {
                    // 목록 생성 중 발생한 예외 처리
                    System.out.println("[입력 오류] " + e.getMessage());
                }
            }
        }

        // 준비물 목록 이름 변경 기능
        public void renameListMenu() {

        }

        // 준비물 목록 삭제 기능
        public void deleteListMenu() {}
    }

    // PreparationList 준비물 목록 리스트 클래스
    public class PreparationList {
        private String listName;
        private String[] items = new String[50];
        private int itemCount = 0;

        
        public PreparationList(String listName) { // 생성자. 준비물 목록을 생성할 때 목록 이름 받음
            this.listName = listName;
        }

        public String getListName() { // 현재 준비물 목록 이름 반환
            return listName;
        }

        public void setListName(String newName) { // 준비물 목록 이름 변경
            this.listName = newName;
        }

        public String[] getItems() { // 준비물 배열 반환
            return items;
        }
    }

    // ListManager 준비물 목록 관리 클래스
    public class ListManager {
        private PreparationList[] lists = new PreparationList[50];
        private int listCount = 0;

        // 준비물 목록 추가 메소드
        public void addList(String listName) {
            // 준비물 목록 배열 공간 확인
            if (listCount >= lists.length) {
                throw new IllegalArgumentException("최대 목록 개수(50)을 초과하였습니다.\n더 이상 목록을 추가할 수 없습니다.")
            }
            // 준비물 이름 중복 검사
            for (int i = 0; i < listCount; i++) {
                if (lists[i].getListName().equals(listName)) {
                    throw new IllegalArgumentException("이미 존재하는 목록 이름입니다.");
                }
            }
            // 새로운 PreparationList 객체 생성
            PreparationList newList = new PreparationList(listName);
            lists[listCount] = newList;
            listCount++;
        }

        // 준비물 목록 이름 변경 메소드
        public void renameList(int index, String newName) {
            // 입력값 유효성 체크
            if (index < 0 || index >= listCount) {
                throw new IllegalArgumentException("잘못된 목록 번호입니다.\n");
            }
            lists[index].setListName(newName);
        }

        // 준비물 목록 삭제 메소드
        public void deleteList(int index) {}
    }

    // ItemManager 준비물 관리 클래스
    public class ItemManager {}
    public static void main(String[] args) {
        WYB app = new WYB();
        MainMenu mainMenu = app.new MainMenu();

        mainMenu.runMenu();
    }
}
