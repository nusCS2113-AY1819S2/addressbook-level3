package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.team.ReadOnlyTeam;

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
    /** The list of teams that was produced by the command */
    private final List<? extends ReadOnlyTeam> relevantTeams;

    /** Constructor for result which do not return any list*/
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        relevantPersons = null;
        relevantTeams = null;
    }

    public CommandResult(String feedbackToUser,
                         List<? extends ReadOnlyPerson> relevantPersons,
                         List<? extends ReadOnlyTeam> relevantTeams) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPersons = relevantPersons;
        this.relevantTeams = relevantTeams;
    }


    /**
     * Returns list of persons relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyPerson>> getRelevantPersons() {
        return Optional.ofNullable(relevantPersons);
    }

    /**
     * Returns list of teams relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyTeam>> getRelevantTeams() {
        return Optional.ofNullable(relevantTeams);
    }

}
