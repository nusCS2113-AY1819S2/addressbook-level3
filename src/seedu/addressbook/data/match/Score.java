package seedu.addressbook.data.match;

import seedu.addressbook.data.exception.IllegalValueException;

import java.util.Arrays;
import java.util.List;

public class Score {

    public final String fullScore;

    public Score(String score) {
        this.fullScore = score;
    }

    @Override
    public String toString() {
        return fullScore;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Score // instanceof handles nulls
                && this.fullScore.equals(((Score) other).fullScore)); // state check
    }

    @Override
    public int hashCode() {
        return fullScore.hashCode();
    }

}