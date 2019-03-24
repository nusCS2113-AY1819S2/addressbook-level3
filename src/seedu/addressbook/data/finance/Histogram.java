package seedu.addressbook.data.finance;

/**
 * build histogram of finance value.
 */
public class Histogram {
    public static final int LENGTH = 10;
    public static final int WIDTH = 10;

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
        //final String HORIZONTAL_LINE = "____";
        //final String VERTICAL_LINE = "|";
        //final String EMPTY_SPACE = "    ";
        //final int LENGTH = 10;
        //final int WIDTH = 10;
        int x = numberOfColumn;
        double yOne = heightOne;
        double yTwo = heightTwo;
        double yThree = heightThree;
        double yFour = heightFour;

        String resultString;
        String[][] twoDString = new String[LENGTH][WIDTH];

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {

                if (j == 0) {
                    twoDString[i][j] = "|";
                } else if (j == 9) {
                    twoDString[i][j] = "\n";
                } else if (i == 9) {
                    twoDString[i][j] = "____";
                } else {
                    twoDString[i][j] = "    ";
                }

            }
        }

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
