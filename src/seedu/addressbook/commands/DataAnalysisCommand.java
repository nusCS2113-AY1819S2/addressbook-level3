package seedu.addressbook.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.ReadOnlyPlayer;
import seedu.addressbook.data.team.ReadOnlyTeam;
/**
 * Displays a report of results obtained by analyzing stored data in League Tracker
 */
public class DataAnalysisCommand extends Command {

    public static final String COMMAND_WORD = "generateReport";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
            + "will conduct a pre-set analysis on data stored in League Tracker \n"
            + "including players, teams, matches, finance and the whole league. \n"
            + "Example: " + COMMAND_WORD + "\n";
    private static final String DEFAULT_PLACE_HOLDER = "Not Available";
    private static final String MESSAGE_SUCCESS = "League Tracker has analyzed existing data for you \n"
            + "Data Analysis Report in League Tracker generated at %1$s \n"
            + "\n" + "%2$s";



    @Override
    public CommandResult execute() {
        List<ReadOnlyPlayer> allPlayers = addressBook.getAllPlayers().immutableListView();
        List<ReadOnlyTeam> allTeams = addressBook.getAllTeams().immutableListView();
        List<ReadOnlyMatch> allMatches = addressBook.getAllMatches().immutableListView();

        String leagueTrackerInfo;
        String topScorer;
        String topScorerEachTeam;
        String transferRecord;

        StringBuilder builder = new StringBuilder();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //get League Tracker Info
        if (allPlayers.isEmpty() && allMatches.isEmpty() && allTeams.isEmpty()) {
            leagueTrackerInfo = "League Tracker is currently empty\n\n";
        } else {
            leagueTrackerInfo = generateLeagueInfo(allTeams, allPlayers, allMatches);
        }

        builder.append(leagueTrackerInfo);

        //get top scorer(s) in the league
        if (allPlayers.isEmpty()) {
            topScorer = DEFAULT_PLACE_HOLDER;
        } else {
            topScorer = generateTopScorer(allPlayers);
        }

        topScorer = "\n* Golden Boot :\n" + topScorer + "\n";
        builder.append(topScorer);

        //get top scorer(s) in each team
        if (allPlayers.isEmpty() || allTeams.isEmpty()) {
            topScorerEachTeam = DEFAULT_PLACE_HOLDER;
        } else {
            topScorerEachTeam = generateTopScorerPerTeam(allTeams);
        }

        topScorerEachTeam = "\n* List of Top Scorers in each team :\n" + topScorerEachTeam + "\n";
        builder.append(topScorerEachTeam);

        //get transfer records
        transferRecord = "\n* Records of all player transfers processed by League Tracker :\n"
                + generateTransferRecord();
        builder.append(transferRecord);

        return new CommandResult(String.format(MESSAGE_SUCCESS, dtf.format(now), builder.toString()));

    }

    /**
     *  generates output string for top scorer(s)
     * @param allPlayers a List of all Players in the league tracker
     * @return a String containing all top scorers names and their goals scored
     */
    private String generateTopScorer (List<ReadOnlyPlayer> allPlayers) {

        String outputString = "";
        int highestGoals = -1;
        int currentGoals = -1;

        for (ReadOnlyPlayer player : allPlayers) {
            try {
                currentGoals = Integer.parseInt(player.getGoalsScored().toString());
            } catch (NumberFormatException nfe) {
                System.out.println("invalid goals Scored detected");
            }

            if (currentGoals > highestGoals) {
                highestGoals = currentGoals;
                outputString = player.getName().toString() + " from " + player.getTeamName().toString();
            } else if (currentGoals == highestGoals) {
                outputString = outputString.concat("/" + player.getName().toString()
                        + " from " + player.getTeamName().toString());
            }
        }

        if (highestGoals < 0) {
            highestGoals = 0;
        }

        outputString = outputString.concat(" with " + highestGoals + " goals.");

        return outputString;
    }


    /**
     * Overloaded to take in List<Player> argument for String generation
     * @param allPlayers A list of players to be processed for topscorer string
     * @return a String containing name(s) of top goal scorers in the list passed in as argument
     */
    private String generateTopScorerOverloaded(List<Player> allPlayers) {

        String outputString = "";
        int highestGoals = -1;
        int currentGoals = -1;

        for (ReadOnlyPlayer player : allPlayers) {
            try {
                currentGoals = Integer.parseInt(player.getGoalsScored().toString());
            } catch (NumberFormatException nfe) {
                System.out.println("invalid goals Scored detected");
            }

            if (currentGoals > highestGoals) {
                highestGoals = currentGoals;
                outputString = player.getName().toString() + " from " + player.getTeamName().toString();
            } else if (currentGoals == highestGoals) {
                outputString = outputString.concat("/" + player.getName().toString()
                        + " from " + player.getTeamName().toString());
            }
        }

        if (highestGoals < 0) {
            highestGoals = 0;
        }

        outputString = outputString.concat(" with " + highestGoals + " goals.");

        return outputString;
    }


    /**
     * Generates output string for scorer(s) in each team
      * @param allTeams a list of all teams in League Tracker
     * @return a String of all teams with their respective top scorers and goals scored
     */
    private String generateTopScorerPerTeam (List<ReadOnlyTeam> allTeams) {

        String currentTeamString;

        StringBuilder builder = new StringBuilder();

        for (ReadOnlyTeam team : allTeams) {
            currentTeamString = team.getTeamName().toString() + ": "
                    + generateTopScorerOverloaded(team.getPlayers()) + "\n";
            builder.append(currentTeamString);
        }

        return builder.toString();
    }

    /**
     * Generates output string for League Tracker status info
     * @param allTeams a list of all teams in League Tracker
     * @param allPlayer a list of all players in League Tracker
     * @param allMatches a list of all matches in League Tracker
     * @return a String of League Tracker status info
     */
    private String generateLeagueInfo (List<ReadOnlyTeam> allTeams,
                                       List<ReadOnlyPlayer> allPlayer, List<ReadOnlyMatch> allMatches) {
        String outputString = "Until generation of this report, League Tracker is tracking: \n";
        String numOfPlayerString = " players\n";
        String numOfTeamString = " teams\n";
        String numOfMatchesString = " matches\n";
        String numOfCompletedMatchesString = " tracked matches have been played to update data in League Tracker \n";
        int numPlayed = 0;

        numOfPlayerString = allPlayer.size() + numOfPlayerString;
        numOfTeamString = allTeams.size() + numOfTeamString;
        numOfMatchesString = allMatches.size() + numOfMatchesString;

        for (ReadOnlyMatch match : allMatches) {
            if (!match.notPlayed()) {
                numPlayed += 1;
            }
        }

        numOfCompletedMatchesString = numPlayed + numOfCompletedMatchesString;

        return outputString + numOfPlayerString + numOfTeamString + numOfMatchesString
                + numOfCompletedMatchesString + "\n";
    }

    /**
     * Generates a string of all transfer records being processed in League Tracker
     * @return a string of transfer records
     */
    private String generateTransferRecord () {
        List<String> allRecords = addressBook.getAllTransferRecords();
        StringBuilder transferRecordBuilder = new StringBuilder();

        for (String record : allRecords) {
            transferRecordBuilder.append(record);
        }

        return transferRecordBuilder.toString();
    }

}
