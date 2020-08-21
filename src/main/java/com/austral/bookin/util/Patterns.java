package com.austral.bookin.util;

public class Patterns {

    /**
     * ^                 # start-of-string.
     * [\w-\.]{3,50}     # any alphanumeric value, _, - or . but only between 3 and 50 characters.
     * @                 # one @ in that position.
     * ([\w-]+\.)        # any alphanumeric value, _, - or .
     * [\w-]{2,4}        # any alphanumeric value, _ or - but only 2, 3 or 4 characters.
     * $                 # end-of-string.
     */
    public final static String PATTERN_EMAIL = "^[\\w-\\.]{3,50}@([\\w-]+\\.)+[\\w-]{2,4}$";

    /**
     *
     * ^                    Start of string
     * [A-Za-z]             ANY uppercase letter and any lowercase letter
     * +                    Must be 1 or inf operand of previous exp
     * S                    end of string
     *
     */
    public final static String PATTERN_NAME = "^[A-Za-z]+$";

    /**
     * ^                 # start-of-string.
     * (?=.*[0-9])       # a digit must occur at least once.
     * (?=.*[A-Za-z])    # any case letter must occur at least once.
     * (?=\S+$)          # no whitespace allowed in the entire string.
     * .{6,50}           # anything, at least six places and maximum fifty.
     * $                 # end-of-string.
     * /i                # ignore case
     */
    public final static String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[A-Za-z])(?=\\S+$).{6,50}$";


    /**
     *  MUST BE EITHER M or F or A ONLY ONCE...
     */
    public final static String PATTERN_GENDER = "^[MFA]{1}$";
}
