package com.afterpay.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;

/**
 * Util class used by Fraud detection application.
 * Created by hariharank12 on 17/05/20.
 */
public class FraudDetectionUtil {

    /**
     * Parses the date entered in string format and returns a date.
     *
     * @param date parse input date entered as string.
     * @return date of date type.
     */
    public static Instant parseDate(String date) {
        return Instant.parse(date);
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
