package com.thegame.Restart;

/**
 * Created by Acid Burn  on 22.03.2016.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class which has Utility methods
 *
 */
public class Utility {
    private static Pattern pattern;
    private static Matcher matcher;
    //Phone Pattern
    private static final String Phone_Pattern="^[0-9-\\+]";




    /**
     * Validate Email with regular expression
     *
     * @param phone
     * @return true for Valid Email and false for Invalid Email
     */
    public static boolean validate(String phone) {
        pattern = Pattern.compile(Phone_Pattern);
        matcher = pattern.matcher(phone);
        return matcher.matches();

    }
    /**
     * Checks for Null String object
     *
     * @param txt
     * @return true for not null and false for null String object
     */
    public static boolean isNotNull(String txt){
        return txt!=null && txt.trim().length()>0 ? true: false;
    }
}

