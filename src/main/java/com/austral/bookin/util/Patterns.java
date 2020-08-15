package com.austral.bookin.util;

public class Patterns {

    /**
     * ^                 # start-of-string.
     * (?=.*[0-9])       # a digit must occur at least once.
     * (?=.*[a-z])       # a lower case letter must occur at least once.
     * (?=.*[A-Z])       # an upper case letter must occur at least once.
     * (?=.*[@#$%^&+=])  # a special character must occur at least once.
     * (?=\S+$)          # no whitespace allowed in the entire string.
     * .{8,50}           # anything, at least eight places and maximum fifty.
     * $                 # end-of-string.
     */
    public final static String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$";

    /**
     * ^                           # Start of string
     * [_A-Za-z0-9-\+]             # match any word
     * +                           # match prev exp at least once
     * (\.[_A-Za-z0-9-]+)          # match any word starting with a literal . including - and _
     * *                           # match prev exp 0 or inf times
     * @                           # match @ char
     * [A-Za-z0-9-]                # match any word
     * +                           # match prev exp at least once
     * (\.[A-Za-z0-9]+)            # match any word but not including -
     * *                           # match prev exp 0 or inf times
     * (\.[A-Za-z]{2,})            # match string starting with . being at least 2 chars long to 4 chars
     * $                           # End of string
     */

    public final static String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";
}
