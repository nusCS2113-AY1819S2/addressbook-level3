package seedu.addressbook.parser;

import seedu.addressbook.commands.*;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Appointment;
import seedu.addressbook.data.person.Doctor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses user input.
 */
public class Parser {

    public static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    public static final Pattern PERSON_REFER_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("d/(?<doctor>[^/]+)"
                    + "p/(?<keywords>\\S+(?:\\s+\\S+)*)");
//            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"
//                    + "d/(?<doctor>[^/]+)");

    public static final Pattern PERSON_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^/]+)"
                    + " (?<isPhonePrivate>p?)p/(?<phone>[^/]+)"
                    + " (?<isEmailPrivate>p?)e/(?<email>[^/]+)"
                    + " (?<isAddressPrivate>p?)a/(?<address>[^/]+)"
                    + "m/(?<appointment>[^/]+)"
                    + "d/(?<doctor>[^/]+)"
                    + "s/(?<status>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    public static final Pattern APPT_DATE_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+)"
                    + "m/(?<date>[^/]+)");

    public static final Pattern DOCTORS_APPOINTMENT_ARGS_FORMAT =
            Pattern.compile("(?<name>[^/]+)");

    /**
     * Signals that the user input could not be parsed.
     */
    public static class ParseException extends Exception {
        ParseException(String message) {
            super(message);
        }
    }

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return prepareAdd(arguments);

            case DeleteCommand.COMMAND_WORD:
                return prepareDelete(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return prepareFind(arguments);

            case DoctorAppointmentsCommand.COMMAND_WORD:
                return prepareFindDoctor(arguments);

            case LengthCommand.COMMAND_WORD:
                return new LengthCommand();

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case ReferCommand.COMMAND_WORD:
                return prepareRefer(arguments);

            case ViewCommand.COMMAND_WORD:
                return prepareView(arguments);

            case ViewAllCommand.COMMAND_WORD:
                return prepareViewAll(arguments);

            case SortCommand.COMMAND_WORD:
                return prepareSort(arguments);

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case ApptDateCommand.COMMAND_WORD:
                return  prepareAppt(arguments);

            case HelpCommand.COMMAND_WORD:// Fallthrough
            default:
                return new HelpCommand();
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author matthiaslum
    private Command prepareAppt(String args){
        final Matcher matcher = APPT_DATE_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches())
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ApptDateCommand.MESSAGE_USAGE));
        String doctor = matcher.group("name");
        String appointment = matcher.group("date");
        if (!Doctor.isValidName(doctor)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ApptDateCommand.MESSAGE_INVALID_DOCTOR_NAME));        }
        if (!ApptDateCommand.isValidDate(appointment)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ApptDateCommand.MESSAGE_DATE_CONSTRAINTS));        }
        return new ApptDateCommand(
                matcher.group("name"),
                matcher.group("date")
        );
    }

    private Command prepareFindDoctor(String args) {
        final Matcher matcher = DOCTORS_APPOINTMENT_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DoctorAppointmentsCommand.MESSAGE_USAGE));
        }
        final String doctorName = matcher.group("name").trim();
        if (!Doctor.isValidName(doctorName)){
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DoctorAppointmentsCommand.MESSAGE_INVALID_DOCTOR_NAME));        }
        return new DoctorAppointmentsCommand(doctorName);
    }
    //@@author

    private Command prepareAdd(String args){
        final Matcher matcher = PERSON_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("name"),

                    matcher.group("phone"),
                    isPrivatePrefixPresent(matcher.group("isPhonePrivate")),

                    matcher.group("email"),
                    isPrivatePrefixPresent(matcher.group("isEmailPrivate")),

                    matcher.group("address"),
                    isPrivatePrefixPresent(matcher.group("isAddressPrivate")),

                    matcher.group("appointment"),

                    matcher.group("doctor"),

                    matcher.group("status"),

                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }
    //@@author WuPeiHsuan
    private Command prepareSort(String args) {
        args = args.trim();
        switch (args) {

            case "name":

            case "appointment":

            case "status":
                return new SortCommand(args.trim());

            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
    //@@author

    /**
     * Checks whether the private prefix of a contact detail in the add command's arguments string is present.
     */
    private static boolean isPrivatePrefixPresent(String matchedPrefix) {
        return matchedPrefix.equals("p");
    }

    /**
     * Extracts the new person's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }


    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new DeleteCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the view command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String args) {

        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new ViewCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses arguments in the context of the view all command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareViewAll(String args) {

        try {
            final int targetIndex = parseArgsAsDisplayedIndex(args);
            return new ViewAllCommand(targetIndex);
        } catch (ParseException | NumberFormatException e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAllCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given arguments string as a single index number.
     *
     * @param args arguments string to parse as index number
     * @return the parsed index number
     * @throws ParseException if no region of the args string could be found for the index
     * @throws NumberFormatException the args string region is not a valid number
     */
    private int parseArgsAsDisplayedIndex(String args) throws ParseException, NumberFormatException {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException("Could not find index number to parse");
        }
        return Integer.parseInt(matcher.group("targetIndex"));
    }

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }


    /**
     * Parses arguments in the context of the refer patient command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author shawn-t
    private Command prepareRefer(String args) {
        final Matcher matcherWithDoctorName = PERSON_REFER_ARGS_FORMAT.matcher(args.trim());

        // Validate arg string format
        if (!matcherWithDoctorName.matches()) { // if doctor name is not present,

            final Matcher matcherWithOnlyKeywords = KEYWORDS_ARGS_FORMAT.matcher(args.trim());

            if (!matcherWithOnlyKeywords.matches()) { // and keywords are not in the correct form,
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ReferCommand.MESSAGE_USAGE));
            }
            // if keywords are in the correct form
            // keywords delimited by whitespace
            final String[] keywords = matcherWithOnlyKeywords.group("keywords").split("\\s+");
            final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
            return new ReferCommand(keywordSet);
        }

        // if doctor name is present,
        String doctorToReferTo = matcherWithDoctorName.group("doctor");
        doctorToReferTo = doctorToReferTo.trim();
        if (!Doctor.isValidName(doctorToReferTo)) { // if doctor name is invalid,
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReferCommand.MESSAGE_INVALID_DOCTOR_NAME));
        }
        return new ReferCommand(
                matcherWithDoctorName.group("keywords"),
                matcherWithDoctorName.group("doctor")
        );

    }

}


