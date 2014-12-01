package com.tibby.media;

/**
 * Created by tangjian on 29/11/14.
 * QQ:562980080
 * Email:tangjian19900607@gmail.com
 */
public class CommonUtil {
    public static String convertSecondsToTimeText(int seconds) {
        if (seconds > 60 * 60) {
            return "";
        }
        int minutePart = seconds / 60;
        int secondPart = seconds % 60;
        if (secondPart > 9) {
            return String.format(" %d:%d", minutePart, secondPart);
        } else {
            return String.format(" %d:0%d", minutePart, secondPart);
        }
    }
}
