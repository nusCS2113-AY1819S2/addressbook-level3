package seedu.addressbook.commands;


import seedu.addressbook.data.person.ReadOnlyPerson;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ApptDateCommand extends Command {

    public static final String COMMAND_WORD = "ApptDate";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all patients who have appointments with a certain doctor on a certain date, and prints out a timeline";

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    public DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy MM d kk mm");

    public String doctor;
    public LocalDate date;
    public static String timetable;
    int year;
    int month;
    int day;
    private static int hourTimesMin;
    private static int counter;
    private static LocalDateTime currentPersonDate;

    public ApptDateCommand(String doctor, String userdate) {
        this.doctor=doctor.trim();
        LocalDate date = LocalDate.parse(userdate, formatter);
        this.date = date;
        this.year=date.getYear();
        this.month=date.getMonthValue();
        this.day=date.getDayOfMonth();
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithSameDoctorSameDate();
        Indicator.setLastCommand("ApptDate");
        return new CommandResult(getMessageForAppointmentsShownSummary(personsFound, this.doctor), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithSameDoctorSameDate() {
        final Set<String> doctorKeyword = new HashSet<>(Arrays.asList(this.doctor));
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();

        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> DoctorName = new HashSet<>(person.getDoctor().getWordsInName());
            if (!Collections.disjoint(DoctorName, doctorKeyword)) {
                LocalDateTime date = LocalDateTime.parse(person.getAppointment().toString(), formatterTime);
                person.setLocalDateTime(date);
                if (date.getYear() == this.year & date.getMonthValue() == this.month & date.getDayOfMonth() == this.day){
                    matchedPersons.add(person);
                }
            }
        }
        Collections.sort(matchedPersons, new SortDate());
        //Different pathway if its zero matched persons.
        if (matchedPersons.isEmpty()){
            this.timetable = "\t\tYou have no appointments for the date:  "
                    + date
                    + "\n";
            for (int i =360; i <1440; i+=15) {
                timetable = timetable
                        + "   "
                        + String.format("%5d%s%d", (i / 60), ":", (i % 60));
                if (i % 60 == 0) {
                    timetable = timetable + 0;
                }
                timetable = timetable + String.format("%32s", "\t*\n");
            }
            return matchedPersons;
        }
        //add a duplicated fake person! So that the counter will not go out of index.
        matchedPersons.add(matchedPersons.get(0));
        System.out.println(4);
        String timetable ="\t\tThis is your Appointment timetable for the date: "
                + date
                + "\n";
        this.counter=0;
        this.currentPersonDate = matchedPersons.get(this.counter).getLocalDateTime();
        this.hourTimesMin = currentPersonDate.getHour() * 60 + currentPersonDate.getMinute();
        for (int i =360; i <1440; i+=15) {
            timetable= timetable
                    +"   "
                    + String.format("%5d%s%d", (i/60), ":", (i%60));
            if (i%60 ==0){
                timetable = timetable + 0;
            }
            if (i == this.hourTimesMin) {
                timetable = timetable
                        + String.format("%32s", "\t**")
                        + String.format("%s", "*****     ")
                        + String.format("%-70s", matchedPersons.get(this.counter).getName().toString())
                        + "\n";
                this.counter = this.counter + 1;
                this.currentPersonDate = matchedPersons.get(this.counter).getLocalDateTime();
                this.hourTimesMin = currentPersonDate.getHour() * 60 +  (currentPersonDate.getMinute());
            }
            else {
                timetable = timetable +String.format("%32s", "\t*\n");
            }
        }
        this.timetable = timetable;
        //removing the duplicated person from the method called.
        matchedPersons.remove(this.counter);
        return matchedPersons;
    }
}

