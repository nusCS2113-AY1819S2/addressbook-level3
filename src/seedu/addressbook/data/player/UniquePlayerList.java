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
     * Signals that an operation would have violated the 'no duplicated jn` property
     */
    public static class DuplicateJerseyInSameTeamException extends DuplicateDataException {
        protected DuplicateJerseyInSameTeamException() {
            super("Operation would result in duplicate jersey numbers in the same team");
        }
    }

    /**
     * Signals that an operation targeting a specified player in the list would fail because
     * there is no such matching player in the list.
     */

    public static class PlayerNotFoundException extends Exception {}

    /**
     * Signals that a specified player that is assumed to be in a specified team cannot be found.
     */
    public static class PlayerNotInTeamException extends Exception {}

    private final List<Player> internalList = new ArrayList<>();

    /**
     * Constructs empty player list.
     */

    public UniquePlayerList() {
    }

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
     *
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
        for (Player player: internalList) {
            if (player.getName().toString().equals(toCheck.getName().toString())) {
                return true;
            }
        }
        return internalList.contains(toCheck);
    }

    /**
     *  Checks if the list contains a player in the same team with the same jersey number
     * @param toCheck A player object to be checked
     * @return a Boolean value indicating whether the player with same jersey number in same team already exists
     */
    public boolean containsSameJnSameTm(ReadOnlyPlayer toCheck) {
        for (Player player: internalList) {
            if (player.getTeamName().toString().equals(toCheck.getTeamName().toString())) {
                if (player.getJerseyNumber().toString().equals(toCheck.getJerseyNumber().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds a player to the list.
     *
     * @throws DuplicatePlayerException if the player to add is a duplicate of an existing player in the list.
     * @throws DuplicateJerseyInSameTeamException if the jersey number is already taken in the team
     */
    public void add(Player toAdd) throws DuplicatePlayerException, DuplicateJerseyInSameTeamException {
        if (contains(toAdd)) {
            throw new DuplicatePlayerException();
        }

        if (containsSameJnSameTm(toAdd)) {
            throw new DuplicateJerseyInSameTeamException();
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
     * Edits an existing player in the league.
     *
     * @param toEdit    the target player to be edited
     * @param newPlayer the target player with edited profile
     * @throws PlayerNotFoundException if no such player could be found in the list
     */

    public void edit(ReadOnlyPlayer toEdit, Player newPlayer) throws PlayerNotFoundException {
        final boolean playerFoundAndDeleted = internalList.remove(toEdit);
        if (!playerFoundAndDeleted) {
            throw new PlayerNotFoundException();
        }
        internalList.add(newPlayer);
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

    /**
     * Finds player with matching Name from list of players.
     *
     * @param target Name of targeted player
     * @return Targeted player
     * @throws PlayerNotFoundException if player is not found
     */
    public Player find (Name target) throws PlayerNotFoundException {
        for (Player player : internalList) {
            if (player.getName().equals(target)) {
                return player;
            }
        }
        throw new PlayerNotFoundException();
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
