/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 3 - StandardTicket class
 */

/**
 * A normal ticket that costs Rs. 1000.
 */
public class StandardTicket extends Ticket {

    public static final double PRICE = 1000.0;

    public StandardTicket(int ticketId, String customerName, int seatNumber) {
        super(ticketId, customerName, seatNumber);
    }

    @Override
    public double getPrice() {
        return PRICE;
    }

    @Override
    public String getTypeName() {
        return "Standard";
    }
}


