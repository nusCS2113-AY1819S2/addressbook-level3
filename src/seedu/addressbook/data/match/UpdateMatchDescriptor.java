package seedu.addressbook.data.match;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.player.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the details to update the match with.
 * Each field value will fill the corresponding field value of the match.
 */

public class UpdateMatchDescriptor {
    private TicketSales homeSales;
    private TicketSales awaySales;
    private List<Name> goalScorers;
    private List<Name> ownGoalScorers;

    public UpdateMatchDescriptor(String homeSales,
                              String awaySales,
                              List<Name> goalScorers,
                              List<Name> ownGoalScorers) throws IllegalValueException {
        this.homeSales = new TicketSales(homeSales);
        this.awaySales = new TicketSales(awaySales);
        if (goalScorers.isEmpty()) {
            this.goalScorers = new ArrayList<>();
        } else {
            this.goalScorers = goalScorers;
        }
        if (ownGoalScorers.isEmpty()) {
            this.ownGoalScorers = new ArrayList<>();
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

    public void setGoalScorers(List<Name> goalScorers) {
        this.goalScorers = goalScorers;
    }

    public List<Name> getGoalScorers() {
        return goalScorers;
    }

    public void setOwnGoalScorers(List<Name> ownGoalScorers) {
        this.ownGoalScorers = ownGoalScorers;
    }

    public List<Name> getOwnGoalScorers() {
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
