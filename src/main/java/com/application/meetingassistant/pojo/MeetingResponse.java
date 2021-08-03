package com.application.meetingassistant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResponse {

   private MeetingRequest meetingRequest;

   private Integer meetingId;

}
