package seedu.addressbook.data.person;

import java.util.Set;
import java.time.LocalDate;


import seedu.addressbook.data.tag.Tag;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    Name getName();
    int nameLength();
    Phone getPhone();
    Email getEmail();
    Address getAddress();
    Appointment getAppointment();
    Doctor getDoctor();
    LocalDate getLocalDate();
    void setLocalDate(LocalDate date);
    Status getStatus();

    /**
     * The returned {@code Set} is a deep copy of the internal {@code Set},
     * changes on the returned list will not affect the person's internal tags.
     */
    Set<Tag> getTags();

    /**
     * Returns true if the values inside this object is same as those of the other (Note: interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
//                && other.getAppointment().equals(this.getAppointment())
//                && other.getDoctor().equals(this.getDoctor())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsTextShowAll() {
        final StringBuilder builder = new StringBuilder();
        final String detailIsPrivate = "(private) ";
        builder.append(getName())
                .append(" Phone: ");
        if (getPhone().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getPhone())
                .append(" Email: ");
        if (getEmail().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getEmail())
                .append(" Address: ");
        if (getAddress().isPrivate()) {
            builder.append(detailIsPrivate);
        }
        builder.append(getAddress()).append(" Appointment: ");
        builder.append(getAppointment());
        builder.append(" Doctor: ");
        builder.append(getDoctor());
        builder.append(" Status: ");
        builder.append(getStatus())
                .append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }

    default String getAsTextNameDateDoctor() {
        final StringBuilder builder = new StringBuilder();
        int num = 30 - nameLength();
        System.out.println(nameLength());
//        int initial = 30;
//        while (num <= 0) {
//            num = initial - nameLength();
//            initial = initial + 5;
//        }
        builder.append(String.format("%1$-" + num + "s", getName()) + "\t");
        builder.append(" Appointment: ");
        builder.append(getAppointment() + "\t");
        builder.append(" Doctor: ");
        builder.append(getDoctor());
        return builder.toString();
    }
    /**
     * Formats a person as text, showing only non-private contact details.
     */
    default String getAsTextHidePrivate() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (!getPhone().isPrivate()) {
            builder.append(" Phone: ").append(getPhone());
        }
        if (!getEmail().isPrivate()) {
            builder.append(" Email: ").append(getEmail());
        }
        if (!getAddress().isPrivate()) {
            builder.append(" Address: ").append(getAddress());
        }
        builder.append("Appointment: ").append(getAppointment());
        builder.append(" Doctor: ").append(getDoctor());
        builder.append(" Status: ").append(getStatus());
        builder.append(" Tags: ");
        for (Tag tag : getTags()) {
            builder.append(tag);
        }
        return builder.toString();
    }
}
