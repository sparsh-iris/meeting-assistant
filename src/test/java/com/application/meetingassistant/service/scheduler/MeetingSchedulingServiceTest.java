package com.application.meetingassistant.service.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.application.meetingassistant.CalendarMeetingAssistantApplication;
import com.application.meetingassistant.dao.scheduler.repository.MeetingRepository;
import com.application.meetingassistant.dto.Meeting;
import com.application.meetingassistant.pojo.FreeSlotsRequest;
import com.application.meetingassistant.pojo.FreeSlotsResponse;
import com.application.meetingassistant.pojo.MeetingRequest;
import com.application.meetingassistant.pojo.MeetingResponse;

@SpringBootTest(classes = CalendarMeetingAssistantApplication.class)
class MeetingSchedulingServiceTest {

   @Autowired
   private IMeetingSchedulingService iMeetingSchedulingService;

   @Autowired
   private MeetingRepository meetingRepository;

   @Test
   @Transactional
   @Rollback
   void scheduleMeeting() {
      List<Integer> calendarIds = new ArrayList<Integer>();
      calendarIds.add(1);
      calendarIds.add(2);
      calendarIds.add(3);
      MeetingResponse meetingResponse = iMeetingSchedulingService.scheduleMeeting(
            new MeetingRequest(calendarIds, LocalDateTime.of(2021, 8, 3, 20, 0), 180));
      Optional<Meeting> meeting = meetingRepository.findById(meetingResponse.getMeetingId());
      Assertions.assertNotNull(meeting.orElse(null));
   }

   @Test
   @Transactional
   @Rollback
   void findFreeSlots() {
      List<Integer> calendarIds = new ArrayList<Integer>();
      calendarIds.add(1);
      calendarIds.add(2);
      calendarIds.add(3);
      List<FreeSlotsResponse> freeSlotsResponses = iMeetingSchedulingService.findFreeSlots(
            new FreeSlotsRequest(calendarIds, 60, LocalDateTime.now()));
      Assertions.assertFalse(freeSlotsResponses.isEmpty());
   }

   @Test
   @Transactional
   @Rollback
   void findMeetingConflicts() {
      List<String> employeeNames = iMeetingSchedulingService.findMeetingConflicts(17);
      List<String> expectedValues = new ArrayList<>();
      expectedValues.add("DummyFirstName1 DummyLastName1");
      expectedValues.add("DummyFirstName3 DummyLastName3");
      Assertions.assertTrue(employeeNames.containsAll(expectedValues));
   }
}