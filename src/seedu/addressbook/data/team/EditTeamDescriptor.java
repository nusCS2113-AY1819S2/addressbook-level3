package seedu.addressbook.data.team;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.tag.Tag;

/**
 * Stores the details to edit the team with.
 * Each non-empty field value will replace the corresponding field value of the team.
 */

public class EditTeamDescriptor {
    private TeamName teamName;
    private Country country;
    private Sponsor sponsor;
    private Set<Tag> tagset;


    public EditTeamDescriptor() {}

    public EditTeamDescriptor(String name,
                              String country,
                              String sponsor,
                              Set<Tag> tagset) throws IllegalValueException {
        if (name == null) {
            this.teamName = null;
        } else {
            this.teamName = new TeamName(name);
        }

        if (country == null) {
            this.country = null;
        } else {
            this.country = new Country(country);
        }

        if (sponsor == null) {
            this.sponsor = null;
        } else {
            this.sponsor = new Sponsor(sponsor);
        }

        if (tagset.isEmpty()) {
            this.tagset = new HashSet<>();
        } else {
            this.tagset = tagset;
        }
    }

    /**
     * Copy constructor.
     */
    public EditTeamDescriptor(EditTeamDescriptor toCopy) {
        setTeamName(toCopy.teamName);
        setCountry(toCopy.country);
        setSponsor(toCopy.sponsor);
        setTags(toCopy.tagset);
    }

    public void setTeamName(TeamName teamName) {
        this.teamName = teamName;
    }

    public TeamName getTeamName() {
        return teamName;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setTags(Set<Tag> tagset) {
        this.tagset = tagset;
    }

    public Set<Tag> getTags() {
        return tagset;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTeamDescriptor)) {
            return false;
        }

        // state check
        EditTeamDescriptor e = (EditTeamDescriptor) other;

        return getTeamName().equals(e.getTeamName())
                && getCountry().equals(e.getCountry())
                && getSponsor().equals(e.getSponsor())
                && getTags().equals(e.getTags());
    }
}
