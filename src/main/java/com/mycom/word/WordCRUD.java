package com.mycom.word;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD {
    ArrayList<Word> list;
    Scanner s;
    final String fname = "Dictionary.txt";
    /*
        => 난이도(1,2,3) & 새 단어 입력: 1 driveway
        뜻 입력: 차고 진입로
        새 단어가 단어장에 추가되었습니다.
     */

    WordCRUD(Scanner s) {
        list = new ArrayList<>();
        this.s = s;
    }

    @Override
    public Object add() {
        // 사용자에게 입력 받는 메소드
        System.out.print("=> 난이도(1,2,3) & 새 단어 입력: ");
        int level = s.nextInt();
        String word = s.nextLine();

        System.out.print("뜻 입력: ");
        String meaning = s.nextLine();

        return new Word(0, level, word, meaning);
    }

    public void addItem() {
        // 입력 받은 데이터를 리스트에 넣는 메소드
        Word one = (Word)add();
        list.add(one);
        System.out.println("새 단어가 단어장에 추가되었습니다. ");
    }

    @Override
    public int update(Object obj) {
        return 0;
    }

    @Override
    public int delete(Object obj) {
        return 0;
    }

    @Override
    public void selectOne(Object obj) {

    }
    /*

    => 원하는 메뉴는? 1
    ----------------------------------
    1 ***       superintendent  관리자, 감독관
    2 *               electric  전기의, 전기를 생산하는
    3 **             equipment  장비, 용품
    ----------------------------------

     */
    public void listAll() {
        System.out.println("----------------------------------");
        for (int i=0; i<list.size(); i++) {
            System.out.print(i+1 + " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("----------------------------------");
    }

    public ArrayList<Integer> listAll(String keyword) {
        ArrayList<Integer> idList = new ArrayList<>();
        int j = 0;

        System.out.println("----------------------------------");
        for (int i=0; i<list.size(); i++) {
            String word = list.get(i).getWord();
            if (!word.contains(keyword)) continue;
            System.out.print(j+1 + " ");
            System.out.println(list.get(i).toString());
            idList.add(i);
            j++;
        }
        System.out.println("----------------------------------");

        return idList;
    }

    public void updateItem() {
        System.out.print("=> 수정할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idList = this.listAll(keyword);

        System.out.print("=> 수정할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();
        // id를 받고 난 후의 enter가 meaning으로 들어가는 것을 방지하기 위함.

        System.out.print("=> 뜻 입력 : ");
        String meaning = s.nextLine();

        Word word = list.get(idList.get(id-1));
        word.setMeaning(meaning);
        System.out.println("선택하신 단어가 수정되었습니다.");
    }

    public void deleteItem() {
        System.out.print("=> 삭제할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idList = this.listAll(keyword);

        System.out.print("=> 삭제할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();
        // id를 받고 난 후의 enter가 answer로 들어가는 것을 방지하기 위함.

        System.out.print("=> 정말로 삭제하시겠습니까?(Y/n) ");
        String answer = s.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            list.remove((int)idList.get(id-1));
            // Integer 타입의 객체라서 remove 하지 못함. => int 타입 수로 변경해주어야 함.
            System.out.println("선택하신 단어가 삭제되었습니다.");
        }
        else {
            System.out.println("취소되었습니다.");
        }
    }

    public void loadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fname));
            String line;
            int count = 0;
            while(true) {
                line = br.readLine();
                if (line == null) break;
                String data[] = line.split("\\|");
                int level = Integer.parseInt(data[0]);
                String word = data[1];
                String meaning = data[2];
                list.add(new Word(0, level, word, meaning));
                count++;
            }
            br.close();
            System.out.println("==> " + count + "개 로딩 완료.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveFile() {
        try {
            PrintWriter pr = new PrintWriter(new FileWriter("test.txt"));

            pr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
