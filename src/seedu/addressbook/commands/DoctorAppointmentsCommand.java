//@@author matthiaslum
package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;

public class DoctorAppointmentsCommand extends Command {

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM d kk mm");
    public static final String COMMAND_WORD = "appointment";
    public static final String MESSAGE_INVALID_DOCTOR_NAME = "Doctor's names should only contain spaces and/or alphanumeric characters\nSpecial characters like . ! @ # , etc are not allowed!\nPlease re-enter with an appropriate doctor name.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Displays a list of all patients who have appointments with a certain doctor, in chronological order of appointments."
            + "Past appointments are not shown (based on current time).\n\t"
            + "Parameters: DOCTOR_NAME...\n\t"
            + "Example: " + COMMAND_WORD + " DoctorTan";

    private final String doctorName;

    public DoctorAppointmentsCommand(String doctorName) {
        this.doctorName = doctorName.trim();
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(doctorName);
        Indicator.setLastCommand("DoctorAppointments");
        return new CommandResult(getMessageForAppointmentsShownSummary(personsFound, doctorName), personsFound);
    }

    /**
     * Retrieve all patients in the address book whose doctor's name is the same.
     *
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(String doctorName) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final String doctor = person.getDoctor().toString();
            if (doctorName.equals(doctor)) {
                LocalDateTime date = LocalDateTime.parse(person.getAppointment().toString(), formatter);
                person.setLocalDateTime(date);
                if (date.compareTo(LocalDateTime.now()) > 0){
                    matchedPersons.add(person);
                }
            }
        }
        Collections.sort(matchedPersons, new SortDate());
        return matchedPersons;
    }

}
//@@author