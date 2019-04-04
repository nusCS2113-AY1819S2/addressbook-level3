package seedu.addressbook.data.match;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.Name;

/**
 * Stores the details to update the match with.
 * Each field value will fill the corresponding field value of the match.
 */

public class UpdateMatchDescriptor {
    private TicketSales homeSales;
    private TicketSales awaySales;
    private Set<Name> goalScorers;
    private Set<Name> ownGoalScorers;


    public UpdateMatchDescriptor() {}

    public UpdateMatchDescriptor(String homeSales,
                              String awaySales,
                              Set<Name> goalScorers,
                              Set<Name> ownGoalScorers) throws IllegalValueException {
        this.homeSales = new TicketSales(homeSales);
        this.awaySales = new TicketSales(awaySales);
        if (goalScorers.isEmpty()) {
            this.goalScorers = new HashSet<>();
        } else {
            this.goalScorers = goalScorers;
        }
        if (ownGoalScorers.isEmpty()) {
            this.ownGoalScorers = new HashSet<>();
        } else {
            this.ownGoalScorers = ownGoalScorers;
        }
    }

    /**
     * Copy constructor.
     */
    public UpdateMatchDescriptor(UpdateMatchDescriptor toCopy) {
        setHomeSales(toCopy.homeSales);
        setAwaySales(toCopy.awaySales);
        setGoalScorers(toCopy.goalScorers);
        setOwnGoalScorers(toCopy.ownGoalScorers);
    }

    public void setHomeSales(TicketSales homeSales) {
        this.homeSales = homeSales;
    }

    public TicketSales getHomeSales() {
        return homeSales;
    }

    public void setAwaySales(TicketSales awaySales) {
        this.awaySales = awaySales;
    }

    public TicketSales getAwaySales() {
        return awaySales;
    }

    public void setGoalScorers(Set<Name> goalScorers) {
        this.goalScorers = goalScorers;
    }

    public Set<Name> getGoalScorers() {
        return goalScorers;
    }

    public void setOwnGoalScorers(Set<Name> ownGoalScorers) {
        this.ownGoalScorers = ownGoalScorers;
    }

    public Set<Name> getOwnGoalScorers() {
        return ownGoalScorers;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateMatchDescriptor)) {
            return false;
        }

        // state check
        UpdateMatchDescriptor e = (UpdateMatchDescriptor) other;

        return getHomeSales().equals(e.getHomeSales())
                && getAwaySales().equals(e.getAwaySales())
                && getGoalScorers().equals(e.getGoalScorers())
                && getOwnGoalScorers().equals(e.getOwnGoalScorers());
    }
}
