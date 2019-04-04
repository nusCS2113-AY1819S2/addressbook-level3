package seedu.addressbook.data.finance;

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
public class UniqueFinanceList implements Iterable<Finance> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateFinanceException extends DuplicateDataException {
        protected DuplicateFinanceException() {
            super("Operation would result in duplicate Finance");
        }
    }

    /**
     * Signals that an operation targeting a specified finance in the list would fail because
     * there is no such matching finance in the list.
     */
    public static class FinanceNotFoundException extends Exception {}

    private final List<Finance> internalList = new ArrayList<>();

    /**
     * Constructs empty finance list.
     */
    public UniqueFinanceList() {}

    /**
     * Constructs a finance list with the given Finances.
     */
    public UniqueFinanceList(Finance...finances) throws UniqueFinanceList.DuplicateFinanceException {
        final List<Finance> initialTags = Arrays.asList(finances);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new UniqueFinanceList.DuplicateFinanceException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     */
    public UniqueFinanceList(Collection<Finance> finances) throws UniqueFinanceList.DuplicateFinanceException {
        if (!Utils.elementsAreUnique(finances)) {
            throw new UniqueFinanceList.DuplicateFinanceException();
        }
        internalList.addAll(finances);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueFinanceList(UniqueFinanceList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyFinance> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }

    /**
     * Checks if the list contains an equivalent finance as the given argument.
     */
    public boolean contains(ReadOnlyFinance toCheck) {
        return internalList.contains(toCheck);
    }


    /**
     * Adds a finance to the list.
    */
    public void add(Finance toAdd) throws UniqueFinanceList.DuplicateFinanceException {
        if (contains(toAdd)) {
            throw new UniqueFinanceList.DuplicateFinanceException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent finance from the list.
     */
    public void remove(ReadOnlyFinance toRemove) throws UniqueFinanceList.FinanceNotFoundException {
        final boolean financeFoundAndDeleted = internalList.remove(toRemove);
        if (!financeFoundAndDeleted) {
            throw new UniqueFinanceList.FinanceNotFoundException();
        }
    }

    /**
     * Clears all Finances in list.
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Sort all Finances in list by (to be edited)
     */

    /*
    public void sort() {
        Comparator<Finance> customTeamCompare = Comparator.comparing(TeamName::getTeamName);
        Collections.sort(internalList, customTeamCompare);
    }
    */

    /**
     * Removes the equivalent finance from the list.
     */
    public void edit(ReadOnlyFinance toRemove, Finance toReplace) throws UniqueFinanceList.FinanceNotFoundException {
        final boolean financeFoundAndDeleted = internalList.remove(toRemove);
        if (!financeFoundAndDeleted) {
            throw new UniqueFinanceList.FinanceNotFoundException();
        }
        internalList.add(toReplace);
    }

    @Override
    public Iterator<Finance> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFinanceList // instanceof handles nulls
                && this.internalList.equals(((UniqueFinanceList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
