package seedu.addressbook.data;

import java.util.List;
import java.util.Set;

import seedu.addressbook.data.finance.Finance;
import seedu.addressbook.data.finance.ReadOnlyFinance;
import seedu.addressbook.data.finance.UniqueFinanceList;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.match.UniqueMatchList.DuplicateMatchException;
import seedu.addressbook.data.match.UniqueMatchList.MatchNotFoundException;
import seedu.addressbook.data.match.UniqueMatchList.MatchUpdatedException;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.player.UniquePlayerList;
import seedu.addressbook.data.player.UniquePlayerList.DuplicatePlayerException;
import seedu.addressbook.data.player.UniquePlayerList.PlayerNotFoundException;
import seedu.addressbook.data.player.UniquePlayerList.PlayerNotInTeamException;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.UniqueTeamList;
import seedu.addressbook.data.team.UniqueTeamList.DuplicateTeamException;
import seedu.addressbook.data.team.UniqueTeamList.TeamNotFoundException;


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
     * Adds a player to the League Tracker.
     *
     * @throws DuplicatePlayerException if an equivalent player already exists.
     */
    public void addPlayer(Player toAdd) throws DuplicatePlayerException {
        allPlayers.add(toAdd);
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals((toAdd.getTeamName().toString()))) {
                team.addPlayer(toAdd);
            }
        }
    }

    /**
     * Edits the equivalent player from League Tracker
     */

    public void editPlayer(ReadOnlyPlayer toEdit, Player newPlayer) throws UniquePlayerList.PlayerNotFoundException {
        allPlayers.edit(toEdit, newPlayer);
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toEdit.getTeamName().toString())) {
                team.removePlayer(toEdit);
                team.addPlayer(newPlayer);
            }
        }
    }

    /**
     * Adds a team to the address book.
     */
    public void addTeam(Team toAdd) throws DuplicateTeamException {
        allTeams.add(toAdd);
    }

    /**
     * Adds a match to the address book.
     *
     * @throws DuplicateMatchException if an equivalent match already exists.
     */
    public void addMatch(Match toAdd) throws DuplicateMatchException {
        allMatches.add(toAdd);
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toAdd.getHome().toString())) {
                team.addMatch(toAdd);
            }
        }
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toAdd.getAway().toString())) {
                team.addMatch(toAdd);
            }
        }
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
     * Checks if an equivalent finance exists in the address book.
     */
    public boolean containsFinance(ReadOnlyFinance key) {
        return allFinances.contains(key);
    }

    /**
     * Removes the equivalent player from the address book.
     *
     * @throws PlayerNotFoundException if no such Player could be found.
     */
    public void removePlayer(ReadOnlyPlayer toRemove) throws PlayerNotFoundException {
        allPlayers.remove(toRemove);
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toRemove.getTeamName().toString())) {
                team.removePlayer(toRemove);
            }
        }
    }

    /**
     * Removes the equivalent match from the address book.
     *
     * @throws MatchNotFoundException if no such match could be found.
     */
    public void removeMatch(ReadOnlyMatch toRemove) throws MatchNotFoundException {
        allMatches.remove(toRemove);
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toRemove.getHome().toString())) {
                team.removeMatch(toRemove);
            }
        }
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toRemove.getAway().toString())) {
                team.removeMatch(toRemove);
            }
        }
    }

    /**
     * Clears all matches from the address book.
     */
    public void clearMatch() {
        allMatches.clear();
        for (Team team : allTeams) {
            team.clearMatchList();
        }
    }

    /**
     * Removes the equivalent team from the address book.
     */
    public void removeTeam(ReadOnlyTeam toRemove) throws
            TeamNotFoundException,
            PlayerNotFoundException,
            MatchNotFoundException {
        allTeams.remove(toRemove);
        Set<Player> players = toRemove.getPlayers();
        Set<Match> matches = toRemove.getMatches();
        for (Player player : players) {
            allPlayers.remove(player);
        }
        for (Match match : matches) {
            allMatches.remove(match);
        }
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
        for (Team team : allTeams) {
            team.clearPlayerList();
        }
    }

    /**
     * Clears all teams from the address book.
     */
    public void clearTeam() {
        allTeams.clear();
        allPlayers.clear();
        allMatches.clear();
    }

    /**
     * Edits the equivalent team from League Tracker
     */
    public void editTeam(ReadOnlyTeam toRemove, Team toReplace) throws UniqueTeamList.TeamNotFoundException {
        allTeams.edit(toRemove, toReplace);
    }

    /**
     * Updates the equivalent match from League Tracker
     */
    public void updateMatch(ReadOnlyMatch toRemove, Match toReplace) throws MatchNotFoundException,
            MatchUpdatedException, TeamNotFoundException, PlayerNotInTeamException {
        String score = computeScore(toRemove, toReplace);
        toReplace.setScore(score);
        allMatches.update(toRemove, toReplace);
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toRemove.getHome().toString())) {
                team.removeMatch(toRemove);
                team.addMatch(toReplace);
                team.updatePoints();
            }
        }
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(toRemove.getAway().toString())) {
                team.removeMatch(toRemove);
                team.addMatch(toReplace);
                team.updatePoints();
            }
        }
    }

    /**
     * Defensively copied UniquePlayerList of all players in the address book at the time of the call.
     */
    public UniquePlayerList getAllPlayers() {
        return new UniquePlayerList(allPlayers);
    }

    /**
     * Defensively copied sorted UniqueMatchList of all matches in the address book at the time of the call.
     */
    public UniqueMatchList getAllMatches() {
        allMatches.sort();
        return new UniqueMatchList(allMatches);
    }

    /**
     * Defensively copied sorted UniqueTeamList of all teams in the address book at the time of the call.
     */
    public UniqueTeamList getAllTeams() {
        allTeams.sort();
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

    /**
     * Sorts all persons from the address book.
     */
    public void sortFinance() {
        allFinances.sort();
    }

    /**
     * @param toRemove
     * @param toReplace
     * @return Computed score of both team
     * @throws TeamNotFoundException if either team does not exit in LeagueTracker
     * @throws PlayerNotInTeamException if any (own)goalScorers is not in either team
     */
    public String computeScore(ReadOnlyMatch toRemove, Match toReplace) throws TeamNotFoundException,
            PlayerNotInTeamException {
        Team home = allTeams.find(toRemove.getHome());
        Team away = allTeams.find(toRemove.getAway());
        int homeScore = countScore(toReplace.getGoalScorers(), home.getPlayers())
                + countScore(toReplace.getOwnGoalScorers(), away.getPlayers());
        int awayScore = countScore(toReplace.getGoalScorers(), away.getPlayers())
                + countScore(toReplace.getOwnGoalScorers(), home.getPlayers());
        if ((toReplace.getGoalScorers().size() + toReplace.getOwnGoalScorers().size() != (homeScore + awayScore))) {
            throw new PlayerNotInTeamException();
        }
        return String.valueOf(homeScore) + "-" + String.valueOf(awayScore);
    }

    /**
     * @param target
     * @param team
     * @returns score of each team contributed by either goals or own goals.
     */
    public int countScore (List<Name> target, Set <Player> team) {
        int count = 0;
        for (Name scorers : target) {
            for (Player players : team) {
                if (players.getName().equals(scorers)) {
                    count++;
                }
            }
        }
        return count;
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
