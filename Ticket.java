/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 2 - Ticket abstract class
 */

/**
 * Base class for all cinema tickets. Subclasses decide the price.
 */
public abstract class Ticket {

    private final int ticketId;
    private final String customerName;
    private final int seatNumber;

    /**
     * Creates a ticket after validating the common details.
     *
     * @param ticketId     positive ticket id
     * @param customerName non-empty customer name
     * @param seatNumber   seat number between 1 and 30
     * @throws IllegalArgumentException if any value is invalid
     */
    protected Ticket(int ticketId, String customerName, int seatNumber) {
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket id must be positive.");
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name must not be empty.");
        }
        if (seatNumber < Show.FIRST_SEAT || seatNumber > Show.LAST_SEAT) {
            throw new IllegalArgumentException("Seat number must be between "
                    + Show.FIRST_SEAT + " and " + Show.LAST_SEAT + ".");
        }
        this.ticketId = ticketId;
        this.customerName = customerName.trim();
        this.seatNumber = seatNumber;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Returns the price the customer pays for this ticket.
     *
     * @return price in rupees
     */
    public abstract double getPrice();

    /**
     * Returns the ticket type label, for example "Standard".
     *
     * @return the type name
     */
    public abstract String getTypeName();

    @Override
    public String toString() {
        return String.format("Ticket #%d | %-8s | %-15s | Seat %2d | Rs. %.2f",
                ticketId, getTypeName(), customerName, seatNumber, getPrice());
    }
}
