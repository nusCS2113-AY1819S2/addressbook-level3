package seedu.addressbook.data.match;

public class Score {

    public final String fullScore;

    /**
     * Constructs given score
     * @param score
     */
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
