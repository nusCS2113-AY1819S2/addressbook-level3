package seedu.addressbook.commands.player;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.JerseyNumber;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.TeamName;
import seedu.addressbook.data.player.UniquePlayerList;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.UniqueTeamList;

/**
 * This command transfers player from one team to another in the league tracker
 */
public class TransferPlayerCommand extends Command {

    public static final String COMMAND_WORD = "transfer";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ":\n"
                    + "Transfers a player from one team to another in the League Tracker. "
                    + "\n"
                    + "Parameters: "
                    + "PLAYER_NAME tm/NEW_TEAM_NAME jn/NEW_JERSEY_NUMBER\n"
                    + "Example: "
                    + COMMAND_WORD
                    + " Lionel Messi tm/Real Madrid jn/10\n"
                    + "Team and player entered must exist in league tracker \n"
                    + "The jersey number in destination team must be available \n"
                    + "Destination team cannot be the same as player's current team";

    public static final String MESSAGE_SUCCESS = "Player %1$s is successfully transferred from %2$s to %3$s, "
            + "%1$s 's new jersey number is %4$s";
    public static final String MESSAGE_PLAYER_NOT_FOUND = "This player %1$s does not exist in the league tracker";
    public static final String MESSAGE_NO_SUCH_TEAM = "This team %1$s does not exist, please enter an existing team";
    public static final String MESSAGE_DESTINATION_IS_CURRENT = "Destination team is same as current team %1$s";
    public static final String MESSAGE_JERSEY_NUMBER_TAKEN = "The jersey number %1$s has already "
            + "been taken in team %2$s. Try again with a different jersey number";

    private final TeamName teamNameItem;
    private final Name playerNameItem;
    private final JerseyNumber jerseyNumberItem;

    public TransferPlayerCommand(String playerName, String destinationTeamName,
                                 String newJerseyNumber) throws IllegalValueException {
        this.teamNameItem = new TeamName(destinationTeamName);
        this.playerNameItem = new Name(playerName);
        this.jerseyNumberItem = new JerseyNumber(newJerseyNumber);
    }

    @Override
    public CommandResult execute() {
        UniquePlayerList oldAllPlayers = addressBook.getAllPlayers();
        UniqueTeamList allTeams = addressBook.getAllTeams();

        Player oldPlayer = null;
        Player newPlayer;
        Boolean isOldPlayerFound = false;
        Boolean isTeamFound = false;
        String oldTeamName;

        //check if the player exists in league tracker
        //check if the destination team is the same as the current team of player
        for (Player player : oldAllPlayers) {
            if (player.getName().equals(this.playerNameItem)) {
                oldPlayer = player;
                oldTeamName = player.getTeamName().toString();
                isOldPlayerFound = true;

                if (oldTeamName.equals(this.teamNameItem.toString())) {
                    return new CommandResult(String.format(MESSAGE_DESTINATION_IS_CURRENT, oldTeamName));
                }
            }
        }

        // if the player does not exist, return an error message and terminate the execute()
        // else, create the player after transfer
        if (!isOldPlayerFound) {
            return new CommandResult(String.format(
                    MESSAGE_PLAYER_NOT_FOUND, this.playerNameItem.toString()
            ));
        } else {
            newPlayer = createPlayerAfterTransfer(this.teamNameItem, this.jerseyNumberItem, oldPlayer);
        }

        // check if the destination team exists
        for (Team team : allTeams) {
            if (team.getTeamName().toString().equals(this.teamNameItem.toString())) {
                isTeamFound = true;
            }
        }

        // if the destination team does not exists, return an error message and terminate the execute()
        // else continue, the destination team definitely exists
        if (!isTeamFound) {
            return new CommandResult(String.format(MESSAGE_NO_SUCH_TEAM, this.teamNameItem.toString()));
        }

        // remove oldPlayer from league tracker and add the newPlayer into league tracker
        try {
            addressBook.removePlayer(oldPlayer);
            addressBook.addPlayer(newPlayer);
        } catch (UniquePlayerList.PlayerNotFoundException pnfe) {
            System.out.println("Old player not found in league tracker");
        } catch (UniquePlayerList.DuplicatePlayerException dpe) {
            System.out.println("New Player already exists in league tracker");
        } catch (UniquePlayerList.DuplicateJerseyInSameTeamException djste) {
            this.restoreRemovedPlayer(oldPlayer);
            return new CommandResult(String.format(MESSAGE_JERSEY_NUMBER_TAKEN,
                    newPlayer.getJerseyNumber().toString(), newPlayer.getTeamName().toString()));
        }

        //transfer done
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldPlayer.getName().toString(),
                oldPlayer.getTeamName().toString(),
                this.teamNameItem.toString(),
                this.jerseyNumberItem.toString()));
    }

    /**
     * creates the player after transfer
     * @param teamNameItem Team Name of the destination team
     * @param oldPlayer player before transfer
     * @return player after transfer
     */
    private static Player createPlayerAfterTransfer(TeamName teamNameItem,
                                                    JerseyNumber jerseyNumberItem, Player oldPlayer) {
        return new Player(oldPlayer.getName(), oldPlayer.getPositionPlayed(), oldPlayer.getAge(),
                oldPlayer.getSalary(), oldPlayer.getGoalsScored(), oldPlayer.getGoalsAssisted(),
                teamNameItem, oldPlayer.getNationality(), jerseyNumberItem,
                oldPlayer.getAppearance(), oldPlayer.getHealthStatus(), oldPlayer.getTags());
    }

    /**
     * restores the oldPlayer removed during transfer
     * @param oldPlayer player before transfer
     */
    private void restoreRemovedPlayer(Player oldPlayer) {
        try {
            addressBook.addPlayer(oldPlayer);
        } catch (UniquePlayerList.DuplicatePlayerException dpe) {
            System.out.println("oldPlayer was not deleted");
        } catch (UniquePlayerList.DuplicateJerseyInSameTeamException djste) {
            System.out.println("That jersey number has been taken by someone already");
        }
    }


}
