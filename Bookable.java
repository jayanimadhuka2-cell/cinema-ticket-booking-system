/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 1 - Bookable interface
 */

/**
 * Contract for anything that has numbered seats that can be booked
 * and cancelled.
 */
public interface Bookable {

    /**
     * Books a seat.
     *
     * @param seatNo the seat number to book
     * @return true if the seat was free and is now booked, otherwise false
     */
    boolean bookSeat(int seatNo);

    /**
     * Cancels the booking of a seat.
     *
     * @param seatNo the seat number to free
     * @return true if the seat was booked and is now free, otherwise false
     */
    boolean cancelSeat(int seatNo);
}
