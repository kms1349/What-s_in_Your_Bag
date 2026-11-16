package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    // 저장 파일
    private final String FILE_NAME = "preparation.txt";

    // 준비물 목록 저장 메소드
    public void saveData(ListManager listManager) {

        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(FILE_NAME), StandardCharsets.UTF_8)) {

            // 전체 목록 개수 저장
            writer.write(String.valueOf(listManager.getListCount()));
            writer.newLine();

            // 각 목록의 이름과 준비물 저장
            for (int i = 0; i < listManager.getListCount(); i++) {
                PreparationList list = listManager.getList(i);

                writer.write(list.getListName());
                writer.newLine();

                writer.write(String.valueOf(list.getItemCount()));
                writer.newLine();

                for (int j = 0; j < list.getItemCount(); j++) {
                    writer.write(list.getItems()[j]);
                    writer.newLine();
                }
            }

            System.out.println("[안내] 변경된 준비물 목록이 저장되었습니다.");

        } catch (IOException e) {
            System.out.println("[오류] 파일 저장에 실패했습니다.");
            e.printStackTrace();
        }
    }

    // 저장된 준비물 목록 불러오는 메소드
    public void loadData(ListManager listManager) {

        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(FILE_NAME), StandardCharsets.UTF_8)) {

            int savedListCount = Integer.parseInt(reader.readLine());

            // 전체 목록 개수 읽기
            for (int i = 0; i < savedListCount; i++) {

                String listName = reader.readLine(); // 목록 이름 읽기
                int savedItemCount = Integer.parseInt(reader.readLine()); // 해당 목록의 준비물 개수 읽기

                PreparationList list = listManager.addList(listName); // 목록 객체 생성

                // 준비물 읽기 및 추가
                for (int j = 0; j < savedItemCount; j++) { 
                    String itemName = reader.readLine();
                    list.addItem(itemName);
                }
            }

            System.out.println("[안내] 준비물 목록을 불러왔습니다.");

        } catch (IOException e) {
            System.out.println("[오류] 파일을 읽는 중 문제가 발생했습니다.");
            e.printStackTrace();

        } catch (NumberFormatException e) {
            System.out.println("[오류] 저장 파일의 형식이 올바르지 않습니다.");
        }
    }
}