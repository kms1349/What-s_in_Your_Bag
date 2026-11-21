package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class FileManager {
    // 저장 파일 (preparation.json)
    private static final Path FILE_PATH = Paths.get("preparation.json");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // JSON 저장 및 읽기에 사용하는 클래스
    private static class SavedList {
        String listName;
        String[] items;
    }
    
    // 준비물 목록 저장 메소드
    public boolean saveData(ListManager listManager) {
        // 저장할 목록 data 객체 만들고 내용 복사
        SavedList[] data = new SavedList[listManager.getListCount()];

        for (int i = 0; i < data.length; i++) {
            PreparationList list = listManager.getList(i);

            SavedList savedList = new SavedList();
            savedList.listName = list.getListName();

            // 크기가 50인 배열에서 실제 준비물이 있는 부분까지만 복사
            savedList.items = Arrays.copyOf(list.getItems(), list.getItemCount());
            
            data[i] = savedList;
        }

        // data 객체를 json 형식으로 변환하고 파일에 저장
        String json = gson.toJson(data);

        Path temporaryFile = null;
        try {
            Path target = FILE_PATH.toAbsolutePath(); // 같은 디렉터리의 임시 파일에 먼저 저장한 뒤 원본 교체
            temporaryFile = Files.createTempFile(target.getParent(), "preparation-", ".tmp");
            try (BufferedWriter writer = Files.newBufferedWriter(
                    temporaryFile, StandardCharsets.UTF_8)) {
                writer.write(json); // 파일에 쓰기
            }
            // 원자적 교체를 지원하지 않으면 저장 실패로 처리하여 원본 보호
            Files.move(temporaryFile, target,
                    StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);

            // System.out.println("[안내] 데이터가 파일에 저장되었습니다."); 성공 시 안내 문구 출력X
            return true;

        } catch (IOException | SecurityException e) {
            System.out.println("[오류] 파일 저장에 실패했습니다.");
            e.printStackTrace();
            return false;
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException | SecurityException e) {
                    System.out.println("[안내] 임시 저장 파일을 정리하지 못했습니다.\n");
                }
            }
        }
    }

    // 저장된 준비물 목록 불러오는 메소드
    public void loadData(ListManager listManager) {
        SavedList[] data;

        try (BufferedReader reader = Files.newBufferedReader(
                FILE_PATH, StandardCharsets.UTF_8)) {

            data = gson.fromJson(reader, SavedList[].class);

        } catch (java.nio.file.NoSuchFileException e) {
            // 처음 실행해서 저장 파일이 없는 경우
            return;

        } catch (IOException e) {
            System.out.println(
                    "[오류] 파일을 읽는 중 문제가 발생했습니다."
            );
            e.printStackTrace();
            return;

        } catch (JsonParseException e) {
            System.out.println(
                    "[오류] 저장 파일의 JSON 형식이 올바르지 않습니다."
            );
            return;
        }

        // 저장된 데이터가 문제 없는지 먼저 검증한 뒤 불러옴
        ListManager loadedManager = new ListManager();

        try {
            if (data == null) {
                throw new IllegalArgumentException(
                        "저장 데이터가 비어 있습니다."
                );
            }

            for (SavedList savedList : data) {
                if (savedList == null || savedList.items == null) {
                    throw new IllegalArgumentException("목록 또는 준비물 배열이 없습니다.");
                }

                // 목록 개수, 이름 공백, 이름 중복 검사 부분
                PreparationList list = loadedManager.addList(savedList.listName);

                for (String item : savedList.items) {
                    // 준비물 공백, 중복 및 최대 개수 검사는 추가 시 수행
                    list.addItem(item);
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("[오류] 저장 데이터가 올바르지 않습니다: " + e.getMessage());
            return;
        }

        // 검증 성공 시 기존 목록을 불러온 목록으로 교체
        while (listManager.getListCount() > 0) {
            listManager.deleteList(listManager.getListCount() - 1);
        }

        for (int i = 0; i < loadedManager.getListCount(); i++) {
            PreparationList source = loadedManager.getList(i);
            PreparationList target =
                    listManager.addList(source.getListName());

            for (int j = 0; j < source.getItemCount(); j++) {
                target.addItem(source.getItems()[j]);
            }
        }

        System.out.println("저장된 준비물 목록 불러오기 성공.");
    }
}
