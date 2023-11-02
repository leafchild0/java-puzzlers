package puzzlers.tasks;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindWodPuzzleTest {

    private final FindWordsPuzzle wordsPuzzle = new FindWordsPuzzle();
    private final char[][] puzzle = {
            {'F','Y','Y','H','N','R','D'},
            {'R','L','J','C','I','N','U'},
            {'A','A','W','A','A','H','R'},
            {'N','T','K','L','P','N','E'},
            {'C','I','L','F','S','A','P'},
            {'E','O','G','O','T','P','N'},
            {'H','P','O','L','A','N','D'}
    };

    @Test
    public void findWords() {
        Set<String> words = Set.of("FRANCE", "POLAND", "INDIA", "JAPAN", "USA", "HOLLAND");
        Set<String> wordsFound = wordsPuzzle.findWords(puzzle, words);
        assertEquals(3, wordsFound.size());
        assertEquals(Set.of("FRANCE", "JAPAN", "HOLLAND"), wordsFound);
    }
}
