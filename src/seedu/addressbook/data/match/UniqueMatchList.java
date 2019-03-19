package seedu.addressbook.data.match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

/**
 * A list of matches. Does not allow null elements or duplicates.
 */
public class UniqueMatchList implements Iterable<Match> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateMatchException extends DuplicateDataException {
        protected DuplicateMatchException() {
            super("Operation would result in duplicate matches");
        }
    }

    /**
     * Signals that an operation targeting a specified match in the list would fail because
     * there is no such matching match in the list.
     */
    public static class MatchNotFoundException extends Exception {}

    private final List<Match> internalList = new ArrayList<>();

    /**
     * Constructs empty match list.
     */
    public UniqueMatchList() {}

    /**
     * Constructs a match list with the given matches.
     */
    public UniqueMatchList(Match... matches) throws DuplicateMatchException {
        final List<Match> initialTags = Arrays.asList(matches);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicateMatchException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param matches a collection of matches
     * @throws DuplicateMatchException if the {@code matches} contains duplicate matches
     */
    public UniqueMatchList(Collection<Match> matches) throws DuplicateMatchException {
        if (!Utils.elementsAreUnique(matches)) {
            throw new DuplicateMatchException();
        }
        internalList.addAll(matches);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueMatchList(UniqueMatchList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyMatch}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyMatch> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }


    /**
     * Checks if the list contains an equivalent match as the given argument.
     */
    public boolean contains(ReadOnlyMatch toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a match to the list.
     *
     * @throws DuplicateMatchException if the match to add is a duplicate of an existing match in the list.
     */
    public void add(Match toAdd) throws DuplicateMatchException {
        if (contains(toAdd)) {
            throw new DuplicateMatchException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent match from the list.
     *
     * @throws MatchNotFoundException if no such match could be found in the list.
     */
    public void remove(ReadOnlyMatch toRemove) throws MatchNotFoundException {
        final boolean matchFoundAndDeleted = internalList.remove(toRemove);
        if (!matchFoundAndDeleted) {
            throw new MatchNotFoundException();
        }
    }

    /**
     * Clears all matches in list.
     */
    public void clear() {
        internalList.clear();
    }

    @Override
    public Iterator<Match> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMatchList // instanceof handles nulls
                && this.internalList.equals(((UniqueMatchList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
