package puzzlers;

import puzzlers.tasks.FindWordsPuzzle;
import puzzlers.tasks.IsDivisibleByNumbers;
import puzzlers.tasks.NextElemSameDigits;
import puzzlers.tasks.SortByAmount;

import java.util.Arrays;
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

        NextElemSameDigits.findNextGreater(new int[]{6});
        NextElemSameDigits.findNextGreater(new int[]{1, 2, 3, 4});
        NextElemSameDigits.findNextGreater(new int[]{1, 2, 3, 2});
        NextElemSameDigits.findNextGreater(new int[]{6, 2, 1, 8, 7, 3});

        System.out.println(IsDivisibleByNumbers.isDivisibleByNumbers(412));
        System.out.println(IsDivisibleByNumbers.isDivisibleByNumbers(143));

        System.out.println(SortByAmount.sortByAccurence("AA BB CC DD BB"));
        System.out.println(SortByAmount.sortByAccurence("AA BB CC DD BB AA AA AA"));
    }
}