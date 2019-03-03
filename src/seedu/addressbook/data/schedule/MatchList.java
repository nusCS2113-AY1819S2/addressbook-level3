package seedu.addressbook.data.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MatchList {
    private final List<Match> internalList = new ArrayList<>();

    /**
     * Constructs empty match list.
     */
    public MatchList(){}

    /**
     * Constructs a match list with the given matches.
     */
    public MatchList(Match... matches){
        final List<Match> initialTags = Arrays.asList(matches);
        internalList.addAll(initialTags);
    }

    /**
     * Constructs a list from the items in the given collection.
     * @param matches a collection of matches
     */
    public MatchList(Collection<Match> matches){
        internalList.addAll(matches);
    }

    /**
     * Constructs a shallow copy of the list.
     */
    public MatchList(MatchList source) {
        internalList.addAll(source.internalList);
    }

    /**
     * Adds a match to the list.
     */
    public void add(Match toAdd){
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent match from the list.
     */
    public void remove(Match toRemove){
        internalList.remove(toRemove);
    }
}
