package com.application.meetingassistant.dao.scheduler.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.application.meetingassistant.dao.scheduler.repository.CalendarRepository;
import com.application.meetingassistant.dao.scheduler.repository.MeetingRepository;
import com.application.meetingassistant.dto.Calendar;
import com.application.meetingassistant.dto.Meeting;
import com.application.meetingassistant.pojo.Duration;

import lombok.extern.log4j.Log4j2;

@Repository
@Transactional
@Log4j2
public class MeetingSchedulerDao implements IMeetingSchedulerDao {

   @Autowired
   private MeetingRepository meetingRepository;

   @Autowired
   private CalendarRepository calendarRepository;

   @Override
   public Meeting addMeeting(Meeting meeting, List<Integer> calendarIds, Date meetingDateTime) {
      meetingRepository.save(meeting);
      log.info("Meeting saved to DB :: {}", meeting.toString());
      for (Integer calendarId : calendarIds) {
         calendarRepository.save(new Calendar(calendarId, meeting.getMeetingId(), meetingDateTime));
         log.info("Calendar entry added to DB for the meetingId and calendarId : {} :: {}", meeting.getMeetingId(), calendarId);
      }
      return meeting;
   }

   @Override
   public Set<Integer> findAllMeetingIdsOfParticipants(List<Integer> calendarIdsOfParticipants, Set<Integer> meetingIds, Date date) {
      for (Integer calendarId : calendarIdsOfParticipants) {
         log.info("Finding all meeting ids for the calendarId : {} on date : {}", calendarId, date);
         List<Optional<Calendar>> calendarOfParticipant = calendarRepository.findByCalendarIdEqualsAndMeetingDateEquals(calendarId, date);
         if (!calendarOfParticipant.isEmpty()) {
            for (Optional<Calendar> optionalCalendar : calendarOfParticipant) {
               optionalCalendar.ifPresent(calendar -> meetingIds.add(calendar.getMeetingId()));
            }
         }
      }
      return meetingIds;
   }

   @Override
   public List<Duration> getMeetingDurations(Set<Integer> meetingIds) {
      List<Duration> durations = new ArrayList<>();
      for (Integer meetingId : meetingIds) {
         Duration meetingDuration = getMeetingDuration(meetingId);
         log.info("Meeting duration for meetingId : {} is {}", meetingId, meetingDuration);
         Optional.of(meetingDuration).ifPresent(durations::add);
      }
      return durations;
   }

   @Override
   public Duration getMeetingDuration(Integer meetingId) {
      Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);
      return optionalMeeting.map(meeting -> new Duration(meeting.getStartTime(), meeting.getEndTime())).orElse(null);
   }

   @Override
   public List<Calendar> getParticipantsOfMeeting(Integer meetingId) {
      List<Calendar> calendarList = new ArrayList<>();
      List<Optional<Calendar>> optionalCalendarList = calendarRepository.findByMeetingId(meetingId);
      if (!optionalCalendarList.isEmpty()) {
         for (Optional<Calendar> optionalCalendar : optionalCalendarList) {
            optionalCalendar.ifPresent(calendarList::add);
         }
      }
      return calendarList;
   }

   @Override
   public void getMeetingsForParticipant(Set<Meeting> meetings, Integer calendarId) {
      List<Optional<Calendar>> optionalCalendar = calendarRepository.findByCalendarId(calendarId);
      if (!optionalCalendar.isEmpty()) {
         for (Optional<Calendar> calendar : optionalCalendar) {
            calendar.ifPresent(value -> meetings.add(meetingRepository.getById(value.getMeetingId())));
         }
      }
   }

   @Override
   public void findCalendarId(Integer meetingId, Set<Integer> calendarIdsWithConflicts) {
      List<Optional<Calendar>> optionalCalendar = calendarRepository.findByMeetingId(meetingId);
      if (!optionalCalendar.isEmpty()) {
         for (Optional<Calendar> calendar : optionalCalendar) {
            calendar.ifPresent(value -> calendarIdsWithConflicts.add(value.getCalendarId()));
         }
      }
   }

}
