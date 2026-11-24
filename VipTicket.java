/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 4 - VipTicket class
 */

/**
 * A VIP ticket: Rs. 2000 plus a Rs. 500 snack combo.
 */
public class VipTicket extends Ticket {

    public static final double BASE_PRICE = 2000.0;
    public static final double SNACK_COMBO_PRICE = 500.0;

    public VipTicket(int ticketId, String customerName, int seatNumber) {
        super(ticketId, customerName, seatNumber);
    }

    @Override
    public double getPrice() {
        return BASE_PRICE + SNACK_COMBO_PRICE;
    }

    @Override
    public String getTypeName() {
        return "VIP";
    }

    @Override
    public String toString() {
        return super.toString() + " (snack combo included)";
    }
}
