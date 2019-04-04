package seedu.addressbook.data.player;

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
 * A list of players. Does not allow null elements or duplicates.
 *
 * @see Player#equals(Object)
 */
public class UniquePlayerList implements Iterable<Player> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */

    public static class DuplicatePlayerException extends DuplicateDataException {
        protected DuplicatePlayerException() {
            super("Operation would result in duplicate players");
        }
    }

    /**
     * Signals that an operation targeting a specified player in the list would fail because
     * there is no such matching player in the list.
     */

    public static class PlayerNotFoundException extends Exception {}

    private final List<Player> internalList = new ArrayList<>();

    /**
     * Constructs empty player list.
     */

    public UniquePlayerList() {}

    /**
     * Constructs a player list with the given players.
     */
    public UniquePlayerList(Player... players) throws DuplicatePlayerException {
        final List<Player> initialTags = Arrays.asList(players);
        if (!Utils.elementsAreUnique(initialTags)) {
            throw new DuplicatePlayerException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param players a collection of players
     * @throws DuplicatePlayerException if the {@code players} contains duplicate players
     */
    public UniquePlayerList(Collection<Player> players) throws DuplicatePlayerException {
        if (!Utils.elementsAreUnique(players)) {
            throw new DuplicatePlayerException();
        }
        internalList.addAll(players);
    }

    /**
     * Constructs a shallow copy of the list.
     */

    public UniquePlayerList(UniquePlayerList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Unmodifiable java List view with elements cast as immutable {@link ReadOnlyPlayer}s.
     * For use with other methods/libraries.
     * Any changes to the internal list/elements are immediately visible in the returned list.
     */

    public List<ReadOnlyPlayer> immutableListView() {
        return Collections.unmodifiableList(internalList);
    }


    /**
     * Checks if the list contains an equivalent player as the given argument.
     */
    public boolean contains(ReadOnlyPlayer toCheck) {
        return internalList.contains(toCheck);
    }

    /**
     * Adds a player to the list.
     *
     * @throws DuplicatePlayerException if the player to add is a duplicate of an existing player in the list.
     */
    public void add(Player toAdd) throws DuplicatePlayerException {
        if (contains(toAdd)) {
            throw new DuplicatePlayerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent player from the list.
     *
     * @throws PlayerNotFoundException if no such player could be found in the list.
     */

    public void remove(ReadOnlyPlayer toRemove) throws PlayerNotFoundException {
        final boolean playerFoundAndDeleted = internalList.remove(toRemove);
        if (!playerFoundAndDeleted) {
            throw new PlayerNotFoundException();
        }
    }

    /**
     * Clears all players in list.
     */
    public void clear() {
        internalList.clear();
    }

    /**
     * Sort all players in list by ascending alphabetical order.
     */
    public void sort() {
        Comparator<Player> customPlayerCompare = Comparator.comparing(Player::getName);
        Collections.sort(internalList, customPlayerCompare);
    }

    @Override
    public Iterator<Player> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePlayerList // instanceof handles nulls
                && this.internalList.equals(((UniquePlayerList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
