package seedu.addressbook.commands.match;

import java.text.ParseException;
import java.util.ArrayList;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.MatchDate;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.Score;
import seedu.addressbook.data.match.TicketSales;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.team.TeamName;
import seedu.addressbook.data.team.UniqueTeamList;

/**
 * Adds a match to the address book.
 */
public class AddMatchCommand extends Command {

    public static final String COMMAND_WORD = "addmatch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a match to the address book. "
            + "Parameters: DATE h/HOMETEAM a/AWAYTEAM\n\t"
            + "Example: " + COMMAND_WORD
            + " 17 MAR 2019 h/West Ham a/Huddersfield \n";

    public static final String MESSAGE_SUCCESS = "New match added: %1$s";
    public static final String MESSAGE_DUPLICATE_MATCH = "This match already exists in the address book";
    public static final String MESSAGE_SAME_TEAM = "Teams cannot play against themselves!";

    private final Match toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddMatchCommand(String date,
                      String home,
                      String away) throws IllegalValueException, ParseException {
        this.toAdd = new Match(
                new MatchDate(date),
                new TeamName(home),
                new TeamName(away),
                new TicketSales(""),
                new TicketSales(""),
                new ArrayList<>(),
                new ArrayList<>(),
                new Score("")
        );
    }

    public AddMatchCommand(Match toAdd) {
        this.toAdd = toAdd;
    }

    public ReadOnlyMatch getMatch() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            addressBook.addMatch(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueMatchList.DuplicateMatchException dme) {
            return new CommandResult(MESSAGE_DUPLICATE_MATCH);
        } catch (UniqueTeamList.TeamNotFoundException tnfe) {
            return new CommandResult(Messages.MESSAGE_TEAM_NOT_IN_LEAGUE_TRACKER);
        } catch (UniqueMatchList.SameTeamException ste) {
            return new CommandResult(MESSAGE_SAME_TEAM);
        }
    }

}
