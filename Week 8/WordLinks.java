import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Word-links (also known as Doublets or Word Ladders) is a puzzle game based on words invented by Lewis Carroll
 * A word-links puzzle begins with two given words (called the first and last words) which are usually
 * semantically related. To solve the puzzle a player must find a chain of words which link the first word to the
 * last word, in such away that, two adjacent words in the list of words (i.e., words which are side by side in
 * the list) are of the same length and differ by one letter only.
 * <p>
 * @author Rían Errity
 * @since 4/04/2021 DDMMYYYY
 */
public class WordLinks {

    // A pattern is faster as a delimiter than String matching.
    private static final Pattern CSV_DELIMITER = Pattern.compile(",\\s?");
    private static final String DICTIONARY_PATH = "words.txt";
    private static final Set<String> EXIT_COMMANDS;

    private static String[] dictionary;

    static {
        final SortedSet<String> exitCommands = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        exitCommands.addAll(Arrays.asList("exit", "quit", "leave"));
        EXIT_COMMANDS = Collections.unmodifiableSortedSet(exitCommands);
    }

    /**
     * An implementation of Lewis Carroll's Word Links.
     * @implNote Does not make use of a Queue due to limitations placed on the assignment with specific
     *           parameters/return types -- and very specific method requirements making it impractical and
     *           inefficient to do so.
     * */
    public static void main(String[] args) {
        System.out.println("Welcome to Word-links, a game conceived by Lewis Carroll.");

        dictionary = readDictionary();
        boolean hasQuit = false;

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.print("Enter a comma separated list of words (or an empty list to quit): ");
                String operation = scanner.nextLine();

                if (EXIT_COMMANDS.contains(operation)) {
                    hasQuit = true;
                } else {
                    String[] inputWords = readWordList(operation);
                    System.out.println(isWordChain(inputWords)
                            ? "Valid chain of words from Lewis Carroll's word-links game."
                            : "Not a valid chain of words from Lewis Carroll's word-links game.");
                }
            } while (!hasQuit);
            System.out.println("Goodbye.");
        } catch (InputMismatchException exception) {
            System.out.println("Invalid input.");
        }
    }

    /**
     * Reads the "words.txt" file in the working directory, and returns an array, with each element constituting one line from the file.
     * @return A String[] containing each line of the file "words.txt"
     * */
    private static String[] readDictionary() {
        final LinkedList<String> words = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_PATH))) {
            // Reader#readLine discards the line terminators present.
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException exception) {
            System.out.println("word.txt is not present.");
        }

        return words.toArray(new String[0]);
    }

    /**
     * Reads the provided String as a set of comma-separated values (CSV)
     * @param csv The provided set of comma-separated values
     * @return An ArrayList containing each element of the CSV
     * */
    private static String[] readWordList(String csv) {
        return CSV_DELIMITER.split(csv);
    }

    /**
     * Returns whether or not there are no duplicate entries in the Array.
     * @implNote Uses a HashSet to confirm whether or not there are no duplicates, if there are duplicates present they will be discarded
     *           meaning a simple length check can determine if there were duplicates.
     * */
    private static boolean isUniqueList(String[] words) {
        return words.length == new HashSet<>(Arrays.asList(words)).size();
    }

    /**
     * Returns whether the provided word is in the dictionary.
     * */
    private static boolean isEnglishWord(String word) {
        return Arrays.binarySearch(dictionary, word) >= 0;
    }

    /**
     * Determines whether the two strings have a difference of one character.
     * @return True if there is only one character difference between the two provided Strings.
     * */
    private static boolean isDifferentByOne(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        char[] word1Chars = word1.toCharArray();
        char[] word2Chars = word2.toCharArray();

        int difference = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1Chars[i] != word2Chars[i]) {
                difference++;
            }
        }

        return difference == 1;
    }

    /**
     * Returns true if the provided array meets the criteria for being a valid Word Chain.
     * 1. Must be constructed of valid english words
     * <p>
     * 2. There must not be duplicates in the array
     * <p>
     * 3. Each entry in the array must be of the same length, and be a valid english word.
     * <p>
     * 4. Each entry in the array must be followed by another which only differs by one character
     * */
    private static boolean isWordChain(String[] wordChain) {
        // Checking for duplicates
        if (!isUniqueList(wordChain)) {
            return false;
        }

        // Ensuring every entry is a valid english word
        for (final String word : wordChain) {
            if (!isEnglishWord(word)) {
                return false;
            }
        }

        // Checking to ensure each entry is succeeded by a word with at most one differing character.
        String current = wordChain[0];
        for (int i = 1; i < wordChain.length; i++) {
            if (!isDifferentByOne(current, wordChain[i])) {
                return false;
            }
            current = wordChain[i];
        }

        // Logical conclusion if it passes the above tests
        return true;
    }

}
