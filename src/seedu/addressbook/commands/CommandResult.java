package seedu.addressbook.commands;

import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.List;
import java.util.Optional;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** The feedback message to be shown to the user. Contains a description of the execution result */
    public final String feedbackToUser;

    /** The list of persons that was produced by the command */
    private final List<? extends ReadOnlyPerson> relevantPersons;

    /** The list of matches that was produced by the command */
    private final List<? extends ReadOnlyMatch> relevantMatches;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        relevantPersons = null;
        relevantMatches = null;
    }

    public CommandResult(String feedbackToUser, List<? extends ReadOnlyPerson> relevantPersons,
                         List<? extends ReadOnlyMatch> relevantMatches) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
        this.relevantMatches = relevantMatches;
    }

    /**
     * Returns list of persons relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyPerson>> getRelevantPersons() {
        return Optional.ofNullable(relevantPersons);
    }

    /**
     * Returns list of matches relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyMatch>> getRelevantMatches() {
        return Optional.ofNullable(relevantMatches);
    }

}
