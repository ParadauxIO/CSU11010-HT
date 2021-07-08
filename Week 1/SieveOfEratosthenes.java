import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Assignment Brief: Write a Java program which uses the Sieve of Eratosthenes to compute the prime numbers from 2 up to a given number N
 * which is entered by the user. As part of your solution you must use an array to represent the sequence of numbers (which may contain
 * both crossed out and non-crossed out numbers)
 * <p>
 * @author Rían Errity
 * @since 13/02/2021
 * @version 1.0
 * */
public class SieveOfEratosthenes {

    /**
     * Entry point to the application.
     * @param args Unused in this application.
     * */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the maximum integer: ");
        int[] createdSequence;
        try {
             createdSequence = sieve(scanner.nextInt());
        } catch (IllegalArgumentException ok) {
            System.out.println("Please enter a value greater than 2.");
            return;
        } catch (InputMismatchException ok) {
            System.out.println("Please enter a valid number.");
            return;
        }

        System.out.println("The Sequence with non-primes surrounded with [ ] :");
        System.out.println(sequenceToString(createdSequence));

        System.out.println("The Sequence of Prime Numbers : " );
        System.out.println(nonCrossedOutSubseqToString(createdSequence));
    }

    /**
     * Creates a sequence of numbers from 2 through finalNumberInArray.
     * <p>
     * @param finalNumberInArray The number you wish to terminate the sequence at.
     * @return An int[] containing every number from 2 through finalNumberInArray incrementally.
     * */
    public static int[] createSequence(int finalNumberInArray) {
        if (finalNumberInArray < 2) {
            throw new IllegalArgumentException("Value must be greater than 2");
        }

        int[] createdSequence = new int[finalNumberInArray-1];

        for (int i = 2; i <= finalNumberInArray; i++) {
            createdSequence[i-2] = i;
        }

        return createdSequence;
    }

    /**
     * Replaces every higher multiple of multiple with -1
     * <p>
     * @param createdSequence The array in which you wish to replace higher multiples with -1.
     * @param multiple The number whose higher multiples you wish to set to -1
     * @return An array, with higher multiples of multiple set to -1
     * */
    public static int[] crossOutHigherMultiples(int[] createdSequence, int multiple) {
        if (createdSequence == null) {
            throw new IllegalArgumentException("The passed array cannot be null");
        }


        int[] newSequence = createdSequence.clone();

        for (int j = (multiple*multiple); j < (createdSequence.length+2); j = (j + multiple)) {
            newSequence[j-2] = -1;
        }

        return newSequence;
    }

    /**
     * Employs the "Sieve of Eratosthenes" in that it goes through each number beginning with 2, and crossing out its higher
     * multiples, then going through the next non-crossed out number's higher multiples until the provided number is reached.
     * <p>
     * @param finalNumberInArray The Number you wish to end the sequence at.
     * @return An int[] with non-primes set to -1. Their value can be recovered using the index+2
     * */
    public static int[] sieve(int finalNumberInArray) {
        int[] numbers = createSequence(finalNumberInArray);

        for (int i = 2; i< Math.sqrt(finalNumberInArray); i++) {
            if (numbers[i] != -1) {
                numbers = crossOutHigherMultiples(numbers, i);
            }
        }

        return numbers;
    }

    /**
     * Returns every character in the provided array, with crossed off values being surrounded with [ ]
     * <p>
     * @param createdSequence The array created using {@link Assignment#sieve(int)}
     * @return A comma-separated sequence of createdSequence.
     * */
    public static String sequenceToString(int[] createdSequence) {
        if (createdSequence == null) {
            throw new IllegalArgumentException("The passed array cannot be null");
        }

        StringBuilder builder = new StringBuilder();

        // Print every number in the array, replace the -1s with their appropriate value, surrounded by [ ]
        int i = 0;
        for (; i < createdSequence.length-1; i++) {
            builder.append(crossOff(i, createdSequence[i])).append(", ");
        }
        builder.append(crossOff(i, createdSequence[i]));

        return builder.toString();
    }

    /**
     * Returns the sequence of non-crossed out createdSequence; createdSequence whose value is not equal to -1 in the array.
     * <p>
     * @param createdSequence The array created using {@link Assignment#sieve(int)}
     * @return A comma-separated sequence of createdSequence.
     * */
    public static String nonCrossedOutSubseqToString(int[] createdSequence) {
        if (createdSequence == null) {
            throw new IllegalArgumentException("The passed array cannot be null");
        }

        StringBuilder builder = new StringBuilder();

        // Print only the primes (non-crossed out) which is signified by being let equal to -1
        for (int i = 0; i < createdSequence.length; i++) {
            if (createdSequence[i] != -1) {
                builder.append(createdSequence[i]).append(", ");
            }
        }

        if (builder.toString().endsWith(", ")) {
            // Deletes the final ", "
            builder.deleteCharAt(builder.length()-1);
            builder.deleteCharAt(builder.length()-1);
        }

        return builder.toString();
    }

    /**
     * Replaces crossed out numbers with their appropriate value surrounded by square brackets.
     * <p>
     * @param index The placement in the array where the number can be found
     * @param number The value at that index in the array.
     * @return A number, potentially surrounded with []
     * */
    private static String crossOff(int index, int number) {
        return number == -1 ? ("[" + (index+2) + "]") : String.valueOf(number);
    }

}
