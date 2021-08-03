package com.application.meetingassistant.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeSlotsResponse {

   private LocalDateTime startTime;

   private LocalDateTime endTime;

}
