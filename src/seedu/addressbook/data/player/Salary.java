package seedu.addressbook.data.player;

import seedu.addressbook.data.exception.IllegalValueException;

/**
 * Represents a Player's salary.
 * Guarantees: immutable; is valid as declared in {@link #isValidSalary(String)}
 */
public class Salary {

        public static final String EXAMPLE = "100000";
        public static final String MESSAGE_Salary_CONSTRAINTS = "Player salary should be a positive number";
        public static final String SALARY_VALIDATION_REGEX = "\\d+";

        public final String value;


        /**
         * Validates given player salary.
         *
         * @throws IllegalValueException if given salary string is invalid.
         */
        public Salary(String salary) throws IllegalValueException {
            salary = salary.trim();
            if (!isValidSalary(salary)) {
                throw new IllegalValueException(MESSAGE_Salary_CONSTRAINTS);
            }
            this.value = salary;
        }

        /**
         * Checks if a given string is a valid player salary.
         */
        public static boolean isValidSalary(String test) {
            try {
                int i = Integer.parseInt(test);
                return (test.matches(SALARY_VALIDATION_REGEX) && i>=0);
            } catch(NumberFormatException nfe){
                return false;
            }
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof Salary // instanceof handles nulls
                    && this.value.equals(((Salary) other).value)); // state check
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }


    }


