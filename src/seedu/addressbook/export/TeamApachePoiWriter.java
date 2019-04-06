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

import seedu.addressbook.data.team.ReadOnlyTeam;

/**
 * This class helps to extract data from Team profiles in league tracker
 * and correctly export to an excel sheet
 */
public class TeamApachePoiWriter {
    private final String outputFilepath = "exported_team_record.xls";
    private final Path exportPath = Paths.get(outputFilepath);

    private final List<ReadOnlyTeam> allTeams;

    public TeamApachePoiWriter(List<ReadOnlyTeam> allTeams) {

        requireNonNull(allTeams);
        allTeams.forEach(Objects::requireNonNull);

        if (!(Files.exists(exportPath) && Files.isRegularFile(exportPath))) {
            try {
                Files.createFile(exportPath);
            } catch (IOException ioe) {
                System.out.println("Error creating output file at: " + exportPath.toString());
            }
        } else {
            System.out.println("Initializing exportTeam output file: " + exportPath.toString());
        }
        this.allTeams = allTeams;
    }


    /**
     * writes team profiles to the destination excel sheet
     */
    public void write() {
        try {
            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet teamSheet = workBook.createSheet("teamSheet");
            HSSFRow row = teamSheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Index Number");
            cell = row.createCell(1);
            cell.setCellValue("Team Name");
            cell = row.createCell(2);
            cell.setCellValue("Country");
            cell = row.createCell(3);
            cell.setCellValue("Sponsor");
            cell = row.createCell(4);
            cell.setCellValue("Number of Players");

            int num = allTeams.size();



            for (int i = 1; i <= num; i++) {
                ReadOnlyTeam teamNow = allTeams.get(i - 1);

                row = teamSheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(teamNow.getTeamName().toString());
                cell = row.createCell(2);
                cell.setCellValue(teamNow.getCountry().toString());
                cell = row.createCell(3);
                cell.setCellValue(teamNow.getSponsor().toString());
                cell = row.createCell(4);
                cell.setCellValue(teamNow.getPlayers().size());
            }

            for (int j = 0; j <= 11; j++) {
                teamSheet.autoSizeColumn(j);
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
     * Get the Current Export path
     * @return the current export path of the excel xls file
     */
    public Path getExportPath() {
        return exportPath;
    }
}
