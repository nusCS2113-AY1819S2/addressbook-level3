package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's Position on the field.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class PositionPlayed {

    public static final String EXAMPLE = "Midfielder";
    public static final String MESSAGE_POSITIONPLAYED_CONSTRAINTS = "Position of a player"
            + "must be spaces or alphanumeric characters";
    public static final String POSITIONPLAYED_VALIDATION_REGEX = ".+";

    public final String fullPosition;

    /**
     * Validates given position.
     *
     * @throws IllegalValueException if given position string is invalid.
     */

    public PositionPlayed (String position) throws IllegalValueException {
        if (!isValidPosition(position)) {
            throw new IllegalValueException(MESSAGE_POSITIONPLAYED_CONSTRAINTS);
        }
        this.fullPosition = position;
    }

    /**
     * Returns true if a given string is a valid position.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(POSITIONPLAYED_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return fullPosition;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PositionPlayed // instanceof handles nulls
                && this.fullPosition.equals(((PositionPlayed) other).fullPosition)); // state check
    }

    @Override
    public int hashCode() {
        return fullPosition.hashCode();
    }

}

