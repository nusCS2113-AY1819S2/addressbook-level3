package seedu.addressbook.data.player;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's team in the address book.
 */
public class Team {
    public static final String EXAMPLE = "FC Barcelona";
    public static final String MESSAGE_TEAM_CONSTRAINTS = "Player's team names"
            + "can be anything.+";
    public static final String TEAM_VALIDATION_REGEX = ".+";

    public final String fullTeam;

    /**
     * Validates given team name.
     *
     * @throws IllegalValueException if given team name string is invalid.
     */
    public Team(String name) throws IllegalValueException {
        name = name.trim();
        if (!isValidTeam(name)) {
            throw new IllegalValueException(MESSAGE_TEAM_CONSTRAINTS);
        }
        this.fullTeam = name;
    }

    /**
     * Returns true if a given string is a valid team name.
     */
    public static boolean isValidTeam(String test) {
        return test.matches(TEAM_VALIDATION_REGEX);
    }

    /**
     * Retrieves a listing of every word in the team name, in order.
     */
    public List<String> getWordsInTeam() {
        return Arrays.asList(fullTeam.split("\\s+"));
    }

    @Override
    public String toString() {
        return fullTeam;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Team // instanceof handles nulls
                && this.fullTeam.equals(((Team) other).fullTeam)); // state check
    }

    @Override
    public int hashCode() {
        return fullTeam.hashCode();
    }

}
