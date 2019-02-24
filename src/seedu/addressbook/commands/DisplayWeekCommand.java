package seedu.addressbook.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Calendar;
        
/**
 * Displays current week of the semester based on today's date.
 */
public class DisplayWeekCommand extends Command {

    public static final String COMMAND_WORD = "displayweek";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Displays the current week of the semester based on today's date.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DISPLAY_CURRENT_WEEK = "You are in %s of %s, %s";
    public static final String MESSAGE_ACAD_CAL_NOT_FOUND = "Unable to find academic calendar file.";
    public static final String MESSAGE_ACAD_CAL_READ_ERROR = "Unable to read academic calendar file.";

    @Override
    public CommandResult execute() {
        try {
            String filePath = "academiccalendar.txt";
            Map<String, String> acadCalMap = new HashMap<>();
            Stream<String> lines = Files.lines(Paths.get(filePath));
            acadCalMap = lines.collect(Collectors.toMap(key -> key.split(":")[0], val -> val.split(":")[1]));
            
            Calendar cal = Calendar.getInstance();
            String calYearWeek = Integer.toString(cal.get(Calendar.YEAR)) + "-" + Integer.toString(cal.get(Calendar.WEEK_OF_YEAR));
            String acadCalWeekYearSem = acadCalMap.get(calYearWeek);
            
            String acadCalWeek = acadCalWeekYearSem.split("_")[0];
            String acadCalYear = acadCalWeekYearSem.split("_")[1];
            String acadCalSem = acadCalWeekYearSem.split("_")[2];

            return new CommandResult(String.format(MESSAGE_DISPLAY_CURRENT_WEEK, acadCalWeek, acadCalYear, acadCalSem));
        } catch (FileNotFoundException fnfe) {
            return new CommandResult(MESSAGE_ACAD_CAL_NOT_FOUND);
        } catch (IOException ioe) {
            return new CommandResult(MESSAGE_ACAD_CAL_READ_ERROR);
        }
    }
}
