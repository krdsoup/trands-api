package kr.co.trands.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DateUtil {

	public static final String YEAR_FORMAT = "yyyy";
	public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_MILLISEC_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATETIME_T_FORMAT = "yyyy-MM-ddTHH:mm:ss";
    public static final String TIMESTAMP_DATE_FORMAT = "yyyyMMdd";
    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
    
    public static final String ZONE_UTC = "UTC";
    public static final String ZONE_SEOUL = "Asia/Seoul";

    public long diffHour(ZonedDateTime startDate, ZonedDateTime endDate) {
        return ChronoUnit.HOURS.between(startDate, endDate);
    }

    public long diffMin(ZonedDateTime startDate, ZonedDateTime endDate) {
        return ChronoUnit.MINUTES.between(startDate, endDate);
    }

    public long diffSecond(ZonedDateTime startDate, ZonedDateTime endDate) {
        return ChronoUnit.SECONDS.between(startDate, endDate);
    }

    public ZonedDateTime createUtcDateTime() {
        return ZonedDateTime.now(ZoneId.of(ZONE_UTC));
    }

    public ZonedDateTime createKorDateTime() {
        return ZonedDateTime.now(ZoneId.of(ZONE_SEOUL));
    }

}
