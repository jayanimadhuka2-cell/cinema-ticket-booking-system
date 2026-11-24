/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 8 - CinemaApp class (menu and all Scanner input)
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Text menu for the cinema system. All user input is read here only.
 */
public class CinemaApp {

    private static final int EXIT_OPTION = 7;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        int choice = 0;
        try {
            while (choice != EXIT_OPTION) {
                printMenu();
                choice = readInt("Enter your choice: ");
                handleChoice(choice, cinema);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Input closed. Exiting.");
        }
        System.out.println("Thank you for using the Cinema Ticket Booking System. Goodbye!");
        SCANNER.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== CINEMA TICKET BOOKING =====");
        System.out.println("1. Add show");
        System.out.println("2. List shows");
        System.out.println("3. Show seat map");
        System.out.println("4. Book ticket");
        System.out.println("5. Cancel ticket");
        System.out.println("6. Sales report");
        System.out.println("7. Exit");
    }

    private static void handleChoice(int choice, Cinema cinema) {
        switch (choice) {
            case 1:
                addShow(cinema);
                break;
            case 2:
                System.out.println(cinema.listShows());
                break;
            case 3:
                showSeatMap(cinema);
                break;
            case 4:
                bookTicket(cinema);
                break;
            case 5:
                cancelTicket(cinema);
                break;
            case 6:
                System.out.println(cinema.salesReport());
                break;
            case EXIT_OPTION:
                break;
            default:
                System.out.println("Invalid option. Please choose 1 to 7.");
        }
    }

    private static void addShow(Cinema cinema) {
        int showId = readPositiveInt("Show id: ");
        String title = readText("Movie title: ");
        String time = readText("Show time (e.g. 18:30): ");
        if (cinema.addShow(showId, title, time)) {
            System.out.println("Show added.");
        } else {
            System.out.println("Could not add show (duplicate id or cinema full).");
        }
    }

    private static void showSeatMap(Cinema cinema) {
        Show show = cinema.findShow(readPositiveInt("Show id: "));
        if (show == null) {
            System.out.println("Show not found.");
        } else {
            System.out.println(show.getSeatMap());
        }
    }

    private static void bookTicket(Cinema cinema) {
        int showId = readPositiveInt("Show id: ");
        if (cinema.findShow(showId) == null) {
            System.out.println("Show not found.");
            return;
        }
        System.out.println("Ticket type: 1) Standard  2) VIP  3) Student");
        int typeChoice = readPositiveInt("Choose type: ");
        String type;
        String studentId = "";
        if (typeChoice == 1) {
            type = Cinema.TYPE_STANDARD;
        } else if (typeChoice == 2) {
            type = Cinema.TYPE_VIP;
        } else if (typeChoice == 3) {
            type = Cinema.TYPE_STUDENT;
            studentId = readText("Student ID: ");
        } else {
            System.out.println("Invalid ticket type.");
            return;
        }
        String customer = readText("Customer name: ");
        int seat = readPositiveInt("Seat number (1-30): ");
        Ticket ticket = cinema.bookTicket(showId, type, customer, seat, studentId);
        if (ticket == null) {
            System.out.println("Booking failed: seat must be 1-30 and free.");
        } else {
            System.out.println("Booked! " + ticket);
        }
    }

    private static void cancelTicket(Cinema cinema) {
        int showId = readPositiveInt("Show id: ");
        int ticketId = readPositiveInt("Ticket id: ");
        double refund = cinema.cancelTicket(showId, ticketId);
        if (refund == Show.NOT_FOUND) {
            System.out.println("Cancellation failed: show or ticket not found.");
        } else {
            System.out.printf("Ticket cancelled. Refund (80%%): Rs. %.2f%n", refund);
        }
    }

    /** Reads a whole number; asks again until the input is valid. */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    /** Reads a whole number greater than zero; asks again until valid. */
    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Please enter a number greater than zero.");
        }
    }

    /** Reads a non-empty line of text. */
    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("This field cannot be empty.");
        }
    }
}
