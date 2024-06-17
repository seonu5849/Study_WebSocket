package com.example.websocket.config.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatUtils {

    public static String koreaTimeFormat(String utcTimeString) {
        // UTC 문자열을 ZonedDateTime 객체로 파싱
        ZonedDateTime utcDateTime = ZonedDateTime.parse(utcTimeString);
        // 한국 시간대로 변환
        ZonedDateTime koreaDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        // 한국 시간대 문자열로 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return koreaDateTime.format(formatter);
    }

    public static String formatTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime.format(DateTimeFormatter.ofPattern("a hh:mm"));
    }

}
