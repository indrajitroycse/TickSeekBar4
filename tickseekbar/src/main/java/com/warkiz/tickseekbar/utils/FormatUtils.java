package com.warkiz.tickseekbar.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Format util class.
 */
public class FormatUtils {
    private static final double DELTA = 0.5d;

    private static final char[][] LEADING_DECIMALS = new char[][]{
            "0.".toCharArray(), "0.0".toCharArray(),
            "0.00".toCharArray(), "0.000".toCharArray(), "0.0000".toCharArray(),
            "0.00000".toCharArray(),
            "0.000000".toCharArray(), "0.0000000".toCharArray(), "0.00000000".toCharArray(),
            "0.000000000".toCharArray(), "0.0000000000".toCharArray(), "0.00000000000".toCharArray(),
            "0.000000000000".toCharArray(), "0.0000000000000".toCharArray(),
            "0.00000000000000".toCharArray(),
            "0.000000000000000".toCharArray()
    };

    private FormatUtils() {
    }

    /**
     * format a double value quickly, will remove the suffix:0.
     */
    public static String fastFormat(double doubleValue, int precision) {
        int posPrecision = Math.abs(precision);
        double roundUpVal = Math.abs(doubleValue) * Math.pow(TickSeekBarConstants.POWER_BASE_VALUE, posPrecision) + DELTA;
        if (roundUpVal > TickSeekBarConstants.FORMAT_LITERAL || posPrecision > TickSeekBarConstants.DP_VALUE) {
            return bigDecFormat(doubleValue, posPrecision);
        }
        long longPart = (long) Math.nextUp(roundUpVal);
        if (longPart < 1) {
            return "0";
        }
        char[] longPartChars = Long.toString(longPart).toCharArray();
        char[] formatChars;
        if (longPartChars.length > posPrecision) {
            int end = longPartChars.length - 1;
            int decIndex = longPartChars.length - posPrecision;
            while (end >= decIndex && longPartChars[end] == '0') {
                end--;
            }
            if (end >= decIndex) {
                formatChars = new char[end + 2];
                System.arraycopy(longPartChars, 0, formatChars, 0, decIndex);
                formatChars[decIndex] = '.';
                System.arraycopy(longPartChars, decIndex, formatChars,
                        decIndex + 1, end - decIndex + 1);
            } else {
                formatChars = new char[decIndex];
                System.arraycopy(longPartChars, 0, formatChars, 0, decIndex);
            }
        } else {
            int end = longPartChars.length - 1;
            while (end >= 0 && longPartChars[end] == '0') {
                end--;
            }
            char[] leadings = LEADING_DECIMALS[posPrecision - longPartChars.length];
            formatChars = Arrays.copyOf(leadings, leadings.length + end + 1);
            System.arraycopy(longPartChars, 0, formatChars, leadings.length, end + 1);
        }
        return Math.signum(doubleValue) > 0 ? new String(formatChars) : "-" + new String(formatChars);
    }

    private static String bigDecFormat(double doubleValue, int precision) {
        String formatStr = new BigDecimal(Double.toString(doubleValue)).setScale(Math.abs(precision), RoundingMode.HALF_UP)
                .toString();
        if (precision == 0) {
            return formatStr;
        }
        int end = formatStr.length() - 1;
        while (end >= 0 && formatStr.charAt(end) == '0') {
            end--;
        }
        formatStr = formatStr.substring(0, end + 1);
        if (formatStr.charAt(formatStr.length() - 1) == '.') {
            formatStr = formatStr.substring(0, formatStr.length() - 1);
        }
        return formatStr;
    }
}