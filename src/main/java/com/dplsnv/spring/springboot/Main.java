package com.dplsnv.spring.springboot;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final int STAGES_AMOUNT = 6;
    public static final String[] hangmanStages = {// 0 ошибок (только основание)
            // 1 ошибка (появилась голова)
            """
             _____
            |/   | 
            |    O 
            |      
            |      
            """,

            // 2 ошибки (добавилось туловище)
            """
             _____
            |/   | 
            |    O 
            |    | 
            |      
            """,
            // 3 ошибки (добавилась рука)
            """
             _____
            |/   | 
            |    O 
            |   /| 
            |      
            """,
            // 4 ошибки (добавилась вторая рука)
            """
             _____
            |/   | 
            |    O 
            |  / | \\
            |      
            """,
            // 5 ошибки (добавилась нога )
            """
             _____
            |/   | 
            |    O 
            |  / | \\
            |   / 
            """,
            // 6 ошибки (добавилось нога)
            """
             _____
            |/   | 
            |    O 
            |  / | \\
            |   / \\ 
            """
    };
    public static String gameWord;
    public static char[] gameWordCharArray;
    public static String currentWord;
    public static int stage;
    public static ArrayList<Character> usedLetters = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int a = 1;
        while(a != 0) {
            System.out.println("0. Выйти из игры");
            System.out.println("1. Играть");
            a = sc.nextInt();
            if(a == 1) {
                startTheGame();
            }
        }
    }
    public static String getRandomWord() throws IOException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("words");
        if(inputStream == null) {
            System.out.println("Файл words.txt не найден в resources!");
            return null;
        }
        String fileContent = new String(inputStream.readAllBytes());
        String[] words = fileContent.split("\n");
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        return words[randomIndex];
    }
    public static void startTheGame() throws IOException {
        gameWord = getRandomWord();
        gameWordCharArray = gameWord.toCharArray();
        currentWord = maskWord(gameWord);
        stage = 0;
        usedLetters = new ArrayList<>();
        while(stage < STAGES_AMOUNT-1){
            System.out.println(currentWord);
            System.out.println("Введите букву");
            String letter = sc.next();
            System.out.println("Введна буква:" + letter);
            isLetterGuessed(letter);
            checkTheWin();
        }
        if(stage != 100) {
            System.out.println("Вы проиграли(");
        }

    }
    public static void isLetterGuessed(String guess) {
        char guessChar = guess.charAt(0);
        char[] gameWordCharArray = gameWord.toCharArray();
        char[] currentWordCharArray = currentWord.toCharArray();
        if(usedLetters.contains(guessChar)) {
            System.out.println("Эта буква уже была введена");
            displayUsedLetters();
        }
        else {
            if(gameWord.contains(guess)){

                for (int i = 0; i < gameWord.length(); i++) {
                    if(gameWordCharArray[i] == guessChar){
                        currentWordCharArray[i] = guessChar;
                    }
                }
                currentWord = new String(currentWordCharArray);
            }
            else {
                stage++;
                System.out.println("Такой буквы нет" +
                        "\nОсталось попыток: " + (STAGES_AMOUNT - 1 -stage));
                System.out.println(hangmanStages[stage]);

            }
            usedLetters.add(guessChar);
            displayUsedLetters();
        }

    }
    public static String maskWord(String word) {
        String result = "*";
        for (int i = 1; i < word.length(); i++) {
            result = result + "*";
        }
        return result;
    }
    public static void displayUsedLetters() {
        if(usedLetters.size() > 0) {
            System.out.print("Использованные буквы: ");
            for (int i = 0; i < usedLetters.size(); i++) {
                System.out.print(usedLetters.get(i) + " ");
            }
            System.out.println();
        }
    }
    public static void checkTheWin() {
        if(!currentWord.contains("*")) {
            System.out.println("Вы победили!");
            stage = 100;
        }
    }

}