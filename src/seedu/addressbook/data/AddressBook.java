package seedu.addressbook.data;

import seedu.addressbook.data.finance.Finance;
import seedu.addressbook.data.finance.UniqueFinanceList;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.match.UniqueMatchList.DuplicateMatchException;
import seedu.addressbook.data.match.UniqueMatchList.MatchNotFoundException;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.player.UniquePlayerList;
import seedu.addressbook.data.player.UniquePlayerList.DuplicatePlayerException;
import seedu.addressbook.data.player.UniquePlayerList.PlayerNotFoundException;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.UniqueTeamList;

/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class AddressBook {

    private final UniquePlayerList allPlayers;
    private final UniqueTeamList allTeams;
    private final UniqueMatchList allMatches;
    private final UniqueFinanceList allFinances;

    /**
     * Creates an empty address book.
     */
    public AddressBook() {
        allPlayers = new UniquePlayerList();
        allMatches = new UniqueMatchList();
        allTeams = new UniqueTeamList();
        allFinances = new UniqueFinanceList();
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param players external changes to this will not affect this address book
     * @param matches external changes to this will not affect this address book
     */
    public AddressBook(UniquePlayerList players,
                       UniqueTeamList teams,
                       UniqueMatchList matches,
                       UniqueFinanceList finances) {
        this.allPlayers = new UniquePlayerList(players);
        this.allTeams = new UniqueTeamList(teams);
        this.allMatches = new UniqueMatchList(matches);
        this.allFinances = new UniqueFinanceList(finances);
    }

    public static AddressBook empty() {
        return new AddressBook();
    }

    /**
     * Adds a player to the address book.
     *
     * @throws DuplicatePlayerException if an equivalent player already exists.
     */
    public void addPlayer(Player toAdd) throws DuplicatePlayerException {
        allPlayers.add(toAdd);
    }

    /**
     * Adds a team to the address book.
     */
    public void addTeam(Team toAdd) throws UniqueTeamList.DuplicateTeamException {
        allTeams.add(toAdd);
    }

    /**
     * Adds a match to the address book.
     *
     * @throws DuplicateMatchException if an equivalent match already exists.
     */
    public void addMatch(Match toAdd) throws DuplicateMatchException {
        allMatches.add(toAdd);
    }

    /**
     * Checks if an equivalent player exists in the address book.
     */
    public boolean containsPlayer(ReadOnlyPlayer key) {
        return allPlayers.contains(key);
    }

    /**
     * Checks if an equivalent team exists in the address book.
     */
    public boolean containsTeam(ReadOnlyTeam key) {
        return allTeams.contains(key);
    }

    /**
     * Checks if an equivalent match exists in the address book.
     */
    public boolean containsMatch(ReadOnlyMatch key) {
        return allMatches.contains(key);
    }

    /**
     * Removes the equivalent player from the address book.
     *
     * @throws PlayerNotFoundException if no such Player could be found.
     */
    public void removePlayer(ReadOnlyPlayer toRemove) throws PlayerNotFoundException {
        allPlayers.remove(toRemove);
    }

    /**
     * Removes the equivalent match from the address book.
     *
     * @throws MatchNotFoundException if no such match could be found.
     */
    public void removeMatch(ReadOnlyMatch toRemove) throws MatchNotFoundException {
        allMatches.remove(toRemove);
    }

    /**
     * Clears all matches from the address book.
     */
    public void clearMatch() {
        allMatches.clear();
    }

    /**
     * Removes the equivalent team from the address book.
     */
    public void removeTeam(ReadOnlyTeam toRemove) throws UniqueTeamList.TeamNotFoundException {
        allTeams.remove(toRemove);
    }

    /**
     * Sorts all persons from the address book.
     */
    public void sort() {
        allPlayers.sort();
    }

    /**
     * Clears all persons from the address book.
     */
    public void clearPlayer() {
        allPlayers.clear();
    }

    /**
     * Clears all teams from the address book.
     */
    public void clearTeam() {
        allTeams.clear();
    }

    /**
     * Edits the equivalent player from League Tracker
     */
    public void editTeam(ReadOnlyTeam toRemove, Team toReplace) throws UniqueTeamList.TeamNotFoundException {
        allTeams.edit(toRemove, toReplace);
    }

    /**
     * Defensively copied UniquePlayerList of all players in the address book at the time of the call.
     */
    public UniquePlayerList getAllPlayers() {
        return new UniquePlayerList(allPlayers);
    }

    /**
     * Defensively copied UniqueMatchList of all matches in the address book at the time of the call.
     */
    public UniqueMatchList getAllMatches() {
        return new UniqueMatchList(allMatches);
    }

    /**
     * Defensively copied UniqueTeamList of all matches in the address book at the time of the call.
     */
    public UniqueTeamList getAllTeams() {
        return new UniqueTeamList(allTeams);
    }

    /**
     * Defensively copied UniqueFinanceList of all finances in the address book at the time of the call.
     */
    public UniqueFinanceList getAllFinances() {
        return new UniqueFinanceList(allFinances);
    }

    /**
     * Refresh all finances to match all existing teams in the address book at the time of the call.
     */
    public void refreshFinance() throws UniqueFinanceList.DuplicateFinanceException {
        allFinances.clear();
        for (ReadOnlyTeam aTeam : allTeams.immutableListView()) {
            allFinances.add(new Finance(aTeam));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.allPlayers.equals(((AddressBook) other).allPlayers));
    }

    @Override
    public int hashCode() {
        return allPlayers.hashCode();
    }
}
