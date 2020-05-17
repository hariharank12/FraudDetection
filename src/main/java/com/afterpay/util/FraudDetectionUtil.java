package com.afterpay.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class used by Fraud detection application.
 * Created by hariharank12 on 17/05/20.
 */
public class FraudDetectionUtil {

    /**
     * Parses the date entered in string format and returns a date.
     * If error in format, returns null.
     *
     * @param date parse input date entered as string.
     * @return date of date type.
     */
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Computes md5 hash of a given string.
     *
     * @param input string input.
     * @return returns md5 hash of the input.
     */
    public static String md5Hash(String input) {
        return DigestUtils.md5Hex(input);
    }


}
