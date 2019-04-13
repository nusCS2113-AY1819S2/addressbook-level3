package seedu.addressbook.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        String topScorer;
        String topScorerEachTeam;
        String teamScoreBoard;
        //String teamCommittedBoard;  not in v1.4
        //String financialCrisis = DEFAULT_PLACE_HOLDER;   not in v1.4

        StringBuilder builder = new StringBuilder();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

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

        topScorerEachTeam = "\n\n* List of Top Scorers in each team :\n" + topScorerEachTeam + "\n";
        builder.append(topScorerEachTeam);

        //get a score board for teams
        /*if (allTeams.isEmpty()) {
            teamScoreBoard = DEFAULT_PLACE_HOLDER;
        } else {
            teamScoreBoard = generateTeamScoreBoardString(allTeams);
        }

        teamScoreBoard = "Score Leader Board for teams :\n" + teamScoreBoard + "\n";
        builder.append(teamScoreBoard);*/

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
     *
     * @param allPlayers
     * @return
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
     * Generates a score board String for no.of goals scored by each team
     * @param allTeams
     * @return
     */
    private String generateTeamScoreBoardString (List<ReadOnlyTeam> allTeams) {

        String currentTeamString;
        int i = 1;

        StringBuilder builder = new StringBuilder();

        Collections.sort(allTeams, new TeamGoalsScoredComparator());

        //TODO Include goalsScored for each team in the team score board.

        for (ReadOnlyTeam team : allTeams) {
            i++;
            currentTeamString = i + ". " + team.getTeamName().toString() + "\n";
            builder.append(currentTeamString);
        }

        return builder.toString();
    }

    /**
     * Customized comparator to compare teams based on the total number of goals scored by their players
     */
    public class TeamGoalsScoredComparator implements Comparator<ReadOnlyTeam> {
        @Override
        public int compare(ReadOnlyTeam o1, ReadOnlyTeam o2) {
            int o1Goals = 0;
            int o2Goals = 0;

            List<Player> o1Players = o1.getPlayers();
            List<Player> o2Players = o2.getPlayers();

            for (Player player : o1Players) {
                o1Goals += Integer.valueOf(player.getGoalsScored().toString());
            }

            for (Player player : o2Players) {
                o2Goals += Integer.valueOf(player.getGoalsScored().toString());
            }

            return o1Goals - o2Goals;
        }
    }

}
