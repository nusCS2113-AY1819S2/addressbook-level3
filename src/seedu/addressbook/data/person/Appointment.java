//@@author matthiaslum

package seedu.addressbook.data.person;

import seedu.addressbook.data.exception.IllegalValueException;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class Appointment implements Comparable<Appointment>{

    public static final String EXAMPLE = "2020 11 10 08 00";
    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS = "Appointment timings must not be earlier than current time, must be in the 24hr format of yyyy MM dd hh mm. Additionally appointment slots are in blocks of 15min. Thus, the time in minutes must be 00, 15, 30 or 45. No appointments earlier than 6am.";
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    public static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy MM dd kk mm");

    public final String appointmentDate;

    /**
     * Validates given appointment.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public Appointment(String date) throws IllegalValueException {
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_APPOINTMENT_CONSTRAINTS);
        }
        this.appointmentDate = date;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDate(String test) {
        Boolean indicator = true;
        try{
            LocalDateTime time = LocalDateTime.parse(test, formatterTime);
            if (time.getHour() <6 | time.getMinute() %15 != 0 | time.compareTo(LocalDateTime.now()) <0 ){
                indicator = false;
            }
        }
        catch (DateTimeParseException e){
            indicator = false;
        }
        return (test.matches(NAME_VALIDATION_REGEX) & indicator);
    }

    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInDate() {
        return Arrays.asList(appointmentDate.split("\\s+"));
    }


    @Override
    public String toString() {
        return appointmentDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.appointmentDate.equals(((Appointment) other).appointmentDate)); // state check
    }

    @Override
    public int hashCode() {
        return appointmentDate.hashCode();
    }
    //@@author

    //@@author WuPeiHsuan
    /**compare name for sorting function*/
    @Override
    public int compareTo(Appointment appointment) {
        return this.appointmentDate.compareTo(appointment.appointmentDate);
    }
    //@@author
}