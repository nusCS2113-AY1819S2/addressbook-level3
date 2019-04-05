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

import seedu.addressbook.data.player.ReadOnlyPlayer;

/**
 * this class helps to convert player profiles correctly into excel file
 */
public class PlayerApachePoiWriter {

    private final String outputFilepath = "exported_player_record.xls";
    private final Path exportPath = Paths.get(outputFilepath);

    private final List<ReadOnlyPlayer> allPlayers;

    public PlayerApachePoiWriter(List<ReadOnlyPlayer> allPlayers) {

        requireNonNull(allPlayers);
        allPlayers.forEach(Objects::requireNonNull);

        if (!(Files.exists(exportPath) && Files.isRegularFile(exportPath))) {
            try {
                Files.createFile(exportPath);
            } catch (IOException ioe) {
                System.out.println("Error creating output file: " + exportPath.toString());
            }
        } else {
            System.out.println("Initializing exportPlayer output file: " + exportPath.toString());
        }
        this.allPlayers = allPlayers;
    }


    /**
     * writes player profiles to the destination excel sheet
     */
    public void write() {
        try {
            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet playerSheet = workBook.createSheet("PlayerSheet");
            HSSFRow row = playerSheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Index Number");
            cell = row.createCell(1);
            cell.setCellValue("Name");
            cell = row.createCell(2);
            cell.setCellValue("Team Name");
            cell = row.createCell(3);
            cell.setCellValue("Position Played");
            cell = row.createCell(4);
            cell.setCellValue("Age");
            cell = row.createCell(5);
            cell.setCellValue("Salary");
            cell = row.createCell(6);
            cell.setCellValue("Goals Scored");
            cell = row.createCell(7);
            cell.setCellValue("Goals Assisted");
            cell = row.createCell(8);
            cell.setCellValue("Nationality");
            cell = row.createCell(9);
            cell.setCellValue("Jersey Number");
            cell = row.createCell(10);
            cell.setCellValue("Appearance");
            cell = row.createCell(11);
            cell.setCellValue("Health Status");

            int num = allPlayers.size();

            for (int i = 1; i <= num; i++) {
                ReadOnlyPlayer playerNow = allPlayers.get(i - 1);

                row = playerSheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(playerNow.getName().toString());
                cell = row.createCell(2);
                cell.setCellValue(playerNow.getTeamName().toString());
                cell = row.createCell(3);
                cell.setCellValue(playerNow.getPositionPlayed().toString());
                cell = row.createCell(4);
                cell.setCellValue(playerNow.getAge().toString());
                cell = row.createCell(5);
                cell.setCellValue(playerNow.getSalary().toString());
                cell = row.createCell(6);
                cell.setCellValue(playerNow.getGoalsScored().toString());
                cell = row.createCell(7);
                cell.setCellValue(playerNow.getGoalsAssisted().toString());
                cell = row.createCell(8);
                cell.setCellValue(playerNow.getNationality().toString());
                cell = row.createCell(9);
                cell.setCellValue(playerNow.getJerseyNumber().toString());
                cell = row.createCell(10);
                cell.setCellValue(playerNow.getAppearance().toString());
                cell = row.createCell(11);
                cell.setCellValue(playerNow.getHealthStatus().toString());
            }

            for (int j = 0; j <= 11; j++) {
                playerSheet.autoSizeColumn(j);
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

    public Path getExportPath() {
        return exportPath;
    }

}
