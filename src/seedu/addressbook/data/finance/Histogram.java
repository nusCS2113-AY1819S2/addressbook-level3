package seedu.addressbook.data.finance;

/**
 * build histogram of finance value.
 */
public class Histogram {
    public static final int LENGTH = 10;
    public static final int WIDTH = 10;
    public static final String HORIZONTAL_LINE = "____";
    public static final String VERTICAL_LINE = "|";
    public static final String EMPTY_SPACE = "    ";

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
        double yOneToFitHeight = (yOne / maxAmongFour) * 9;
        int yOneInt = (int) Math.round(yOneToFitHeight);
        double yTwoToFitHeight = (yTwo / maxAmongFour) * 9;
        int yTwoInt = (int) Math.round(yTwoToFitHeight);
        double yThreeToFitHeight = (yThree / maxAmongFour) * 9;
        int yThreeInt = (int) Math.round(yThreeToFitHeight);
        double yFourToFitHeight = (yFour / maxAmongFour) * 9;
        int yFourInt = (int) Math.round(yFourToFitHeight);

        String resultString;
        String[][] twoDString = new String[LENGTH][WIDTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {

                if (j == 0) {
                    twoDString[i][j] = "|"; //set the y axis
                } else if (j == 9) {
                    twoDString[i][j] = "\n"; //end the line
                } else if (x == 4 & j == 2 & i == 9) {
                    twoDString[i][j] = "_▲1"; //set the first column name
                } else if (x == 4 & j == 4 & i == 9) {
                    twoDString[i][j] = "_▲2"; //set the second column name
                } else if (x == 4 & j == 6 & i == 9) {
                    twoDString[i][j] = "_▲3"; //set the third column name
                } else if (x == 4 & j == 8 & i == 9) {
                    twoDString[i][j] = "_▲4"; //set the fourth column name
                } else if (i == 9) {
                    twoDString[i][j] = "____"; //set the x axis
                } else if (x == 4 & j == 2 & i >= (9 - yOneInt)) {
                    twoDString[i][j] = "▓▓▓"; //print the first column
                } else if (x == 4 & j == 4 & i >= (9 - yTwoInt)) {
                    twoDString[i][j] = "▓▓▓"; //print the second column
                } else if (x == 4 & j == 6 & i >= (9 - yThreeInt)) {
                    twoDString[i][j] = "▓▓▓"; //print the third column
                } else if (x == 4 & j == 8 & i >= (9 - yFourInt)) {
                    twoDString[i][j] = "▓▓▓"; //print the fourth column
                } else {
                    twoDString[i][j] = "░░░"; //print the blank space
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

        resultString = sb.toString();

        return resultString;
    }
}
