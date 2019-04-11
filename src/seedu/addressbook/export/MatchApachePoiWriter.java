package seedu.addressbook.export;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import seedu.addressbook.data.match.ReadOnlyMatch;
import seedu.addressbook.data.player.Name;

/**
 * this class helps to convert match information correctly into excel file
 */
public class MatchApachePoiWriter {

    private final String outputFilepath = "exported_match_record.xls";
    private final Path exportPath = Paths.get(outputFilepath);

    private final List<ReadOnlyMatch> allMatches;

    public MatchApachePoiWriter(List<ReadOnlyMatch> allMatches) {

        requireNonNull(allMatches);
        allMatches.forEach(Objects::requireNonNull);

        if (!(Files.exists(exportPath) && Files.isRegularFile(exportPath))) {
            try {
                Files.createFile(exportPath);
            } catch (IOException ioe) {
                System.out.println("Error creating output file: " + exportPath.toString());
            }
        } else {
            System.out.println("Initializing exportMatch output file: " + exportPath.toString());
        }
        this.allMatches = allMatches;
    }


    /**
     * writes match information to the destination excel sheet
     */
    public void write() {
        try {
            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet matchSheet = workBook.createSheet("MatchSheet");
            HSSFRow row = matchSheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Index Number");
            cell = row.createCell(1);
            cell.setCellValue("Date");
            cell = row.createCell(2);
            cell.setCellValue("Home Team");
            cell = row.createCell(3);
            cell.setCellValue("Away Team");
            cell = row.createCell(4);
            cell.setCellValue("Ticket Sales going to Home Team");
            cell = row.createCell(5);
            cell.setCellValue("Ticket Sales going to Away Team");
            cell = row.createCell(6);
            cell.setCellValue("Goal Scorers");
            cell = row.createCell(7);
            cell.setCellValue("Own Goal Scorers");

            int num = allMatches.size();

            for (int i = 1; i <= num; i++) {
                ReadOnlyMatch matchNow = allMatches.get(i - 1);
                List<Name> goalScorers = matchNow.getGoalScorers();
                List<Name> ownGoalScorers = matchNow.getOwnGoalScorers();
                String gsString = createGoalScorerString(goalScorers);
                String ogsString = createOwnGoalScorerString(ownGoalScorers);

                row = matchSheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(matchNow.getDate().toString());
                cell = row.createCell(2);
                cell.setCellValue(matchNow.getHome().toString());
                cell = row.createCell(3);
                cell.setCellValue(matchNow.getAway().toString());
                cell = row.createCell(4);
                cell.setCellValue(matchNow.getHomeSales().toString());
                cell = row.createCell(5);
                cell.setCellValue(matchNow.getAwaySales().toString());
                cell = row.createCell(6);
                cell.setCellValue(gsString);
                cell = row.createCell(7);
                cell.setCellValue(ogsString);

            }

            for (int j = 0; j <= 7; j++) {
                matchSheet.autoSizeColumn(j);
            }

            try {
                workBook.write(new FileOutputStream(outputFilepath));
                workBook.close();
            } catch (FileNotFoundException ffe) {
                System.out.println("File specified by the outputFilePath does not exist");
                throw new FileNotFoundException();
            } catch (IOException ioe) {
                System.out.println("Error writing data to " + outputFilepath);
                throw new IOException();
            }

        } catch (NullPointerException npe) {
            System.out.println("Please check your parameters. npe happening");
            throw new NullPointerException();
        } catch (FileNotFoundException ffe) {
            System.out.println("File not found...");
        } catch (IOException ioe) {
            System.out.println("Error exporting to file");
        }
    }

    /**
     * get the current export path of the excel file
     * @return the current export path of the excel file
     */
    public Path getExportPath() {
        return exportPath;
    }

    /**
     * create the String of all goal scorers in a match
     * @param goalScorers a List of Name objects of all goal scorers in a match
     * @return a comma-separated string with names of all goalScorers in a match
     */
    private String createGoalScorerString(List<Name> goalScorers) {
        String finalGsString = "";

        for (Name name : goalScorers) {
            finalGsString = finalGsString.concat(name.toString() + ", ");
        }

        return finalGsString;
    }

    /**
     * create the String of all own goal scorers in a match
     * @param ownGoalScorers a List of Name objects of all own-goal scorers in a match
     * @return a comma-separated string with names of all own-goal scorers in a match
     */
    private String createOwnGoalScorerString(List<Name> ownGoalScorers) {
        String finalOgsString = "";

        for (Name name : ownGoalScorers) {
            finalOgsString = finalOgsString.concat(name.toString() + ", ");
        }

        return finalOgsString;
    }

}
