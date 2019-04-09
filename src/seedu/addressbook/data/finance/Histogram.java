package seedu.addressbook.data.finance;

/**
 * build histogram of finance value.
 */
public class Histogram {
    public static final int LENGTH = 10;
    public static final int WIDTH = 10;
    public static final int MAX_HEIGHT = 8;
    public static final String SET_Y_AXIS = "   |";
    public static final String SET_X_AXIS = "______";
    public static final String PRINT_COLUMN = "|xxx|";
    public static final String PRINT_BLANK_SPACE = "        ";
    public static final String Y_AXIS = "Finance(USD) \n";

    private int numberOfColumn;
    private double heightOne;
    private double heightTwo;
    private double heightThree;
    private double heightFour;

    public Histogram (int numberOfColumn, double heightOne, double heightTwo, double heightThree, double heightFour) {
        this.numberOfColumn = numberOfColumn;
        this.heightOne = heightOne;
        this.heightTwo = heightTwo;
        this.heightThree = heightThree;
        this.heightFour = heightFour;
    }

    public String getHistogramString() {
        int x = numberOfColumn;
        double yOne = heightOne;
        double yTwo = heightTwo;
        double yThree = heightThree;
        double yFour = heightFour;

        /**
         * process values to fit height in Histogram.
         */
        double maxAmongFour = Math.max(Math.max(Math.max(yOne, yTwo), yThree), yFour);
        double yOneToFitHeight = (yOne / maxAmongFour) * MAX_HEIGHT;
        int yOneInt = (int) Math.round(yOneToFitHeight);
        double yTwoToFitHeight = (yTwo / maxAmongFour) * MAX_HEIGHT;
        int yTwoInt = (int) Math.round(yTwoToFitHeight);
        double yThreeToFitHeight = (yThree / maxAmongFour) * MAX_HEIGHT;
        int yThreeInt = (int) Math.round(yThreeToFitHeight);
        double yFourToFitHeight = (yFour / maxAmongFour) * MAX_HEIGHT;
        int yFourInt = (int) Math.round(yFourToFitHeight);

        String resultString;
        String[][] twoDString = new String[LENGTH][WIDTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {

                if (j == 0) {
                    twoDString[i][j] = SET_Y_AXIS; //set the y axis
                } else if (j == 9) {
                    twoDString[i][j] = "\n"; //end the line
                } else if (x == 4 & j == 2 & i == 9) {
                    twoDString[i][j] = "_^1 "; //set the first column name
                } else if (x == 4 & j == 4 & i == 9) {
                    twoDString[i][j] = "_^2 "; //set the second column name
                } else if (x == 4 & j == 6 & i == 9) {
                    twoDString[i][j] = "_^3 "; //set the third column name
                } else if (x == 4 & j == 8 & i == 9) {
                    twoDString[i][j] = "_^4 ___ Quarter"; //set the fourth column name
                } else if (i == 9) {
                    twoDString[i][j] = SET_X_AXIS; //set the x axis
                } else if (x == 4 & j == 2 & i >= (9 - yOneInt)) {
                    twoDString[i][j] = PRINT_COLUMN; //print the first column
                } else if (x == 4 & j == 4 & i >= (9 - yTwoInt)) {
                    twoDString[i][j] = PRINT_COLUMN; //print the second column
                } else if (x == 4 & j == 6 & i >= (9 - yThreeInt)) {
                    twoDString[i][j] = PRINT_COLUMN; //print the third column
                } else if (x == 4 & j == 8 & i >= (9 - yFourInt)) {
                    twoDString[i][j] = PRINT_COLUMN; //print the fourth column
                } else {
                    twoDString[i][j] = PRINT_BLANK_SPACE; //print the blank space
                }

            }
        }

        /**
         * build the 2D string to a string.
         */
        StringBuilder sb = new StringBuilder();
        for (String[] s1 : twoDString) {
            for (String s2 : s1) {
                sb.append(s2);
            }
        }

        resultString = Y_AXIS + sb.toString();

        return resultString;
    }
}
