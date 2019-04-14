package seedu.addressbook.parser;

import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.commands.*;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.person.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ParserTest {

    private Parser parser;

    @Before
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void emptyInput_returnsIncorrect() {
        final String[] emptyInputs = { "", "  ", "\n  \n" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, emptyInputs);
    }

    @Test
    public void unknownCommandWord_returnsHelp() {
        final String input = "unknowncommandword arguments arguments";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    /**
     * Test 0-argument commands
     */
    
    @Test
    public void helpCommand_parsedCorrectly() {
        final String input = "help";
        parseAndAssertCommandType(input, HelpCommand.class);
    }
    
    @Test
    public void clearCommand_parsedCorrectly() {
        final String input = "clear";
        parseAndAssertCommandType(input, ClearCommand.class);
    }

    @Test
    public void listCommand_parsedCorrectly() {
        final String input = "list";
        parseAndAssertCommandType(input, ListCommand.class);
    }



    @Test
    public void exitCommand_parsedCorrectly() {
        final String input = "exit";
        parseAndAssertCommandType(input, ExitCommand.class);
    }

    /**
     * Test ingle index argument commands
     */
    
    @Test
    public void deleteCommand_noArgs() {
        final String[] inputs = { "delete", "delete " };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "delete notAnumber ", "delete 8*wh12", "delete 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }
    
    @Test
    public void deleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "delete " + testIndex;
        final DeleteCommand result = parseAndAssertCommandType(input, DeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewCommand_noArgs() {
        final String[] inputs = { "view", "view " };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "view notAnumber ", "view 8*wh12", "view 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }
    
    @Test
    public void viewCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 2;
        final String input = "view " + testIndex;
        final ViewCommand result = parseAndAssertCommandType(input, ViewCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewAllCommand_noArgs() {
        final String[] inputs = { "viewall", "viewall " };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "viewall notAnumber ", "viewall 8*wh12", "viewall 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewall " + testIndex;
        final ViewAllCommand result = parseAndAssertCommandType(input, ViewAllCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    /**
     * Test find persons by keyword in name command
     */

    @Test
    public void findCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {
                "find",
                "find "
        };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void findCommand_validArgs_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "find " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void findCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "find " + String.join(" ", keySet) + " " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    /**
     * Test add person command
     */
    
    @Test
    public void addCommand_invalidArgs() {
        final String[] inputs = {
                "add",
                "add ",
                "add wrong args format",
                // no phone prefix
                String.format("add $s $s e/$s a/$s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE),
                // no email prefix
                String.format("add $s p/$s $s a/$s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE),
                // no address prefix
                String.format("add $s p/$s e/$s $s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE)
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void addCommand_invalidPersonDataInArgs() {
        final String invalidName = "[]\\[;]";
        final String validName = Name.EXAMPLE;
        final String invalidPhoneArg = "p/not__numbers";
        final String validPhoneArg = "p/" + Phone.EXAMPLE;
        final String invalidEmailArg = "e/notAnEmail123";
        final String validEmailArg = "e/" + Email.EXAMPLE;
        final String invalidTagArg = "t/invalid_-[.tag";

        // address can be any string, so no invalid address
        final String addCommandFormatString = "add $s $s $s a/" + Address.EXAMPLE;

        // test each incorrect person data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addCommandFormatString, invalidName, validPhoneArg, validEmailArg),
                // invalid phone
                String.format(addCommandFormatString, validName, invalidPhoneArg, validEmailArg),
                // invalid email
                String.format(addCommandFormatString, validName, validPhoneArg, invalidEmailArg),
                // invalid tag
                String.format(addCommandFormatString, validName, validPhoneArg, validEmailArg) + " " + invalidTagArg
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void addCommand_validPersonData_parsedCorrectly() {
        final Person testPerson = generateTestPerson();
        final String input = convertPersonToAddCommandString(testPerson);
        final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
        assertEquals(result.getPerson(), testPerson);
    }

    @Test
    public void addCommand_duplicateTags_merged() throws IllegalValueException {
        final Person testPerson = generateTestPerson();
        String input = convertPersonToAddCommandString(testPerson);
        for (Tag tag : testPerson.getTags()) {
            // create duplicates by doubling each tag
            input += " t/" + tag.tagName;
        }

        final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
        assertEquals(result.getPerson(), testPerson);
    }

    private static Person generateTestPerson() {
        try {
            return new Person(
                new Name(Name.EXAMPLE),
                new Phone(Phone.EXAMPLE, true),
                new Email(Email.EXAMPLE, false),
                new Address(Address.EXAMPLE, true),
                new Appointment(Appointment.EXAMPLE),
                new Doctor(Doctor.EXAMPLE),
                new Status(Status.EXAMPLE),
                new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2"), new Tag("tag3")))
            );
        } catch (IllegalValueException ive) {
            throw new RuntimeException("test person data should be valid by definition");
        }
    }

    private static String convertPersonToAddCommandString(ReadOnlyPerson person) {
        String addCommand = "add "
                + person.getName().fullName
                + (person.getPhone().isPrivate() ? " pp/" : " p/") + person.getPhone().value
                + (person.getEmail().isPrivate() ? " pe/" : " e/") + person.getEmail().value
                + (person.getAddress().isPrivate() ? " pa/" : " a/") + person.getAddress().value
                +("m/")+person.getAppointment().appointmentDate
                +("d/")+person.getDoctor().doctorName
                +("s/")+person.getStatus().status;
        for (Tag tag : person.getTags()) {
            addCommand += " t/" + tag.tagName;
        }
        return addCommand;
    }

    //@@author WuPeiHsuan
    /**
     * Test sort command
     */

    @Test
    public void sortCommand_parsedCorrectly() {
        final String input = "sort name";
        parseAndAssertCommandType(input, SortCommand.class);
    }

    @Test
    public void sortCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {
                "sort",
                "sort address",
                "sort 123",
                "sort Appointment"
        };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    /**
     * Test invalid status
     */
    @Test
    public void addCommand_invalidStatus() {
        final String validName = Name.EXAMPLE;
        final String validPhoneArg = "p/" + Phone.EXAMPLE;
        final String validEmailArg = "e/" + Email.EXAMPLE;
        final String anyAddress = "a/" + Address.EXAMPLE;
        final String addCommandFormatString = "add $s $s $s $s $s $s $s";
        final String validAppointmentArg = "m/" + Appointment.EXAMPLE;
        final String validDoctorArg = "p/" + Doctor.EXAMPLE;
        final String invalidStatus1 = "s/";
        final String invalidStatus2 = "s/abc";
        final String invalidStatus3 = "s/critical";

        // test each incorrect person data field argument individually
        final String[] inputs = {
                // invalid status 1
                String.format(addCommandFormatString, validName, validPhoneArg, validEmailArg, anyAddress, validAppointmentArg, validDoctorArg, invalidStatus1),
                // invalid status 2
                String.format(addCommandFormatString, validName, validPhoneArg, validEmailArg, anyAddress, validAppointmentArg, validDoctorArg, invalidStatus2),
                // invalid status 3
                String.format(addCommandFormatString, validName, validPhoneArg, validEmailArg, anyAddress, validAppointmentArg, validDoctorArg, invalidStatus3)

        };

        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }

    }

    //@@author
    /**
     * Test refer person command
     */
    // @@author shawn-t
    @Test
    public void referCommand_invalidArgs() {
        final String[] inputs = {
                "refer",
                "refer "
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReferCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }
    //@@author

    //@@author matthiaslum
    @Test
    public void appointmentCommand_invalidArgs() {
        final String[] inputs = {
                "appointment",
                "appointment  "
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoctorAppointmentsCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void apptDateCommand_invalidArgs() {
        final String[] inputs = {
                "apptDate validname r23r2",
                "apptDate  ",
                "apptDate",
                "apptDate NODATE",

        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApptDateCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }
    //@@author

    //@@author shawn-t
    @Test
    public void referCommand_invalidDoctorNameInArgs() {
        final String[] inputs = {
                // invalid doctor names
                "refer d/Dr. p/John Doe",
                "refer d/Dr, p/John Doe",
                "refer d/Dr! p/John Doe",
                "refer d/Dr@ p/John Doe",
                "refer d/#@! p/John Doe"
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReferCommand.MESSAGE_INVALID_DOCTOR_NAME);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }
    //@@author

    //@@author matthiaslum
    @Test
    public void appointmentCommand_invalidDoctorNameInArgs() {
        final String[] inputs = {
                // invalid doctor names
                "appointment Dr.",
                "appointment Dr,",
                "appointment Dr!",
                "appointment Dr@",
                "appointment #@!"
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoctorAppointmentsCommand.MESSAGE_INVALID_DOCTOR_NAME);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void apptDateCommand_invalidDoctorNameInArgs() {
        final String[] inputs = {
                // invalid doctor names
                "apptDate Dr. m/2020 11 12",
                "apptDate Dr, m/2020 11 12",
                "apptDate Dr! m/2020 11 12",
                "apptDate Dr@ m/2020 11 12",
                "apptDate #@! m/2020 11 12"
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApptDateCommand.MESSAGE_INVALID_DOCTOR_NAME);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void apptDateCommand_invalidDateInArgs() {
        final String[] inputs = {
                // invalid doctor names
                "apptDate Dr m/2020 11 12 21",
                "apptDate Dr m/2020 100 22",
                "apptDate Dr m/30Nov",
                "apptDate Dr m/223232322323232233223",
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApptDateCommand.MESSAGE_DATE_CONSTRAINTS);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }
    //@@author

//    @Test
//    public void referCommand_validArgs_parsedCorrectly() {
//        final String[] keywords = { "key1", "key2", "key3" };
//        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));
//
//        final String input = "refer " + String.join(" ", keySet);
//        final ReferCommand result =
//                parseAndAssertCommandType(input, ReferCommand.class);
//        assertEquals(keySet, result.getKeywords());
//    }
//
//    @Test
//    public void referCommand_duplicateKeys_parsedCorrectly() {
//        final String[] keywords = { "key1", "key2", "key3" };
//        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));
//
//        // duplicate every keyword
//        final String input = "refer " + String.join(" ", keySet) + " " + String.join(" ", keySet);
//        final ReferCommand result =
//                parseAndAssertCommandType(input, ReferCommand.class);
//        assertEquals(keySet, result.getKeywords());
//    }
//
//    @Test
//    public void referCommand_validPersonData_parsedCorrectly() {
//        final String referArgsString = generateTestRefer();
//        final ReferCommand result1 = parseAndAssertCommandType(referArgsString, ReferCommand.class);
//        assertEquals(result1, referArgsString);
//
//        final String referArgsStringWithDoctorName = generateTestReferWithDoctorName();
//        final ReferCommand result2 = parseAndAssertCommandType(referArgsStringWithDoctorName, ReferCommand.class);
//        assertEquals(result2, referArgsStringWithDoctorName);
//
//    }
//
//    private static String generateTestRefer() {
//        try {
//            return "refer " + new Name(Name.EXAMPLE).toString();
//        } catch (IllegalValueException ive) {
//            throw new RuntimeException("test person data should be valid by definition");
//        }
//    }
//
//    private static String generateTestReferWithDoctorName() {
//        try {
//            return "refer d/" + new Doctor(Doctor.EXAMPLE).toString() + " p/" + new Name(Name.EXAMPLE).toString();
//        } catch (IllegalValueException ive) {
//            throw new RuntimeException("test person data should be valid by definition");
//        }
//    }

    /**
     * Utility methods
     */

    /**
     * Asserts that parsing the given inputs will return IncorrectCommand with the given feedback message.
     */
    private void parseAndAssertIncorrectWithMessage(String feedbackMessage, String... inputs) {
        for (String input : inputs) {
            final IncorrectCommand result = parseAndAssertCommandType(input, IncorrectCommand.class);
            assertEquals(result.feedbackToUser, feedbackMessage);
        }
    }

    /**
     * Utility method for parsing input and asserting the class/type of the returned command object.
     *
     * @param input to be parsed
     * @param expectedCommandClass expected class of returned command
     * @return the parsed command object
     */
    private <T extends Command> T parseAndAssertCommandType(String input, Class<T> expectedCommandClass) {
        final Command result = parser.parseCommand(input);
        assertTrue(result.getClass().isAssignableFrom(expectedCommandClass));
        return (T) result;
    }
}
