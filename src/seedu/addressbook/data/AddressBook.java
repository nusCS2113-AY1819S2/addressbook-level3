package seedu.addressbook.data;

import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.match.UniqueMatchList.DuplicateMatchException;
import seedu.addressbook.data.match.UniqueMatchList.MatchNotFoundException;

import seedu.addressbook.data.player.Person;
import seedu.addressbook.data.player.ReadOnlyPerson;
import seedu.addressbook.data.player.UniquePersonList;
import seedu.addressbook.data.player.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.player.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.UniqueTeamList;

/**
 * Represents the entire address book. Contains the data of the address book.
 */
public class AddressBook {

    private final UniquePersonList allPersons;
    private final UniqueTeamList allTeams;
    private final UniqueMatchList allMatches;

    /**
     * Creates an empty address book.
     */
    public AddressBook() {
        allPersons = new UniquePersonList();
        allMatches = new UniqueMatchList();
        allTeams = new UniqueTeamList();
    }

    /**
     * Constructs an address book with the given data.
     *
     * @param persons external changes to this will not affect this address book
     * @param matches external changes to this will not affect this address book
     */
    public AddressBook(UniquePersonList persons,
                       UniqueTeamList teams,
                       UniqueMatchList matches) {
        this.allPersons = new UniquePersonList(persons);
        this.allTeams = new UniqueTeamList(teams);
        this.allMatches = new UniqueMatchList(matches);
    }

    public static AddressBook empty() {
        return new AddressBook();
    }

    /**
     * Adds a player to the address book.
     *
     * @throws DuplicatePersonException if an equivalent player already exists.
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        allPersons.add(toAdd);
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
    public boolean containsPerson(ReadOnlyPerson key) {
        return allPersons.contains(key);
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
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        allPersons.remove(toRemove);
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
        allPersons.sort();
    }

    /**
     * Clears all persons from the address book.
     */
    public void clearPerson() {
        allPersons.clear();
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
     * Defensively copied UniquePersonList of all persons in the address book at the time of the call.
     */
    public UniquePersonList getAllPersons() {
        return new UniquePersonList(allPersons);
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.allPersons.equals(((AddressBook) other).allPersons));
    }

    @Override
    public int hashCode() {
        return allPersons.hashCode();
    }
}
