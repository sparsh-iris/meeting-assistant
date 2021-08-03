package com.application.meetingassistant.util;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The type Utility.
 */
public class Utility {

   private Utility() {

   }

   /**
    * Convert time stamp to date time local date time.
    *
    * @param timestampInSeconds the timestamp in seconds
    * @return the local date time
    */
   public static LocalDateTime convertTimeStampToDateTime(long timestampInSeconds) {
      return Instant.ofEpochSecond(timestampInSeconds).atZone(ZoneOffset.UTC).toLocalDateTime();
   }

   /**
    * Convert date time to time stamp long.
    *
    * @param localDateTime the local date time
    * @return the long
    */
   public static Long convertDateTimeToTimeStamp(LocalDateTime localDateTime) {
      return localDateTime.atZone(ZoneOffset.UTC).toEpochSecond();
   }

   /**
    * Convert local date time to sql date.
    *
    * @param localDateTime the local date time
    * @return the date
    */
   public static Date convertLocalDateTimeToSql(LocalDateTime localDateTime) {
      return Date.valueOf(localDateTime.toLocalDate());
   }

}
