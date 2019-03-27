package seedu.addressbook.commands;

import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.team.ReadOnlyTeam;

import java.util.List;
import java.util.Optional;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /** The feedback message to be shown to the user. Contains a description of the execution result */
    public final String feedbackToUser;

    /** The list of players that was produced by the command */
    private final List<? extends ReadOnlyPlayer> relevantPlayers;

    /** The list of teams that was produced by the command */
    private final List<? extends ReadOnlyTeam> relevantTeams;

    /** The list of matches that was produced by the command */
    private final List<? extends ReadOnlyMatch> relevantMatches;

    /** The list of finances that was produced by the command */
    private final List<? extends ReadOnlyFinance> relevantFinances;

    /** Constructor for result which do not return any list*/
    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        relevantPlayers = null;
        relevantTeams = null;
        relevantMatches = null;
        relevantFinances = null;
    }

    public CommandResult(String feedbackToUser,
                         List<? extends ReadOnlyPlayer> relevantPlayers,
                         List<? extends ReadOnlyTeam> relevantTeams,
                         List<? extends ReadOnlyMatch> relevantMatches,
                         List<? extends ReadOnlyFinance> relevantFinances) {
        this.feedbackToUser = feedbackToUser;
        this.relevantPlayers = relevantPlayers;
        this.relevantTeams = relevantTeams;
        this.relevantMatches = relevantMatches;
        this.relevantFinances = relevantFinances;
    }


    /**
     * Returns list of persons relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyPlayer>> getRelevantPlayers() {
        return Optional.ofNullable(relevantPlayers);
    }

    /**
     * Returns list of matches relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyMatch>> getRelevantMatches() {
        return Optional.ofNullable(relevantMatches);
    }

    /**
     * Returns list of teams relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyTeam>> getRelevantTeams() {
        return Optional.ofNullable(relevantTeams);
    }

    /**
     * Returns list of finances relevant to the command command result, if any.
     */
    public Optional<List<? extends ReadOnlyFinance>> getRelevantFinances() {
        return Optional.ofNullable(relevantFinances);
    }

}
