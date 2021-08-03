package com.application.meetingassistant.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.meetingassistant.pojo.FreeSlotsRequest;
import com.application.meetingassistant.pojo.FreeSlotsResponse;
import com.application.meetingassistant.pojo.MeetingRequest;
import com.application.meetingassistant.pojo.MeetingResponse;
import com.application.meetingassistant.service.scheduler.IMeetingSchedulingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

/**
 * The type Meeting scheduling controller.
 */
@RestController
@RequestMapping("/meeting")
@Log4j2
public class MeetingSchedulingController {

   @Autowired
   private IMeetingSchedulingService iMeetingSchedulingService;

   @Autowired
   private ObjectMapper objectMapper;

   /**
    * Schedule meeting with given details for the provided calendarIds
    *
    * @param meetingRequest the meeting request
    * @return the response entity
    */
   @PostMapping(value = "/bookMeeting", consumes = "application/json", produces = "application/json")
   public ResponseEntity<MeetingResponse> scheduleMeeting(@RequestBody @Valid MeetingRequest meetingRequest) {
      log.info("Request received to schedule meeting :: {}", meetingRequest);
      MeetingResponse scheduledMeetingResponse = iMeetingSchedulingService.scheduleMeeting(meetingRequest);
      return ResponseEntity.status(HttpStatus.OK).body(scheduledMeetingResponse);
   }

   /**
    * Find free slots response entity.
    *
    * @param freeSlotsRequest the free slots request
    * @return the response entity
    * @throws JsonProcessingException the json processing exception
    */
   @PostMapping(value = "/findFreeSlots", consumes = "application/json", produces = "application/json")
   public ResponseEntity<List<FreeSlotsResponse>> findFreeSlots(@RequestBody @Valid FreeSlotsRequest freeSlotsRequest)
         throws JsonProcessingException {
      log.info("Request received to find free slots :: {}", freeSlotsRequest);
      List<FreeSlotsResponse> freeSlots = iMeetingSchedulingService.findFreeSlots(freeSlotsRequest);
      log.info("Response sent back :: {}", objectMapper.writeValueAsString(freeSlots));
      return ResponseEntity.status(HttpStatus.OK).body(freeSlots);
   }

   /**
    * Gets meeting conflicts.
    *
    * @param meetingId the meeting id
    * @return the meeting conflicts
    * @throws JsonProcessingException the json processing exception
    */
   @GetMapping(value = "/getConflicts", produces = "application/json")
   public ResponseEntity<List<String>> getMeetingConflicts(@RequestParam @NotNull Integer meetingId) throws JsonProcessingException {
      log.info("Request received to find meeting conflicts for meeting id :: {}", meetingId);
      List<String> meetingConflicts = iMeetingSchedulingService.findMeetingConflicts(meetingId);
      log.info("Response sent back :: {}", objectMapper.writeValueAsString(meetingConflicts));
      return ResponseEntity.status(HttpStatus.OK).body(meetingConflicts);
   }

}
