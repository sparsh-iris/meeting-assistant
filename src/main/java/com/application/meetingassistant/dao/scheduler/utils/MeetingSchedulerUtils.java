package com.application.meetingassistant.dao.scheduler.utils;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.application.meetingassistant.dto.Meeting;
import com.application.meetingassistant.pojo.Duration;
import com.application.meetingassistant.pojo.FreeSlotsResponse;
import com.application.meetingassistant.util.Utility;

public class MeetingSchedulerUtils {

   private MeetingSchedulerUtils() {

   }

   /**
    * Takes all meeting durations as input and the duration of event in minutes, calculates the free slots by merging all the time intervals and
    * then finding free slots between the merged durations, if no free slot is found we return a meeting duration before the first event and after
    * the last event
    *
    * @param allDurations             the all durations
    * @param meetingDurationInMinutes the meeting duration in minutes
    * @return the free slots
    */
   public static List<FreeSlotsResponse> getFreeSlots(List<Duration> allDurations, int meetingDurationInMinutes) {
      int meetingDurationInSeconds = meetingDurationInMinutes * 60;
      List<Duration> mergedDurations = getMergedDurations(allDurations);
      List<Duration> freeSlots = getFreeSlotsBetweenMergedDurations(mergedDurations, meetingDurationInSeconds);
      if (freeSlots.isEmpty()) {
         freeSlots = getFreeSlotsOutsideMergedDurations(mergedDurations, meetingDurationInSeconds);
      }
      List<FreeSlotsResponse> freeSlotsResponses = new ArrayList<>();
      for (Duration freeSlot : freeSlots) {
         freeSlotsResponses.add(new FreeSlotsResponse(Utility.convertTimeStampToDateTime(freeSlot.getStartTime()),
               Utility.convertTimeStampToDateTime(freeSlot.getEndTime())));
      }
      return freeSlotsResponses;
   }

   private static List<Duration> getFreeSlotsOutsideMergedDurations(List<Duration> mergedDurations, int meetingDurationInSeconds) {
      long startTime = mergedDurations.get(mergedDurations.size() - 1).getStartTime();
      long endTime = mergedDurations.get(0).getEndTime();
      List<Duration> durations = new ArrayList<>();
      durations.add(new Duration(startTime - meetingDurationInSeconds, startTime));
      durations.add(new Duration(endTime, endTime + meetingDurationInSeconds));
      return durations;
   }

   private static List<Duration> getFreeSlotsBetweenMergedDurations(List<Duration> mergedDurations, int meetingDurationInSeconds) {
      List<Duration> freeSlots = new ArrayList<>();
      for (int i = mergedDurations.size() - 1; i > 0; i--) {
         Duration duration1 = mergedDurations.get(i);
         Duration duration2 = mergedDurations.get(i - 1);
         if (duration2.getStartTime() - duration1.getEndTime() >= meetingDurationInSeconds) {
            freeSlots.add(new Duration(duration1.getEndTime(), duration2.getStartTime()));
         }
      }
      return freeSlots;
   }

   /**
    * Takes all meeting durations as input and merges them
    *
    * @param allDurations the all durations
    * @return the merged durations
    */
   private static List<Duration> getMergedDurations(List<Duration> allDurations) {
      List<Duration> mergedDurations = new ArrayList<>();
      Deque<Duration> stack = new ArrayDeque<>();
      allDurations.sort(Comparator.comparingLong(Duration::getStartTime));
      stack.push(allDurations.get(0));
      for (int i = 1; i < allDurations.size(); i++) {
         Duration top = stack.peek();
         if (Objects.nonNull(top)) {
            if (top.getEndTime() < allDurations.get(i).getStartTime()) {
               stack.push(allDurations.get(i));
            } else if (top.getEndTime() < allDurations.get(i).getEndTime()) {
               top.setEndTime(allDurations.get(i).getEndTime());
               stack.pop();
               stack.push(top);
            }
         }
      }
      while (!stack.isEmpty()) {
         mergedDurations.add(stack.pop());
      }
      return mergedDurations;
   }

   /**
    * Takes all meeting durations as input and returns a list of meetings having conflicts with the event duration
    *
    * @param meetingDuration the meeting duration
    * @param meetingId       the meeting id
    * @param meetings        the meetings
    * @return the meeting conflicts
    */
   public static List<Integer> getMeetingConflicts(Duration meetingDuration, Integer meetingId, Set<Meeting> meetings) {
      List<Integer> meetingsWithConflicts = new ArrayList<>();
      for (Meeting meeting : meetings) {
         if (!Objects.equals(meeting.getMeetingId(), meetingId) && checkForConflicts(meeting.getStartTime(), meeting.getEndTime(), meetingDuration)) {
            meetingsWithConflicts.add(meeting.getMeetingId());
         }
      }
      return meetingsWithConflicts;
   }

   private static boolean checkForConflicts(Long meetingStartTime, Long meetingEndTime, Duration eventDuration) {
      long eventStartTime = eventDuration.getStartTime();
      long eventEndTime = eventDuration.getEndTime();
      return (meetingStartTime > meetingEndTime) || (eventStartTime > eventEndTime) || (meetingStartTime < eventEndTime
            && meetingEndTime > eventStartTime);
   }

   public static List<FreeSlotsResponse> getFreeSlots(LocalDateTime meetingDate) {
      List<FreeSlotsResponse> freeSlotsResponses = new ArrayList<>();
      LocalDateTime startTime = LocalDateTime.of(meetingDate.getYear(), meetingDate.getMonth(), meetingDate.getDayOfMonth(), 9, 0);
      LocalDateTime endTime = LocalDateTime.of(meetingDate.getYear(), meetingDate.getMonth(), meetingDate.getDayOfMonth(), 21, 0);
      freeSlotsResponses.add(new FreeSlotsResponse(startTime, endTime));
      return freeSlotsResponses;
   }
}
