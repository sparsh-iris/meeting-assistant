package com.application.meetingassistant.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDetails {

   private Date timestamp;

   private String message;

   private String details;

   public ErrorDetails(Date timestamp, String message) {
      this.timestamp = timestamp;
      this.message = message;
   }
}
