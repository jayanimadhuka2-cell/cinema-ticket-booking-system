/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 7 - Cinema class
 */

/**
 * Manages all shows, creates tickets and produces the sales report.
 * This class never reads input or prints; it only returns results.
 */
public class Cinema {

    public static final String TYPE_STANDARD = "STANDARD";
    public static final String TYPE_VIP = "VIP";
    public static final String TYPE_STUDENT = "STUDENT";

    private static final int MAX_SHOWS = 20;

    private final Show[] shows = new Show[MAX_SHOWS];
    private int showCount;
    private int nextTicketId = 1;

    /**
     * Adds a new show.
     *
     * @return true if added; false if the id already exists, the cinema is full or details are invalid
     */
    public boolean addShow(int showId, String movieTitle, String time) {
        if (showCount >= MAX_SHOWS || findShow(showId) != null) {
            return false;
        }
        try {
            shows[showCount] = new Show(showId, movieTitle, time);
            showCount++;
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Finds a show by id.
     *
     * @return the show, or null if not found
     */
    public Show findShow(int showId) {
        for (int i = 0; i < showCount; i++) {
            if (shows[i].getShowId() == showId) {
                return shows[i];
            }
        }
        return null;
    }

    public int getShowCount() {
        return showCount;
    }

    /**
     * Books a ticket of the given type (STANDARD, VIP or STUDENT).
     * A student ticket needs a student ID, so use the five-argument version for it.
     *
     * @return the new ticket, or null if the booking failed
     */
    public Ticket bookTicket(int showId, String type, String customer, int seat) {
        return bookTicket(showId, type, customer, seat, "");
    }

    /**
     * Books a ticket of the given type, with a student ID for student tickets.
     *
     * @return the new ticket, or null if the show or type is unknown, the seat is
     *         invalid or already taken, or any detail is invalid
     */
    public Ticket bookTicket(int showId, String type, String customer, int seat, String studentId) {
        Show show = findShow(showId);
        if (show == null) {
            return null;
        }
        Ticket ticket = createTicket(type, customer, seat, studentId);
        if (ticket == null || !show.sellTicket(ticket)) {
            return null;
        }
        nextTicketId++;
        return ticket;
    }

    private Ticket createTicket(String type, String customer, int seat, String studentId) {
        if (type == null) {
            return null;
        }
        try {
            switch (type.trim().toUpperCase()) {
                case TYPE_STANDARD:
                    return new StandardTicket(nextTicketId, customer, seat);
                case TYPE_VIP:
                    return new VipTicket(nextTicketId, customer, seat);
                case TYPE_STUDENT:
                    return new StudentTicket(nextTicketId, customer, seat, studentId);
                default:
                    return null;
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Cancels a ticket and refunds 80% of its price.
     *
     * @return the refund amount, or Show.NOT_FOUND if the show or ticket does not exist
     */
    public double cancelTicket(int showId, int ticketId) {
        Show show = findShow(showId);
        if (show == null) {
            return Show.NOT_FOUND;
        }
        return show.cancelTicket(ticketId);
    }

    /**
     * Lists all shows, one per line.
     */
    public String listShows() {
        if (showCount == 0) {
            return "No shows added yet.";
        }
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < showCount; i++) {
            text.append(shows[i]);
            if (i < showCount - 1) {
                text.append(System.lineSeparator());
            }
        }
        return text.toString();
    }

    /**
     * Builds the sales report: tickets sold and income per show, then the totals.
     */
    public String salesReport() {
        if (showCount == 0) {
            return "No shows added yet.";
        }
        String nl = System.lineSeparator();
        StringBuilder report = new StringBuilder("===== SALES REPORT =====").append(nl);
        int totalTickets = 0;
        double totalIncome = 0;
        for (int i = 0; i < showCount; i++) {
            Show show = shows[i];
            report.append(String.format("Show #%d | %s | %s | Tickets sold: %d | Income: Rs. %.2f",
                    show.getShowId(), show.getMovieTitle(), show.getTime(),
                    show.getTicketsSold(), show.getIncome())).append(nl);
            report.append(show.describeTickets()).append(nl);
            totalTickets += show.getTicketsSold();
            totalIncome += show.getIncome();
        }
        report.append(String.format("TOTAL | Tickets sold: %d | Income: Rs. %.2f", totalTickets, totalIncome));
        return report.toString();
    }
}
