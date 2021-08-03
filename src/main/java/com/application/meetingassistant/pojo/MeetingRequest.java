package com.application.meetingassistant.pojo;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequest {

   @NotNull(message = "Please specify calendarIds of Users") List<Integer> calendarIds;

   @NotNull(message = "Please specify date and time of meeting") LocalDateTime meetingDateTime;

   @NotNull(message = "Please specify duration of meeting in minutes") Integer durationInMinutes;

}
