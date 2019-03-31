package seedu.addressbook.data.match;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a team's ticket sales of a match in USD in the address book.
 */

public class TicketSales {

    public static final String EXAMPLE = "500";
    public static final String MESSAGE_TICKETSALES_CONSTRAINTS = "TeamName's ticket sales in USD";
    public static final String TICKETSALES_VALIDATION_REGEX = "\\d*";

    public final String value;

    /**
     * Validates given ticket sales amount.
     */
    public TicketSales(String ticketSales) throws IllegalValueException {
        ticketSales = ticketSales.trim();
        if (!isValidTicketSales(ticketSales)) {
            throw new IllegalValueException(MESSAGE_TICKETSALES_CONSTRAINTS);
        }
        this.value = ticketSales;
    }

    /**
     * Checks if a given string is a valid ticket sales amount.
     */
    public static boolean isValidTicketSales(String test) {
        return test.matches(TICKETSALES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TicketSales // instanceof handles nulls
                && this.value.equals(((TicketSales) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
