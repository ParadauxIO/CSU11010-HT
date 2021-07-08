import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Chuck-A-Luck is a dice game where 3 six sided dice tumble in a spinning cage and players can place bets
 * with a House dealer on the outcome of the 3 dice when the cage stops spinning. It originated in British
 * Pubs many years ago and started without spinning cages. Instead, players used a horn-shaped chute made of
 * leather or metal to "chuck" the 3 dice across a table and then take their "luck" on how they landed.
 * <p>
 * Today, Chuck-A-Luck is still popular at many first-class casinos around the world.
 *
 * @author Rían Errity
 * @since 7/03/2021
 */
public class ChuckALuck {

    private static final int DICE_COUNT = 3;

    // These are static fields because of the dumb requirement of resolveBet.
    private static final Set<String> EXIT_COMMANDS;
    private static final Scanner scanner = new Scanner(System.in);
    private static int betsMade = 0;

    // Initialise valid exit commands.
    static {
        final SortedSet<String> exitCommands = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        exitCommands.addAll(Arrays.asList("exit", "quit", "leave"));
        EXIT_COMMANDS = Collections.unmodifiableSortedSet(exitCommands);
    }

    public static void main(String[] args) {
        Wallet wallet = new Wallet();
        boolean hasQuit = false;
        double originalBalance;

        System.out.println("Chuck-a-Luck Game : Rían Errity");
        System.out.println("Valid operations: triple, field, high, low, balance, quit\n------");

        System.out.print("How much cash do you have on hand? : ");

        wallet.put(originalBalance = parseDouble());

        do {
            System.out.print("What kind of bet do you wish to place? : ");
            String operation = scanner.nextLine();

            if (EXIT_COMMANDS.contains(operation)) {
                hasQuit = true;
            } else {
                if (operation.equalsIgnoreCase("balance")) {
                    System.out.println("Your current cash balance is: " + wallet.check());
                } else {
                    resolveBet(operation, wallet);
                }
            }

        } while (wallet.check() > 0 && !hasQuit);

        // Print some information about their game.

        System.out.println("Game Statistics\n------");
        System.out.println("Wallet balance: " + wallet.check());
        System.out.println("Bets made: " + betsMade);
        System.out.println("You started with: " + originalBalance);

        double percentageChange = (wallet.check() / originalBalance) * 100d;
        System.out.printf("That represents a %s of %.2f%%%n", (percentageChange > 100) ? "gain" : "loss", (percentageChange > 100) ?
                percentageChange : 100d - percentageChange);
        scanner.close();
    }

    public static void resolveBet(String betType, Wallet wallet) {
        System.out.println("Your current cash balance is: " + wallet.check());
        System.out.print("How much would you like to bet? : ");
        double currentBet = parseDouble();

        if (currentBet <= 0) {
            System.out.println("Your bet must be greater than €1");
            return;
        }

        if (!wallet.get(currentBet)) {
            System.out.println("You have insufficient funds to complete this transaction.");
            return;
        }

        betsMade++; // Used in the game statistics
        Dice[] thrownDice = new Dice[DICE_COUNT]; // How many dice to roll

        for (int i = 0; i < thrownDice.length; i++) {
            thrownDice[i] = new Dice();
        } // Populate dice array

        int score = getScore(thrownDice);

        switch (betType) {
            case "triple": {
                // If all dice show the same number. 30:1
                if (areFacesEqual(thrownDice)) {
                    // User has won.
                    currentBet += (currentBet * 30);

                    // House has won
                    if (areFacesEqual(thrownDice, 1) || areFacesEqual(thrownDice, 6)) {
                        currentBet = 0;
                    }

                    // House has won.
                } else {
                    currentBet = 0;
                }
                break;
            }

            case "field": {
                // If the score is les than 8 or greater than 12 user wins, otherwise house wins. 1:1
                currentBet = (score < 8 || score > 12) ? currentBet * 2 : 0;
                break;
            }

            case "high": {
                // house wins
                if (score > 10) {
                    currentBet *= 2;

                    // high triple check : house wins in this case
                    if (areFacesEqual(thrownDice, 4) || areFacesEqual(thrownDice, 5) || areFacesEqual(thrownDice, 6)) {
                        currentBet = 0;
                    }
                    // user wins
                } else {
                    currentBet = 0;
                }
                break;
            }

            case "low": {
                if (score < 11) {
                    currentBet *= 2;

                    // low triple check : house wins in this case
                    if (areFacesEqual(thrownDice, 1) || areFacesEqual(thrownDice, 2) || areFacesEqual(thrownDice, 3)) {
                        currentBet = 0;
                    }

                    // house wins
                } else {
                    currentBet = 0;
                }
                break;
            }

            default: {
                System.out.println("Error: Invalid operation.\nValid operations: triple, field, high, low, balance, quit");
                return;
            }
        }

        StringBuilder resultsDisplay = new StringBuilder().append("Dice show: ");

        for (Dice die : thrownDice) {
            resultsDisplay.append(die.topFace()).append(" ");
        }

        // Modify the user's wallet with their winnings.
        wallet.put(currentBet);

        System.out.println(resultsDisplay.toString().trim());
        System.out.printf("%s. Your balance is now %.2f!%n", (currentBet == 0) ? "The house won" : "You won", wallet.check());
    }

    /**
     * Gets the total score between all dice provided.
     *
     * @param thrownDice All of the thrown dice.
     * @return Returns the sum of the dice faces.
     */
    private static int getScore(Dice[] thrownDice) {
        if (thrownDice == null || thrownDice.length == 0) {
            throw new IllegalArgumentException("Must provide a valid dice array.");
        }

        int score = 0;

        for (final Dice die : thrownDice) {
            score += die.topFace();
        }

        return score;
    }

    /**
     * Returns true if the top face of each of the provided dice are the same.
     *
     * @param thrownDice All of the thrown dice.
     * @return true when all faces show the same number.
     */
    private static boolean areFacesEqual(Dice[] thrownDice) {
        if (thrownDice == null || thrownDice.length == 0) {
            throw new IllegalArgumentException("Must provide a valid dice array.");
        }

        int firstFace = thrownDice[0].topFace();

        for (int i = 1; i < thrownDice.length; i++) {
            if (firstFace != thrownDice[i].topFace()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the top face of each of the provided dice are equal to the provided number.
     *
     * @param thrownDice All of the dice thrown
     * @param number     What number you wish to check that all the dice show
     * @return Whether or not all of the dice faces are equal to the provided number.
     */
    private static boolean areFacesEqual(Dice[] thrownDice, int number) {
        if (thrownDice == null || thrownDice.length == 0) {
            throw new IllegalArgumentException("Must provide a valid dice array.");
        }

        for (int i = 1; i < thrownDice.length; i++) {
            if (thrownDice[i].topFace() != number) {
                return false;
            }
        }

        return true;
    }

    /**
     * Parses a double from the scanner, disregarding the entire line in one.
     */
    private static double parseDouble() {
        return Double.parseDouble(scanner.nextLine().trim());
    }

}
