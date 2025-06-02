package TypingTest;

import Utils.Console;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Words {
    private List<String> wordList;
    private Random random = new Random();

    public Words(String filePath) {
        try {
            wordList = Files.readAllLines(Paths.get(filePath));
            wordList.removeIf(String::isBlank);
        } catch (IOException e) {
            Console.print("Error reading file: " + e.getMessage() , Console.Color.RED);
            wordList = List.of();
        }
    }

    public List<String> getWordList() {
        return wordList;
    }


}
