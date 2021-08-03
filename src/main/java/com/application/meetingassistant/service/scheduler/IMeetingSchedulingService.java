package com.application.meetingassistant.service.scheduler;

import java.util.List;

import com.application.meetingassistant.pojo.FreeSlotsRequest;
import com.application.meetingassistant.pojo.FreeSlotsResponse;
import com.application.meetingassistant.pojo.MeetingRequest;
import com.application.meetingassistant.pojo.MeetingResponse;

/**
 * The interface Meeting scheduling service.
 */
public interface IMeetingSchedulingService {

   /**
    * Schedule an event for all the given calendarIds with the provided details of time and duration
    *
    * @param meetingRequest the meeting request
    * @return the meeting response
    */
   MeetingResponse scheduleMeeting(MeetingRequest meetingRequest);

   /**
    * Given a list of calendarIds it finds the Free slots available between all of them
    *
    * @param freeSlotsRequest the free slots request
    * @return the list
    */
   List<FreeSlotsResponse> findFreeSlots(FreeSlotsRequest freeSlotsRequest);

   /**
    * Given a meeting Id it finds all the users which have a conflicting meeting at the time of the meetingId given
    *
    * @param meetingId the meeting id
    * @return the list
    */
   List<String> findMeetingConflicts(Integer meetingId);
}
