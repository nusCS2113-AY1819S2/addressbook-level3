package seedu.addressbook.storage.jaxb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.Match;
import seedu.addressbook.data.match.MatchDate;
import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.match.Score;
import seedu.addressbook.data.match.TicketSales;
import seedu.addressbook.data.player.Name;
import seedu.addressbook.data.team.TeamName;

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
    @XmlElement (required = true)
    private String score;
    @XmlElement
    private List<AdaptedName> goalScored = new ArrayList<>();
    @XmlElement
    private List<AdaptedName> ownGoalScored = new ArrayList<>();

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
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        date = df.format(source.getDate().calendar.getTime());

        home = source.getHome().fullName;

        away = source.getAway().fullName;

        homeSales = source.getHomeSales().value;

        awaySales = source.getAwaySales().value;

        score = source.getScore().fullScore;

        goalScored = new ArrayList<>();
        for (Name player : source.getGoalScorers()) {
            goalScored.add(new AdaptedName(player));
        }

        ownGoalScored = new ArrayList<>();
        for (Name player : source.getOwnGoalScorers()) {
            ownGoalScored.add(new AdaptedName(player));
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
        for (AdaptedName player : goalScored) {
            if (player.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        for (AdaptedName player : ownGoalScored) {
            if (player.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        // second call only happens if home/away are all not null
        return Utils.isAnyNull(date, home, away, homeSales, awaySales, score);
    }

    /**
     * Converts this jaxb-friendly adapted match object into the match object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match
     */
    public Match toModelType() throws IllegalValueException, ParseException {
        final List<Name> goalScorers = new ArrayList<>();
        for (AdaptedName player : goalScored) {
            goalScorers.add(player.toModelType());
        }
        final List<Name> ownGoalScorers = new ArrayList<>();
        for (AdaptedName player : ownGoalScored) {
            ownGoalScorers.add(player.toModelType());
        }
        final MatchDate date = new MatchDate(this.date);
        final TeamName home = new TeamName(this.home);
        final TeamName away = new TeamName(this.away);
        final TicketSales homeSales = new TicketSales(this.homeSales);
        final TicketSales awaySales = new TicketSales(this.awaySales);
        final Score score = new Score(this.score);
        return new Match(date, home, away, homeSales, awaySales, goalScorers, ownGoalScorers, score);
    }
}
