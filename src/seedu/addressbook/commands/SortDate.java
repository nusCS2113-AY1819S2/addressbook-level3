package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.Comparator;

public class SortDate implements Comparator<ReadOnlyPerson> {

    public int compare(ReadOnlyPerson a, ReadOnlyPerson b){
        return a.getLocalDateTime().compareTo(b.getLocalDateTime());
    }

}
