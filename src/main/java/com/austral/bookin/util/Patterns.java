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
     * (?=.*[A-Za-z])    # any case letter must occur at least once.
     * (?=\S+$)          # no whitespace allowed in the entire string.
     * .{6,50}           # anything, at least six places and maximum fifty.
     * $                 # end-of-string.
     */
    public final static String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[A-Za-z])(?=\\S+$).{6,50}$";

    /**
     * ^                 # start-of-string.
     * [MFA]{1}          # Must be either M or F or A only once.
     * $                 # end-of-string.
     */
    public final static String PATTERN_GENDER = "^[MFA]{1}$";

    /**
     * ^                 # start-of-string.
     * [a-zA-Z]          # any alphabetic value
     * (?:[\s]           # space
     * $                 # end-of-string.
     */
    public static final String PATTERN_NAME = "^([A-Za-zÀ-ÖØ-öø-ÿ]+(?:[\\s]+[A-Za-zÀ-ÖØ-öø-ÿ]+)*){3,30}$";

    /**
     * ^                 # start-of-string.
     * [\w\s]            # any alphanumeric value and spaces
     * $                 # end-of-string.
     */
    public static final String PATTERN_TITLE = "^([A-Za-zÀ-ÖØ-öø-ÿ]){3,30}$";

    /**
     * ^                 # start-of-string.
     * [a-zA-Z]          # any alphabetic value
     * (?:[\s]           # space
     * $                 # end-of-string.
     */
    public static final String PATTERN_GENRE = "^([a-zA-Z]+(?:[\\s]+[a-zA-Z]+)*)$";

    /**
     * ^                 # start-of-string.
     * A-Za-zÀ-ÖØ-öø-ÿ   # any alphabetic value
     * $                 # end-of-string.
     */
    public static final String PATTERN_LANGUAGE = "^([A-Za-zÀ-ÖØ-öø-ÿ]+)$";
}