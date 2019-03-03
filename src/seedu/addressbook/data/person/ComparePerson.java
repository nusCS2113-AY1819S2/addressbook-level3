package seedu.addressbook.data.person;

import java.util.Comparator;

/**
 * Compares person by name
 */
public class ComparePerson implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2){
        return person1.getName().fullName.compareToIgnoreCase(person2.getName().fullName);
    }
}