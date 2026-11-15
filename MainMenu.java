package project;
import java.util.Scanner;

// MainMenu 메인 메뉴 클래스
public class MainMenu {
    // 사용자 입력을 위한 Scanner
    private Scanner sc = new Scanner(System.in);
    private ListManager listManager = new ListManager();

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
                switch (answer) {
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
                                "메뉴에 존재하지 않는 입력입니다.\n올바른 숫자나 알파벳을 입력해 주세요.\n");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[입력 오류] " + e.getMessage());
            }
        }
    }

    // 준비물 목록 생성 기능
    public void addListMenu() {
        System.out.println("-----준비물 목록 추가-----");

        System.out.println("준비물 목록 이름: ");
        String listName = sc.nextLine().trim();

        try {
            listManager.addList(listName);
            System.out.println("[안내] 준비물 목록이 생성되었습니다.");

        } catch (IllegalArgumentException e) {
            // 목록 생성 중 발생한 예외 처리
            System.out.println("[입력 오류] " + e.getMessage());
        }
    }


    // 준비물 목록 이름 변경 기능
    public void renameListMenu() {

    }

    // 준비물 목록 삭제 기능
    public void deleteListMenu() {
    }
}
