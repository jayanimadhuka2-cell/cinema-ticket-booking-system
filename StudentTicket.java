/**
 * Course : IT1207 - Object Oriented Programming
 * Project: P7 Cinema Ticket Booking (Group 7)
 * Author : <Student Name> (<Index Number>)
 * Role   : 5 - StudentTicket class
 */

/**
 * A discounted ticket for students: 50% off the standard price.
 * A student ID is required.
 */
public class StudentTicket extends Ticket {

    public static final double DISCOUNT_RATE = 0.5;

    private final String studentId;

    /**
     * @param ticketId     positive ticket id
     * @param customerName non-empty customer name
     * @param seatNumber   seat number between 1 and 30
     * @param studentId    non-empty student ID
     * @throws IllegalArgumentException if the student ID is empty or other values are invalid
     */
    public StudentTicket(int ticketId, String customerName, int seatNumber, String studentId) {
        super(ticketId, customerName, seatNumber);
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required for a student ticket.");
        }
        this.studentId = studentId.trim();
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public double getPrice() {
        return StandardTicket.PRICE * (1 - DISCOUNT_RATE);
    }

    @Override
    public String getTypeName() {
        return "Student";
    }

    @Override
    public String toString() {
        return super.toString() + " (Student ID: " + studentId + ")";
    }
}
