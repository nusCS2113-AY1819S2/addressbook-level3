package seedu.addressbook.storage.jaxb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Away;
import seedu.addressbook.data.match.Date;
import seedu.addressbook.data.match.Home;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.TicketSales;
import seedu.addressbook.data.player.Player;

/**
 * JAXB-friendly adapted match data holder class.
 */
public class AdaptedMatch {

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String home;
    @XmlElement(required = true)
    private String away;

    @XmlElement (required = true)
    private String homeSales;
    @XmlElement (required = true)
    private String awaySales;
    @XmlElement
    private List<AdaptedPlayer> goalScored = new ArrayList<>();
    @XmlElement
    private List<AdaptedPlayer> ownGoalScored = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedMatch() {}


    /**
     * Converts a given match into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedMatch
     */
    public AdaptedMatch(ReadOnlyMatch source) {
        date = source.getDate().fullDate;

        home = source.getHome().fullHome;

        away = source.getAway().fullAway;

        homeSales = source.getHomeSales().value;

        awaySales = source.getAwaySales().value;

        goalScored = new ArrayList<>();
        for (Player player : source.getGoalScorers()) {
            goalScored.add(new AdaptedPlayer(player));
        }

        ownGoalScored = new ArrayList<>();
        for (Player player : source.getOwnGoalScorers()) {
            ownGoalScored.add(new AdaptedPlayer(player));
        }
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
        for (AdaptedPlayer player : goalScored) {
            if (player.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        for (AdaptedPlayer player : ownGoalScored) {
            if (player.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        // second call only happens if home/away are all not null
        return Utils.isAnyNull(date, home, away, homeSales, awaySales);
    }

    /**
     * Converts this jaxb-friendly adapted match object into the match object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match
     */
    public Match toModelType() throws IllegalValueException {
        final Set<Player> goalScorers = new HashSet<>();
        for (AdaptedPlayer player : goalScored) {
            goalScorers.add(player.toModelType());
        }
        final Set<Player> ownGoalScorers = new HashSet<>();
        for (AdaptedPlayer player : ownGoalScored) {
            ownGoalScorers.add(player.toModelType());
        }
        final Date date = new Date(this.date);
        final Home home = new Home(this.home);
        final Away away = new Away(this.away);
        final TicketSales homeSales = new TicketSales(this.homeSales);
        final TicketSales awaySales = new TicketSales(this.awaySales);
        return new Match(date, home, away, homeSales, awaySales, goalScorers, ownGoalScorers);
    }
}
