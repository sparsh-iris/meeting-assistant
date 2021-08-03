package com.application.meetingassistant.dao.scheduler.dao;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.application.meetingassistant.dto.Calendar;
import com.application.meetingassistant.dto.Meeting;
import com.application.meetingassistant.pojo.Duration;

public interface IMeetingSchedulerDao {

   Meeting addMeeting(Meeting meeting, List<Integer> calendarIds, Date meetingDateTime);

   Set<Integer> findAllMeetingIdsOfParticipants(List<Integer> calendarIdsOfParticipants, Set<Integer> meetingIds, Date date);

   List<Duration> getMeetingDurations(Set<Integer> meetingIds);

   Duration getMeetingDuration(Integer meetingId);

   List<Calendar> getParticipantsOfMeeting(Integer meetingId);

   void getMeetingsForParticipant(Set<Meeting> meetings, Integer calendarId);

   void findCalendarId(Integer meetingId, Set<Integer> calendarIdsWithConflicts);
}
