package com.application.meetingassistant.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingCalendarCustomException extends RuntimeException {

   private final String message;

   private String exceptionMessage;

   public MeetingCalendarCustomException(String message) {
      super(message);
      this.message = message;
   }

   public MeetingCalendarCustomException(String message, String exceptionMessage) {
      super(message);
      this.message = message;
      this.exceptionMessage = exceptionMessage;
   }
}
