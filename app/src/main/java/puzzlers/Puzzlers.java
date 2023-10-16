package puzzlers;

import puzzlers.tasks.FindWordsPuzzle;

import java.util.HashSet;
import java.util.Set;

public class Puzzlers {

    public static void main(String[] args) {
        char[][] puzzle = {
                            {'F','Y','Y','H','N','R','D'},
                            {'R','L','J','C','I','N','U'},
                            {'A','A','W','A','A','H','R'},
                            {'N','T','K','L','P','N','E'},
                            {'C','I','L','F','S','A','P'},
                            {'E','O','G','O','T','P','N'},
                            {'H','P','O','L','A','N','D'}
                          };
        Set<String> words = new HashSet<>();
        words.add("FRANCE");
        words.add("POLAND");
        words.add("INDIA");
        words.add("JAPAN");
        words.add("USA");
        words.add("HOLLAND");
        Set<String> wordsFound = FindWordsPuzzle.findWords(puzzle, words);
        wordsFound.forEach(System.out::println);
    }
}