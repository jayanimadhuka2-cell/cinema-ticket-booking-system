/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 6 - Show class
 */

/**
 * One screening of a movie. Holds 30 seats and the tickets sold for them.
 * The ticket array holds different Ticket subclasses (polymorphism).
 */
public class Show implements Bookable {

    public static final int FIRST_SEAT = 1;
    public static final int LAST_SEAT = 30;
    public static final int TOTAL_SEATS = 30;
    /** Returned by cancelTicket when no ticket with that id exists. */
    public static final double NOT_FOUND = -1.0;

    private static final double REFUND_RATE = 0.8;
    private static final int SEATS_PER_ROW = 10;

    private final int showId;
    private final String movieTitle;
    private final String time;
    private final boolean[] seatTaken = new boolean[TOTAL_SEATS + 1]; // index 0 unused
    private final Ticket[] sold = new Ticket[TOTAL_SEATS];
    private int soldCount;
    private double grossSales;
    private double totalRefunded;

    /**
     * @param showId     positive show id
     * @param movieTitle non-empty movie title
     * @param time       non-empty show time, for example "18:30"
     * @throws IllegalArgumentException if any value is invalid
     */
    public Show(int showId, String movieTitle, String time) {
        if (showId <= 0) {
            throw new IllegalArgumentException("Show id must be positive.");
        }
        if (movieTitle == null || movieTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Movie title must not be empty.");
        }
        if (time == null || time.trim().isEmpty()) {
            throw new IllegalArgumentException("Show time must not be empty.");
        }
        this.showId = showId;
        this.movieTitle = movieTitle.trim();
        this.time = time.trim();
    }

    public int getShowId() {
        return showId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getTime() {
        return time;
    }

    public int getTicketsSold() {
        return soldCount;
    }

    /**
     * Net income: money taken for tickets minus refunds paid out.
     * A cancelled ticket therefore leaves the cinema with 20% of its price.
     *
     * @return net income in rupees
     */
    public double getIncome() {
        return grossSales - totalRefunded;
    }

    private boolean isValidSeat(int seatNo) {
        return seatNo >= FIRST_SEAT && seatNo <= LAST_SEAT;
    }

    public boolean isSeatBooked(int seatNo) {
        return isValidSeat(seatNo) && seatTaken[seatNo];
    }

    @Override
    public boolean bookSeat(int seatNo) {
        if (!isValidSeat(seatNo) || seatTaken[seatNo]) {
            return false;
        }
        seatTaken[seatNo] = true;
        return true;
    }

    @Override
    public boolean cancelSeat(int seatNo) {
        if (!isValidSeat(seatNo) || !seatTaken[seatNo]) {
            return false;
        }
        seatTaken[seatNo] = false;
        return true;
    }

    /**
     * Counts the seats that are still free.
     *
     * @return number of free seats
     */
    public int availableSeats() {
        int free = 0;
        for (int seat = FIRST_SEAT; seat <= LAST_SEAT; seat++) {
            if (!seatTaken[seat]) {
                free++;
            }
        }
        return free;
    }

    /**
     * Books the ticket's seat and records the sale.
     *
     * @param ticket the ticket to sell
     * @return true if the seat was free and the sale was recorded
     */
    public boolean sellTicket(Ticket ticket) {
        if (ticket == null || !bookSeat(ticket.getSeatNumber())) {
            return false;
        }
        sold[soldCount] = ticket;
        soldCount++;
        grossSales += ticket.getPrice();
        return true;
    }

    /**
     * Cancels a sold ticket, frees its seat and refunds 80% of its price.
     *
     * @param ticketId the id of the ticket to cancel
     * @return the refund amount, or NOT_FOUND if no such ticket exists
     */
    public double cancelTicket(int ticketId) {
        for (int i = 0; i < soldCount; i++) {
            if (sold[i].getTicketId() == ticketId) {
                Ticket ticket = sold[i];
                cancelSeat(ticket.getSeatNumber());
                for (int j = i; j < soldCount - 1; j++) {
                    sold[j] = sold[j + 1];
                }
                soldCount--;
                sold[soldCount] = null;
                double refund = ticket.getPrice() * REFUND_RATE;
                totalRefunded += refund;
                return refund;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Builds a text seat map. Free seats show their number, booked seats show XX.
     *
     * @return the seat map as text
     */
    public String getSeatMap() {
        StringBuilder map = new StringBuilder();
        map.append("Seat map for ").append(movieTitle).append(" at ").append(time)
                .append(System.lineSeparator());
        for (int seat = FIRST_SEAT; seat <= LAST_SEAT; seat++) {
            if (seatTaken[seat]) {
                map.append("[XX] ");
            } else {
                map.append(String.format("[%02d] ", seat));
            }
            if (seat % SEATS_PER_ROW == 0) {
                map.append(System.lineSeparator());
            }
        }
        map.append("XX = booked");
        return map.toString();
    }

    /**
     * Lists every sold ticket. Each line comes from the overridden toString().
     *
     * @return the tickets as text
     */
    public String describeTickets() {
        if (soldCount == 0) {
            return "  (no tickets sold)";
        }
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < soldCount; i++) {
            text.append("  ").append(sold[i]);
            if (i < soldCount - 1) {
                text.append(System.lineSeparator());
            }
        }
        return text.toString();
    }

    @Override
    public String toString() {
        return String.format("Show #%d | %s | %s | Free seats: %d/%d",
                showId, movieTitle, time, availableSeats(), TOTAL_SEATS);
    }
}
