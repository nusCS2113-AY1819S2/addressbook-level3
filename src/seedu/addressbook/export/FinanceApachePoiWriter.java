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

import seedu.addressbook.data.finance.ReadOnlyFinance;

/**
 * this class helps to convert finance profiles correctly into excel file
 */

public class FinanceApachePoiWriter {

    private final String outputFilepath = "exported_finance_record.xls";
    private final Path exportPath = Paths.get(outputFilepath);

    private final List<ReadOnlyFinance> allFinances;

    public FinanceApachePoiWriter(List<ReadOnlyFinance> allFinances) {

        requireNonNull(allFinances);
        allFinances.forEach(Objects::requireNonNull);

        if (!(Files.exists(exportPath) && Files.isRegularFile(exportPath))) {
            try {
                Files.createFile(exportPath);
            } catch (IOException ioe) {
                System.out.println("Error creating output file: " + exportPath.toString());
            }
        } else {
            System.out.println("Initializing exportFinance output file: " + exportPath.toString());
        }
        this.allFinances = allFinances;
    }


    /**
     * writes finance records to the destination excel sheet
     */
    public void write() {
        try {
            HSSFWorkbook workBook = new HSSFWorkbook();
            HSSFSheet financeSheet = workBook.createSheet("League Finance Sheet");
            HSSFRow row = financeSheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("Index Number");
            cell = row.createCell(1);
            cell.setCellValue("Team Name");
            cell = row.createCell(2);
            cell.setCellValue("Amount of Sponsorship Received");
            cell = row.createCell(3);
            cell.setCellValue("Amount of Ticket Income");
            cell = row.createCell(4);
            cell.setCellValue("Amount of Total Income");
            cell = row.createCell(5);
            cell.setCellValue("Amount of Q1 Income");
            cell = row.createCell(6);
            cell.setCellValue("Amount of Q2 Income");
            cell = row.createCell(7);
            cell.setCellValue("Amount of Q3 Income");
            cell = row.createCell(8);
            cell.setCellValue("Amount of Q4 Income");

            int num = allFinances.size();

            for (int i = 1; i <= num; i++) {
                ReadOnlyFinance financeNow = allFinances.get(i - 1);

                row = financeSheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(financeNow.getTeamName());
                cell = row.createCell(2);
                cell.setCellValue(financeNow.getSponsor());
                cell = row.createCell(2);
                cell.setCellValue(financeNow.getTicketIncome());
                cell = row.createCell(4);
                cell.setCellValue(financeNow.getFinance());
                cell = row.createCell(5);
                cell.setCellValue(financeNow.getQuarterOne());
                cell = row.createCell(6);
                cell.setCellValue(financeNow.getQuarterTwo());
                cell = row.createCell(7);
                cell.setCellValue(financeNow.getQuarterThree());
                cell = row.createCell(8);
                cell.setCellValue(financeNow.getQuarterFour());

            }

            for (int j = 0; j <= 8; j++) {
                financeSheet.autoSizeColumn(j);
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

}
