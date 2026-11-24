/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 9 - CinemaTest class
 */

/**
 * Simple PASS/FAIL checks for the cinema system. Run with: java CinemaTest
 */
public class CinemaTest {

    private static final double DELTA = 0.001;
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        Cinema cinema = new Cinema();

        check("Add a show", cinema.addShow(1, "Avatar", "18:30"));
        check("EDGE: duplicate show id is rejected", !cinema.addShow(1, "Other", "20:00"));
        check("EDGE: empty movie title is rejected", !cinema.addShow(2, "  ", "20:00"));

        Ticket standard = cinema.bookTicket(1, "STANDARD", "Nimal", 1);
        Ticket vip = cinema.bookTicket(1, "VIP", "Kamal", 2);
        Ticket student = cinema.bookTicket(1, "STUDENT", "Sunil", 3, "IT/2024/001");

        check("Standard ticket costs Rs. 1000", standard != null && near(standard.getPrice(), 1000));
        check("VIP ticket costs Rs. 2500 (2000 + 500 combo)", vip != null && near(vip.getPrice(), 2500));
        check("Student ticket costs Rs. 500 (50% off)", student != null && near(student.getPrice(), 500));
        check("Same show sells all three ticket types", cinema.findShow(1).getTicketsSold() == 3);
        check("Available seats drop to 27 after 3 bookings", cinema.findShow(1).availableSeats() == 27);

        check("EDGE: a seat cannot be booked twice", cinema.bookTicket(1, "STANDARD", "Amal", 1) == null);
        check("EDGE: seat 0 is rejected", cinema.bookTicket(1, "STANDARD", "Amal", 0) == null);
        check("EDGE: seat 31 is rejected", cinema.bookTicket(1, "STANDARD", "Amal", 31) == null);
        check("EDGE: student ticket without student ID is rejected",
                cinema.bookTicket(1, "STUDENT", "Amal", 5) == null);
        check("EDGE: empty customer name is rejected", cinema.bookTicket(1, "VIP", "", 6) == null);
        check("Unknown show id returns null", cinema.bookTicket(99, "VIP", "Amal", 7) == null);

        Ticket[] tickets = {standard, vip, student};
        double total = 0;
        for (Ticket ticket : tickets) {
            total += ticket.getPrice();
        }
        check("Polymorphism: Ticket[] total price is Rs. 4000", near(total, 4000));

        Bookable bookable = cinema.findShow(1);
        check("Show works through the Bookable interface", bookable.bookSeat(10) && bookable.cancelSeat(10));

        double refund = cinema.cancelTicket(1, standard.getTicketId());
        check("Cancel refunds 80% of the price (Rs. 800)", near(refund, 800));
        check("Cancel frees the seat", !cinema.findShow(1).isSeatBooked(1));
        check("Income after refund is Rs. 3200", near(cinema.findShow(1).getIncome(), 3200));
        check("EDGE: cancelling an unknown ticket fails",
                cinema.cancelTicket(1, 999) == Show.NOT_FOUND);
        check("A cancelled seat can be booked again", cinema.bookTicket(1, "STANDARD", "Amal", 1) != null);
        check("Sales report mentions the movie", cinema.salesReport().contains("Avatar"));

        System.out.println();
        System.out.println("Passed: " + passed + " | Failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static boolean near(double actual, double expected) {
        return Math.abs(actual - expected) < DELTA;
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS: " + name);
        } else {
            failed++;
            System.out.println("FAIL: " + name);
        }
    }
}
