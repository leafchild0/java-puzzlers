package puzzlers.tasks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class FindWordsPuzzle {

    public Set<String> findWords(char[][] puzzle, Set<String> words) {
        Set<String> foundWords = new HashSet<>();
        int minimumWordLength = findMinimumWordLength(words);
        Set<String> possibleWords = findPossibleWords(puzzle, minimumWordLength);
        for(String word : words) {
            for(String possibleWord : possibleWords) {
                if(possibleWord.contains(word) || possibleWord.contains(new StringBuilder(word).reverse().toString())) {
                    foundWords.add(word);
                    break;
                }
            }
        }
        return foundWords;
    }

    private int findMinimumWordLength(Set<String> words) {
        int minimumLength = Integer.MAX_VALUE;
        for(String word : words) {
            if(word.length() < minimumLength)
                minimumLength = word.length();
        }
        return minimumLength;
    }

    private Set<String> findPossibleWords(char[][] puzzle, int minimumWordLength) {
        Set<String> possibleWords = new LinkedHashSet<>();
        int dimension = puzzle.length; //Assuming puzzle is square
        if(dimension >= minimumWordLength) {
            /* Every row in the puzzle is added as a possible word holder */
            for (char[] chars : puzzle) {
                if (chars.length >= minimumWordLength) {
                    possibleWords.add(Arrays.toString(chars));
                }
            }
            /* Every column in the puzzle is added as a possible word holder */
            for(int i = 0; i < dimension; i++) {
                StringBuilder temp = new StringBuilder();
                for (char[] chars : puzzle) {
                    temp.append(chars[i]);
                }
                possibleWords.add(temp.toString());
            }

            /* Adding principle diagonal word holders */
            StringBuilder temp1 = new StringBuilder();
            StringBuilder temp2 = new StringBuilder();
            for(int i = 0; i < dimension; i++) {
                temp1.append(puzzle[i][i]);
                temp2.append(puzzle[i][dimension - i - 1]);
            }
            possibleWords.add(temp1.toString());
            possibleWords.add(temp2.toString());

            /* Adding non-principle diagonal word holders */
            for(int i = 1; i < dimension - minimumWordLength; i++) {
                temp1 = new StringBuilder();
                temp2 = new StringBuilder();
                StringBuilder temp3 = new StringBuilder();
                StringBuilder temp4 = new StringBuilder();
                for(int j = i, k = 0; j < dimension && k < dimension; j++, k++) {
                    temp1.append(puzzle[j][k]);
                    temp2.append(puzzle[k][j]);
                    temp3.append(puzzle[dimension - j - 1][k]);
                    temp4.append(puzzle[dimension - k - 1][j]);
                }
                possibleWords.add(temp1.toString());
                possibleWords.add(temp2.toString());
                possibleWords.add(temp3.toString());
                possibleWords.add(temp4.toString());
            }
        }
        return possibleWords;
    }
}