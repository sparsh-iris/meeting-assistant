package com.application.meetingassistant.dao.scheduler;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.application.meetingassistant.CalendarMeetingAssistantApplication;
import com.application.meetingassistant.dao.scheduler.dao.IMeetingSchedulerDao;
import com.application.meetingassistant.dao.scheduler.repository.CalendarRepository;
import com.application.meetingassistant.dao.scheduler.repository.MeetingRepository;
import com.application.meetingassistant.dto.Calendar;
import com.application.meetingassistant.dto.Meeting;
import com.application.meetingassistant.pojo.Duration;

@SpringBootTest(classes = CalendarMeetingAssistantApplication.class)
class MeetingSchedulerDaoTest {

   @Autowired
   private IMeetingSchedulerDao iMeetingSchedulerDao;

   @Autowired
   private MeetingRepository meetingRepository;

   @Autowired
   private CalendarRepository calendarRepository;

   @Test
   @Transactional
   @Rollback
   void addMeeting() {
      Meeting meeting = new Meeting(LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond(),
            LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond() + 3600);
      ArrayList<Integer> calendarIds = new ArrayList<>();
      calendarIds.add(1);
      calendarIds.add(2);
      iMeetingSchedulerDao.addMeeting(meeting, calendarIds, new Date(System.currentTimeMillis()));
      Optional<Meeting> fetchedMeeting = meetingRepository.findById(meeting.getMeetingId());
      Optional<Calendar> calendar = calendarRepository.findById(calendarIds.get(0));
      Assertions.assertNotNull(fetchedMeeting.orElse(null));
      Assertions.assertEquals(fetchedMeeting.orElse(null), meeting);
   }

   @Test
   @Transactional
   @Rollback
   void findAllMeetingIdsOfParticipants() {
      List<Integer> ids = new ArrayList<>();
      ids.add(1);
      ids.add(2);
      ids.add(3);
      Set<Integer> meetingIds = new HashSet<>();
      iMeetingSchedulerDao.findAllMeetingIdsOfParticipants(ids, meetingIds, new Date(System.currentTimeMillis()));
      List<Integer> expectedValues = new ArrayList<>();
      expectedValues.add(17);
      expectedValues.add(4);
      expectedValues.add(7);
      expectedValues.add(10);
      expectedValues.add(13);
      Assertions.assertTrue(meetingIds.containsAll(expectedValues));
   }

   @Test
   @Transactional
   @Rollback
   void getMeetingDurations() {
      List<Duration> durations;
      Set<Integer> meetingIds = new HashSet<>();
      meetingIds.add(17);
      meetingIds.add(4);
      meetingIds.add(7);
      meetingIds.add(10);
      meetingIds.add(13);
      durations = iMeetingSchedulerDao.getMeetingDurations(meetingIds);
      Assertions.assertFalse(durations.isEmpty());
   }

   @Test
   @Transactional
   @Rollback
   void getMeetingDuration() {
      Assertions.assertNotNull(iMeetingSchedulerDao.getMeetingDuration(17));
   }

   @Test
   @Transactional
   @Rollback
   void getParticipantsOfMeeting() {
      Assertions.assertEquals(iMeetingSchedulerDao.getParticipantsOfMeeting(17).size(), 3);
   }

}