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
     * ^                 # start-of-string.
     * [a-zA-Z]          # any alphanumeric value
     * (?:[\s]           # space
     * $                 # end-of-string.
     */
    public static final String PATTERN_NAME = "^([a-zA-Z]+(?:[\\s]+[a-zA-Z]+)*){3,30}$";
}