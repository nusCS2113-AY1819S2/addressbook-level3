package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a player's Position on the field.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class PositionPlayed {

    public static final String example = "Midfielder";
    public static final String MESSAGE_POSITIONPLAYED_CONSTRAINTS = "Position of a player must be spaces or alphanumeric characters";
    public static final String PositionPlayed_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullPosition;
    private boolean isPrivate;
    /**
     * Validates given position.
     *
     * @throws IllegalValueException if given position string is invalid.
     */

    public PositionPlayed (String position,boolean isPrivate) throws IllegalValueException {
        this.isPrivate = isPrivate;

        if (!isValidPosition(position)) {
            throw new IllegalValueException(MESSAGE_POSITIONPLAYED_CONSTRAINTS);
        }
        this.fullPosition = position;
    }

    /**
     * Returns true if a given string is a valid position.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(PositionPlayed_VALIDATION_REGEX);
    }
    public boolean isPrivate() {
        return isPrivate;
    }

}

