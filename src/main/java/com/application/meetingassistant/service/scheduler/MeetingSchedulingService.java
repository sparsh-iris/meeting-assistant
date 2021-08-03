package com.application.meetingassistant.service.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.meetingassistant.dao.scheduler.dao.IMeetingSchedulerDao;
import com.application.meetingassistant.dao.scheduler.utils.MeetingSchedulerUtils;
import com.application.meetingassistant.dao.user.dao.IUserDao;
import com.application.meetingassistant.dto.Calendar;
import com.application.meetingassistant.dto.Meeting;
import com.application.meetingassistant.exception.MeetingCalendarCustomException;
import com.application.meetingassistant.pojo.Duration;
import com.application.meetingassistant.pojo.FreeSlotsRequest;
import com.application.meetingassistant.pojo.FreeSlotsResponse;
import com.application.meetingassistant.pojo.MeetingRequest;
import com.application.meetingassistant.pojo.MeetingResponse;
import com.application.meetingassistant.util.Utility;

@Service
public class MeetingSchedulingService implements IMeetingSchedulingService {

   @Autowired
   private IMeetingSchedulerDao iMeetingSchedulerDao;

   @Autowired
   private IUserDao iUserDao;

   @Override
   public MeetingResponse scheduleMeeting(MeetingRequest meetingRequest) {
      List<Integer> participantCalendarIds = meetingRequest.getCalendarIds();
      Long meetingStartTimeStamp = Utility.convertDateTimeToTimeStamp(meetingRequest.getMeetingDateTime());
      Long meetingEndTimeStamp = Utility.convertDateTimeToTimeStamp(meetingRequest.getMeetingDateTime()) + meetingRequest.getDurationInMinutes() * 60;
      Meeting meeting = new Meeting(meetingStartTimeStamp, meetingEndTimeStamp);
      try {
         iMeetingSchedulerDao.addMeeting(meeting, participantCalendarIds, Utility.convertLocalDateTimeToSql(meetingRequest.getMeetingDateTime()));
         return new MeetingResponse(meetingRequest, meeting.getMeetingId());
      } catch (Exception ex) {
         throw new MeetingCalendarCustomException("Exception occurred while scheduling meeting", ex.getMessage());
      }
   }

   @Override
   public List<FreeSlotsResponse> findFreeSlots(FreeSlotsRequest freeSlotsRequest) {
      Set<Integer> meetingIdsOfAllParticipants = new HashSet<>();
      List<Duration> allMeetingDurations;
      try {
         iMeetingSchedulerDao.findAllMeetingIdsOfParticipants(freeSlotsRequest.getCalendarIds(), meetingIdsOfAllParticipants,
               Utility.convertLocalDateTimeToSql(freeSlotsRequest.getMeetingDate()));
         if (meetingIdsOfAllParticipants.isEmpty()) {
            return MeetingSchedulerUtils.getFreeSlots(freeSlotsRequest.getMeetingDate());
         }
         allMeetingDurations = iMeetingSchedulerDao.getMeetingDurations(meetingIdsOfAllParticipants);
         return MeetingSchedulerUtils.getFreeSlots(allMeetingDurations, freeSlotsRequest.getDurationInMinutes());
      } catch (Exception ex) {
         throw new MeetingCalendarCustomException("Exception occurred while finding free slots", ex.getMessage());
      }
   }

   @Override
   public List<String> findMeetingConflicts(Integer meetingId) {
      List<String> employeeNames = new ArrayList<>();
      try {
         List<Calendar> participantCalendars = iMeetingSchedulerDao.getParticipantsOfMeeting(meetingId);
         Set<Meeting> allMeetingsOfParticipants = new HashSet<>();
         for (Calendar calendar : participantCalendars) {
            iMeetingSchedulerDao.getMeetingsForParticipant(allMeetingsOfParticipants, calendar.getCalendarId());
         }
         if (allMeetingsOfParticipants.isEmpty()) {
            return null;
         }
         Duration eventDuration = iMeetingSchedulerDao.getMeetingDuration(meetingId);
         List<Integer> meetingIdsWithConflicts = MeetingSchedulerUtils.getMeetingConflicts(eventDuration, meetingId, allMeetingsOfParticipants);
         Set<Integer> calendarIdsWithConflicts = new HashSet<>();
         for (Integer conflictingMeetingId : meetingIdsWithConflicts) {
            iMeetingSchedulerDao.findCalendarId(conflictingMeetingId, calendarIdsWithConflicts);
         }
         for (Integer calendarIdWithConflict : calendarIdsWithConflicts) {
            employeeNames.add(iUserDao.getEmployeeName(calendarIdWithConflict));
         }
      } catch (Exception ex) {
         throw new MeetingCalendarCustomException("Exception occurred while Finding meeting conflicts", ex.getMessage());
      }
      return employeeNames;
   }

}
