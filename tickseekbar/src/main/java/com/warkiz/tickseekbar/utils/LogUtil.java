package com.warkiz.tickseekbar.utils;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * Log Utils to display HiLog.
 */
public class LogUtil {
    /**
     * LOG_TAG variable to use in HiLog.
     */
    public static final String LOG_TAG = "TSB";

    /**
     * LOG_LABEL variable to use in HiLog.
     */
    public static final HiLogLabel LOG_LABEL = new HiLogLabel(HiLog.DEBUG, 0x0, LOG_TAG);

    /**
     * LOG_FORMAT variable to use the specified string format in HiLog.
     */
    public static final String LOG_FORMAT = "%{public}s: %{public}s";

    private LogUtil() {
    }

    /**
     * debug method for HiLog.
     */
    public static void debug(String tag, String message) {
        HiLog.debug(LOG_LABEL, LOG_FORMAT, tag, message);
    }

    /**
     * info method for HiLog.
     */
    public static void info(String tag, String message) {
        HiLog.info(LOG_LABEL, LOG_FORMAT, tag, message);
    }

    /**
     * warn method for HiLog.
     */
    public static void warn(String tag, String message) {
        HiLog.warn(LOG_LABEL, LOG_FORMAT, tag, message);
    }

    /**
     * error method for HiLog.
     */
    public static void error(String tag, String message) {
        HiLog.error(LOG_LABEL, LOG_FORMAT, tag, message);
    }
}
