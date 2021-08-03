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
public class FreeSlotsRequest {

   @NotNull(message = "Please specify calendarIds of Users") List<Integer> calendarIds;

   @NotNull(message = "Please specify druation of meeting in minutes") Integer durationInMinutes;

   @NotNull(message = "Please specify the date and time of meeting") LocalDateTime meetingDate;

}
