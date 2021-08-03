package com.application.meetingassistant.dao.scheduler.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.application.meetingassistant.dto.Meeting;
import com.application.meetingassistant.pojo.Duration;
import com.application.meetingassistant.pojo.FreeSlotsResponse;

class MeetingSchedulerUtilsTest {

   @Test
   void getFreeSlots() {
      List<Duration> durations = new ArrayList<>();
      durations.add(new Duration(1628002800L, 1628013600L));
      durations.add(new Duration(1627999200L, 1628002800L));
      durations.add(new Duration(1628020800L, 1628028000L));
      durations.add(new Duration(1627992000L, 1627995600L));
      durations.add(new Duration(1628013600L, 1628017200L));
      List<FreeSlotsResponse> freeSlots = MeetingSchedulerUtils.getFreeSlots(durations, 60);
      Assertions.assertFalse(freeSlots.isEmpty());
   }

   @Test
   void getMeetingConflicts() {
      Set<Meeting> meetingSet = new HashSet<>();
      meetingSet.add(new Meeting(21, 1628002800L, 1628010000L));
      meetingSet.add(new Meeting(4, 1627999200L, 1628002800L));
      meetingSet.add(new Meeting(13, 1628013600L, 1628017200L));
      meetingSet.add(new Meeting(7, 1628020800L, 1628028000L));
      meetingSet.add(new Meeting(10, 1627992000L, 1627995600L));
      meetingSet.add(new Meeting(17, 1628002800L, 1628013600L));
      meetingSet.add(new Meeting(15, 1627988400L, 1627992000L));
      List<Integer> conflicts = MeetingSchedulerUtils.getMeetingConflicts(new Duration(1628002800L, 1628013600L), 17, meetingSet);
      Assertions.assertFalse(conflicts.isEmpty());
   }

   @Test
   void testGetFreeSlots() {
      Assertions.assertFalse(MeetingSchedulerUtils.getFreeSlots(LocalDateTime.now()).isEmpty());
   }
}