package seedu.addressbook.data.finance;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.team.ReadOnlyTeam;
import seedu.addressbook.data.match.ReadOnlyMatch;

import java.util.List;
import java.util.ArrayList;


public class TicketBox {
    private double boxIncome = 0;
    private ReadOnlyTeam targetTeam;
    private List<ReadOnlyMatch> matchesOfTeam;

    String homeString = targetTeam.toString();
    protected AddressBook addressBook;

    public TicketBox (ReadOnlyTeam team){
        this.targetTeam = team;
        this.matchesOfTeam = getMatchesWithHome(homeString);


    }

    private List<ReadOnlyMatch> getMatchesWithHome(String home) {
        final List<ReadOnlyMatch> matchedMatches = new ArrayList<>();
        for (ReadOnlyMatch match : addressBook.getAllMatches()) {
            final String wordsInHome = match.getHome().toString();
            if (wordsInHome.equals(home)) {
                matchedMatches.add(match);
            }
        }
        return matchedMatches;
    }

    public List<ReadOnlyMatch> getMatchesOfTeam(){
        return matchesOfTeam;
    }
    public double getTicketBox(){
        return boxIncome;
    }

}
