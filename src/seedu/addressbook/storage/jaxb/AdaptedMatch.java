package seedu.addressbook.storage.jaxb;

import seedu.addressbook.common.Utils;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.match.*;
import seedu.addressbook.data.tag.Tag;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @XmlElement
    private List<AdaptedTag> tagged = new ArrayList<>();

    /**
     * No-arg constructor for JAXB use.
     */
    public AdaptedMatch() {}


    /**
     * Converts a given Match into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created AdaptedMatch
     */
    public AdaptedMatch(ReadOnlyMatch source) {
        date = source.getDate().fullDate;

        home = source.getHome().fullHome;

        away = source.getAway().fullAway;

        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new AdaptedTag(tag));
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
        for (AdaptedTag tag : tagged) {
            if (tag.isAnyRequiredFieldMissing()) {
                return true;
            }
        }
        // second call only happens if home/away are all not null
        return Utils.isAnyNull(date, home, away)
                || Utils.isAnyNull(home, away);
    }

    /**
     * Converts this jaxb-friendly adapted match object into the Match object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match
     */
    public Match toModelType() throws IllegalValueException {
        final Set<Tag> tags = new HashSet<>();
        for (AdaptedTag tag : tagged) {
            tags.add(tag.toModelType());
        }
        final Date date = new Date(this.date);
        final Home home = new Home(this.home);
        final Away away = new Away(this.away);
        return new Match(date, home, away, tags);
    }
}