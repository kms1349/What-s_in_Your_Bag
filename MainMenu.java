package project;
import java.util.Scanner;

// MainMenu 메인 메뉴 클래스
public class MainMenu {
    // 사용자 입력을 위한 Scanner
    private Scanner sc = new Scanner(System.in);
    private ListManager listManager = new ListManager();
    private ItemManager itemManager = new ItemManager();
    private FileManager fileManager = new FileManager();

    // 메인 메뉴 기능 목록
    private String[] mainMenu = { // 타 클래스에서 수정 불가하게 private로 함
            "[메인 메뉴]",
            "a. 새로운 준비물 목록 추가",
            "b. 준비물 목록 이름 변경",
            "c. 기존 준비물 목록 삭제",
            "0. 프로그램 종료"
    };

    // Item 메뉴 기능 목록
    private String[] itemMenu = {
        "[준비물 메뉴]",
        "0. 메인으로 돌아가기"
    };

    // 메뉴 입력의 공백 검사 메소드
    private void validateMenuInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("메뉴를 입력해 주세요.");
        }
    }

    // 목록 선택, 이름 변경, 삭제 시 목록 번호 형식 및 범위 검사 메소드
    private int parseListIndex(String input) {
        validateMenuInput(input);
        int number;
        try {
            number = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("목록 번호를 숫자로 입력해 주세요.");
        }
        if (number < 1 || number > listManager.getListCount()) {
            throw new IllegalArgumentException("존재하지 않는 목록 번호입니다.");
        }
        return number - 1;
    }

    // 메인 메뉴 출력 메소드
    public void showMainMenu() {
        System.out.println("==============================");
        System.out.println("What's in Your Bag?");
        System.out.println("상황별 준비물 관리 프로그램");
        System.out.println("------------------------------");

        // 준비물 목록 출력
        listManager.showList();

        // 메인 메뉴 기능 목록 표시
        for (String item : mainMenu) {
            System.out.println(item);
        }

        System.out.println("==============================");
        System.out.print("목록 번호 또는 메뉴 알파벳을 입력: ");
    }

    // 메인 메뉴 실행(프로그램 시작 시 실행)
    public void runMainMenu() {
        // 프로그램 시작 시 파일에 저장된 데이터 불러오기
        fileManager.loadData(listManager);
        
        while (true) {
            showMainMenu();
            String answer = sc.nextLine().trim();
            try {
                validateMenuInput(answer);
                switch (answer) {
                    case "a":
                        System.out.println("[안내] 준비물 목록을 추가합니다.\n");
                        addListMenu(); // 목록 추가 기능 실행
                        break;
                    case "b":
                        System.out.println("[안내] 목록의 이름을 변경합니다.\n");
                        renameListMenu(); // 목록 이름 변경 기능 실행
                        break;
                    case "c":
                        System.out.println("[안내] 준비물 목록을 삭제합니다.\n");
                        deleteListMenu(); // 목록 삭제 기능 실행
                        break;
                    case "0":
                        fileManager.saveData(listManager); // 파일에 내용 저장하고 종료하기
                        System.out.println("\n[안내] 프로그램을 종료합니다");
                        return;

                    default:
                        // 입력값이 위에 안 걸리는 경우 일단 목록 번호로 처리
                        int index = parseListIndex(answer); // 입력 검사
                        runItemMenu(index); // 해당 번호의 준비물 목록과 아이템 메뉴 출력 메소드
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[입력 오류] " + e.getMessage());
            }
        }
    }

    // 준비물 목록 생성 기능
    public void addListMenu() {
        System.out.println("----------준비물 목록 추가----------");

        System.out.print("준비물 목록 이름: ");
        String listName = sc.nextLine().trim();

        PreparationList list;

        try {
            // 실제 준비물 목록 객체 생성 수행
            list = listManager.addList(listName); // 데이터 저장
            System.out.println("[안내] 목록이 생성되었습니다.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("[입력 오류] " + e.getMessage());
            return;
        }
        // 목록에 준비물 추가 수행
        while (true) {
            if (list.getItemCount() >= list.getItems().length) { // 준비물 배열 여유 공간 확인
                System.out.println("[안내] 최대 준비물 개수(50)에 도달했습니다.");
                break;
            }

            System.out.print("준비물을 입력 (종료: 0): ");
            String itemName = sc.nextLine().trim();

            if (itemName.equals("0")) break; // 준비물 입력 종료

            if (itemName.isEmpty()) { // 공백 입력 방지
                System.out.println("[안내] 준비물 이름을 입력해 주세요.");
                continue;
            }

            try {
                itemManager.addItemMenu(list, itemName);
            } catch (IllegalArgumentException e) {
                System.out.println("[입력 오류] " + e.getMessage());
            }
        }
        fileManager.saveData(listManager); // 파일에 저장
        System.out.println("[안내] 준비물 입력이 완료되었습니다.");
    }


    // 준비물 목록 이름 변경 기능
    public void renameListMenu() {
        System.out.println("----------준비물 목록 이름 변경----------");

        System.out.print("이름을 변경할 목록 번호: ");
        String input = sc.nextLine().trim();
        int index;
        try {
            index = parseListIndex(input);
        } catch (IllegalArgumentException e) {
            System.out.println("[입력 오류] " + e.getMessage());
            return;
        }
        System.out.print("새로운 목록 이름: ");
        String newName = sc.nextLine().trim();

        try {
            listManager.renameList(index, newName); // 실제 목록 이름 변경 수행
            fileManager.saveData(listManager); // 파일에 저장
            System.out.println("[안내] " + (index + 1) + "번 목록의 이름이 " + newName +"로 변경되었습니다.");

        } catch (IllegalArgumentException e) {
            // 목록 생성 중 발생한 예외 처리
            System.out.println("[입력 오류] " + e.getMessage());
        }
    }

    // 준비물 목록 삭제 기능
    public void deleteListMenu() {
        System.out.println("----------준비물 목록 삭제----------");

        System.out.print("삭제할 목록 번호: ");
        String input = sc.nextLine().trim();
        int index;
        try {
            index = parseListIndex(input);
        } catch (IllegalArgumentException e) {
            System.out.println("[입력 오류] " + e.getMessage());
            return;
        }

        try {
            listManager.deleteList(index); // 실제 목록 삭제 수행
            fileManager.saveData(listManager); // 파일에 저장
            System.out.println("[안내] 준비물 목록이 삭제되었습니다.");

        } catch (IllegalArgumentException e) {
            // 목록 삭제 중 발생한 예외 처리
            System.out.println("[입력 오류] " + e.getMessage());
        }
    }

    
    // 해당 번호의 준비물 목록과 준비물 메뉴 출력 메소드
    public void showItemMenu(int index) {
        System.out.println("==============================");

        // 해당 인덱스의 준비물 목록 이름과 준비물들 출력
        PreparationList list = listManager.getList(index);
        System.out.println("[" + list.getListName() + "]");
        if (list.getItemCount() == 0) {
            System.out.println("등록된 준비물이 없습니다.");
        } else {
            for (int i = 0; i < list.getItemCount(); i++) {
                System.out.println((i + 1) + ". " + list.getItems()[i]);
            }
        }

        System.out.println("------------------------------");
        // 준비물 메뉴 기능 목록 표시
        for (String item : itemMenu) {
            System.out.println(item);
        }

        System.out.println("==============================");
        System.out.print("메뉴 알파벳을 입력: ");
    }
    
    // 준비물 메뉴 실행
    public void runItemMenu(int index) {
        while (true) {
            showItemMenu(index); // 선택 준비물 목록과 준비물 메뉴 출력

            String answer = sc.nextLine().trim();

            try {
                validateMenuInput(answer);
                switch (answer) {
                    // 추후 준비물 관련 메뉴 추가(추가, 수정, 삭제, 체크리스트)
                    case "0":
                        System.out.println("[안내] 메인 메뉴로 돌아갑니다.\n");
                        return;
                    default:
                        throw new IllegalArgumentException(
                            "메뉴에 존재하지 않는 입력입니다.\n올바른 알파벳을 입력해 주세요.\n"
                        );
                    }
            } catch (IllegalArgumentException e) {
                System.out.println("[입력오류] " + e.getMessage());
            }
        }
    }
}
