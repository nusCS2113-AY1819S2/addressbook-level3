package seedu.addressbook.data.person;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.time.LocalDate;


import seedu.addressbook.data.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Appointment appointment;
    private Doctor doctor;
    public LocalDate date = null;


    private final Set<Tag> tags = new HashSet<>();
    /**
     * Assumption: Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Appointment appointment, Doctor doctor, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.appointment = appointment;
        this.doctor =doctor;
        this.tags.addAll(tags);
    }

    /**
     * Copy constructor.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getAppointment(), source.getDoctor(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public int nameLength(){
        return name.getNameLength();
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public Appointment getAppointment() {
        return appointment;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Doctor getDoctor() {
        return doctor;
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    /**
     * Replaces this person's tags with the tags in {@code replacement}.
     */
    public void setTags(Set<Tag> replacement) {
        tags.clear();
        tags.addAll(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, appointment, doctor, tags);
    }
    @Override
    public LocalDate getLocalDate(){
        return date;
    }

    @Override
    public void setLocalDate(LocalDate date){
        this.date = date;
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

}

//new class, return person.getLocalDate().compareTo(person2.getLocalDate())