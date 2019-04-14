package seedu.addressbook.storage.jaxb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.finance.Finance;
import seedu.addressbook.data.finance.UniqueFinanceList;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.UniqueMatchList;
import seedu.addressbook.data.player.Player;
import seedu.addressbook.data.player.UniquePlayerList;
import seedu.addressbook.data.team.Team;
import seedu.addressbook.data.team.UniqueTeamList;


/**
 * JAXB-friendly adapted address book data holder class.
 */
@XmlRootElement(name = "AddressBook")
public class AdaptedAddressBook {

    @XmlElement
    private List<AdaptedPlayer> players = new ArrayList<>();
    @XmlElement
    private List<AdaptedTeam> teams = new ArrayList<>();
    @XmlElement
    private List<AdaptedMatch> matches = new ArrayList<>();
    @XmlElement
    private List<AdaptedFinance> finances = new ArrayList<>();
    @XmlElement
    private List<String> transferRecords = new ArrayList<>();


    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedAddressBook() {}

    /**
     * Converts a given AddressBook into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedAddressBook
     */
    public AdaptedAddressBook(AddressBook source) {
        players = new ArrayList<>();
        matches = new ArrayList<>();
        finances = new ArrayList<>();
        transferRecords = new ArrayList<>();

        source.getAllPlayers().forEach(player -> players.add(new AdaptedPlayer(player)));
        source.getAllMatches().forEach(match -> matches.add(new AdaptedMatch(match)));
        source.getAllTeams().forEach(team -> teams.add(new AdaptedTeam(team)));
        source.getAllFinances().forEach(finance -> finances.add(new AdaptedFinance(finance)));
        source.getAllTransferRecords().forEach(transferRecord -> transferRecords.add(transferRecord));

    }


    /**
     * Returns true if any required field is missing.
     *
     * JAXB does not enforce (required = true) without a given XML schema.
     * Since we do most of our validation using the data class constructors, the only extra logic we need
     * is to ensure that every xml element in the document is present. JAXB sets missing elements as null,
     * so we check for that.
     */
    public boolean isAnyRequiredFieldMissing() {
        return players.stream().anyMatch(AdaptedPlayer::isAnyRequiredFieldMissing)
                || matches.stream().anyMatch(AdaptedMatch::isAnyRequiredFieldMissing)
                || teams.stream().anyMatch(AdaptedTeam::isAnyRequiredFieldMissing)
                || finances.stream().anyMatch(AdaptedFinance::isAnyRequiredFieldMissing);

    }


    /**
     * Converts this jaxb-friendly {@code AdaptedAddressBook} object into the corresponding(@code AddressBook} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted player
     * @throws IllegalValueException if there were any data constraints violated in the adapted match
     * @throws IllegalValueException if there were any data constraints violated in the adapted finance
     */
    public AddressBook toModelType() throws IllegalValueException, ParseException {
        final List<Player> playerList = new ArrayList<>();
        final List<Team> teamList = new ArrayList<>();
        final List<Match> matchList = new ArrayList<>();
        final List<Finance> financeList = new ArrayList<>();
        final ArrayList<String> transferRecordsLists = new ArrayList<>();

        for (AdaptedPlayer player : players) {
            playerList.add(player.toModelType());
        }

        for (AdaptedTeam team : teams) {
            teamList.add(team.toModelType());
        }

        for (AdaptedMatch match : matches) {
            matchList.add(match.toModelType());
        }

        for (AdaptedFinance finance : finances) {
            financeList.add(finance.toModelType());
        }

        for (String record : transferRecords) {
            transferRecordsLists.add(record);
        }

        return new AddressBook(
                new UniquePlayerList(playerList),
                new UniqueTeamList(teamList),
                new UniqueMatchList(matchList),
                new UniqueFinanceList(financeList),
                transferRecordsLists);
    }
}
