package seedu.addressbook.data.team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.DuplicateDataException;

/**
 * A list of Teams. Does not allow null elements or duplicates.
 */

public class UniqueTeamList implements Iterable<Team> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTeamException extends DuplicateDataException {
        protected DuplicateTeamException() {
            super("Operation would result in duplicate Teams");
        }
    }

    /**
     * Signals that an operation targeting a specified team in the list would fail because
     * there is no such matching team in the list.
     */
    public static class TeamNotFoundException extends Exception {}

    private final List<Team> internalList = new ArrayList<>();

    /**
     * Constructs empty team list.
     */
    public UniqueTeamList() {}

    /**
     * Constructs a team list with the given Teams.
     */
    public UniqueTeamList(Team...teams) throws DuplicateTeamException {
        final List<Team> initialTags = Arrays.asList(teams);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicateTeamException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     */
    public UniqueTeamList(Collection<Team> teams) throws DuplicateTeamException {
        if (!Utils.elementsAreUnique(teams)) {
            throw new DuplicateTeamException();
        }
        internalList.addAll(teams);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public UniqueTeamList(UniqueTeamList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */
    public List<ReadOnlyTeam> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }

    /**
     * Checks if the list contains an equivalent team as the given argument.
     */
    public boolean contains(ReadOnlyTeam toCheck) {
        String nameToCheck = toCheck.getTeamName().toString().toLowerCase();
        for (ReadOnlyTeam employee: internalList) {
            String employeeName = employee.getTeamName().toString().toLowerCase();
            if (employeeName.equals(nameToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a team to the list.
     */
    public void add(Team toAdd) throws DuplicateTeamException {
        if (contains(toAdd)) {
            throw new DuplicateTeamException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent team from the list.
     */
    public void remove(ReadOnlyTeam toRemove) throws TeamNotFoundException {
        final boolean teamFoundAndDeleted = internalList.remove(toRemove);
        if (!teamFoundAndDeleted) {
            throw new TeamNotFoundException();
        }
    }

    /**
     * Clears all Teams in list.
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Sort all Teams in list by ascending alphabetical order.
     */
    public void sort() {
        Comparator<Team> customTeamCompare = Comparator
                .comparing(Team::getPoints).reversed()
                .thenComparing(Team::getLoses)
                .thenComparing(Team::getTeamName);
        Collections.sort(internalList, customTeamCompare);
    }

    /**
     * Removes the equivalent team from the list.
     */
    public void edit(ReadOnlyTeam toRemove,
                     Team toReplace,
                     boolean namechange) throws TeamNotFoundException, DuplicateTeamException {
        if (contains(toReplace) && namechange) {
            throw new DuplicateTeamException();
        }
        final boolean teamFoundAndDeleted = internalList.remove(toRemove);
        if (!teamFoundAndDeleted) {
            throw new TeamNotFoundException();
        }
        internalList.add(toReplace);
    }

    /**
     * Finds team with matching teamName from list of teams.
     *
     * @param target Team name of targeted team.
     * @return Targeted team
     * @throws TeamNotFoundException if team is not found
     */
    public Team find (TeamName target) throws TeamNotFoundException {
        for (Team team : internalList) {
            if (team.getTeamName().equals(target)) {
                return team;
            }
        }
        throw new TeamNotFoundException();
    }

    @Override
    public Iterator<Team> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTeamList // instanceof handles nulls
                && this.internalList.equals(((UniqueTeamList) other).internalList));
    }

}
