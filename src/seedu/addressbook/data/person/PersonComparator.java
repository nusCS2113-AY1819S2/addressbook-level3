package seedu.addressbook.data.person;
import java.util.Comparator;
/**
 * Compares persons' name alphabetically*
 */

public class PersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person p1, Person p2) {
        return (p1.getName().getFullName()).compareTo(p2.getName().getFullName());
    }
}