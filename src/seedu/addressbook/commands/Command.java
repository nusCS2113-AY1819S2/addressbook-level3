package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;

import static seedu.addressbook.ui.Gui.DISPLAYED_INDEX_OFFSET;

/**
 * Represents an executable command.
 */
public abstract class Command {
    protected AddressBook addressBook;
    protected List<? extends ReadOnlyPerson> relevantPersons;
    private int targetIndex = -1;

    /**
     * @param targetIndex last visible listing index of the target person
     */
    public Command(int targetIndex) {
        this.setTargetIndex(targetIndex);
    }

    protected Command() {
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param personsDisplayed used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, personsDisplayed.size());
    }

    public static String getMessageForPersonReferShownSummary(List<? extends ReadOnlyPerson> personsDisplayed) {
        return String.format(Messages.MESSAGE_SELECT_PATIENT, personsDisplayed.size());
    }

    public static String getMessageForAppointmentsShownSummary(List<? extends ReadOnlyPerson> personsDisplayed, String doctor) {
        return String.format(Messages.MESSAGE_NUMBER_OF_APPOINTMENTS, doctor, personsDisplayed.size());
    }

    //@@author WuPeiHsuan
    public static String getMessageForPersonSortShownSummary(List<? extends ReadOnlyPerson> personsDisplayed, String column) {
        column = column.trim();
        switch(column){
            case "name":
                return String.format(Messages.MESSAGE_SORT_NAME_OVERVIEW, personsDisplayed.size(), column);
            case "appointment":
                return String.format(Messages.MESSAGE_SORT_APPOINTMENT_OVERVIEW, personsDisplayed.size(), column);

        }
        return String.format(Messages.MESSAGE_SORT_STATUS_OVERVIEW, personsDisplayed.size(), column);
        //return String.format(Messages.MESSAGE_PERSON_SORTED_OVERVIEW, personsDisplayed.size(), column);
    }
    //@@author
    /**
     * Executes the command and returns the result.
     */
    public CommandResult execute() throws IllegalValueException {
        throw new UnsupportedOperationException("This method should be implement in child classes");
    }

    //Note: it is better to make the execute() method abstract, by replacing the above method with the line below:
    //public abstract CommandResult execute();

    /**
     * Supplies the data the command will operate on.
     */
    public void setData(AddressBook addressBook, List<? extends ReadOnlyPerson> relevantPersons) {
        this.addressBook = addressBook; //passes a reference to the same object, so editing that object will store it directly.
        this.relevantPersons = relevantPersons;
    }

    /**
     * Extracts the the target person in the last shown list from the given arguments.
     *
     * @throws IndexOutOfBoundsException if the target index is out of bounds of the last viewed listing
     */
    protected ReadOnlyPerson getTargetPerson() throws IndexOutOfBoundsException {
        return relevantPersons.get(getTargetIndex() - DISPLAYED_INDEX_OFFSET);
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }
}
