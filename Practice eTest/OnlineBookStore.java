import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class OnlineBookStore {
    public static int ISBN_INDEX = 0;
    public static int TITLE_INDEX = 1;
    public static int AUTHOR_INDEX = 2;
    public static int PUBLISHER_INDEX = 3;
    public static int PUBLISHER_YEAR_INDEX = 4;
    public static int QUANTITY_INDEX = 5;
    public static int PRICE_INDEX = 6;

    private static final Scanner scanner = new Scanner(System.in);

    private static final Set<String> EXIT_COMMANDS;
    // Initialise valid exit commands.
    static {
        final SortedSet<String> exitCommands = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        exitCommands.addAll(Arrays.asList("exit", "quit", "leave"));
        EXIT_COMMANDS = Collections.unmodifiableSortedSet(exitCommands);
    }

    public static void main(String[] args) {
        ArrayList<Book> bookList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("books.txt"))){
            for (String currentLine; (currentLine = bufferedReader.readLine()) != null;) {
                String[] bookCsv = currentLine.split(", ");
                bookList.add(new Book(bookCsv[ISBN_INDEX], bookCsv[TITLE_INDEX], bookCsv[AUTHOR_INDEX], bookCsv[PUBLISHER_INDEX],
                        Integer.parseInt(bookCsv[PUBLISHER_YEAR_INDEX]), Integer.parseInt(bookCsv[QUANTITY_INDEX]),
                        Double.parseDouble(bookCsv[PRICE_INDEX])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Uncomment the following lines once you have implemented the required methods
        printBookDetails(bookList);
        purchaseBook(bookList);
    }

    public static void printBookDetails(ArrayList<Book> bookList) {
        for (final Book book : bookList) {
            System.out.printf("ISBN: %s%nTitle: %s%nAuthor: %s%nPublisher: %s%nYear of Publication: %s%nQuantity: %s%nPrice: %s%n",
                    book.getIsbn(), book.getTitle(),book.getAuthor(), book.getPublisher(), book.getPublishYear(), book.getQuantity(),
                    book.getPrice());
        }
    }

    public static Book getBook(ArrayList<Book> bookList, String title) {
        for (final Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }

        return null;
    }

    public static void topUpCard(ChargeCard card, double amount) {
        card.topUpFunds(amount);
    }

    public static void purchaseBook(ArrayList<Book> bookList) {
        System.out.print("How much money do you have? : ");
        ChargeCard card = new ChargeCard();
        topUpCard(card, parseDoubleLine());
        
        boolean hasQuit = false;

        while (!hasQuit) {
            System.out.print("Which book would you like to purchase? (or quit) : ");
            String operation = scanner.nextLine();
            if (EXIT_COMMANDS.contains(operation)) {
                hasQuit = true;
            } else {
                Book requestedBook = getBook(bookList, operation);

                if (requestedBook == null) {
                    System.out.println("This book does not appear to exist.");
                    return;
                }

                if (requestedBook.getQuantity() <= 0) {
                    System.out.println("There is no more " + operation + " left in stock.");
                    return;
                }

                if (card.getFunds() < requestedBook.getPrice()) {
                    System.out.println("You have insufficient funds to complete this transaction.\n This book costs: "
                            + requestedBook.getPrice() + "\n Your balance is: " + card.getFunds());
                    return;
                }

                System.out.println("You have successfully purchased: " + requestedBook.getTitle() + " by " + requestedBook.getAuthor()
                        + "\nThe necessary funds have been removed from your account.");

                card.removeFunds(requestedBook.getPrice());
                requestedBook.setQuantity(requestedBook.getQuantity() - 1);
            }
        }
        

    }

    private static double parseDoubleLine() {
        return Double.parseDouble(scanner.nextLine().trim());
    }


}
