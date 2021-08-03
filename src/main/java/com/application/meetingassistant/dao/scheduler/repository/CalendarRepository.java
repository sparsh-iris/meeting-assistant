package com.application.meetingassistant.dao.scheduler.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.meetingassistant.dto.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

   List<Optional<Calendar>> findByCalendarId(Integer calendarId);

   List<Optional<Calendar>> findByMeetingId(Integer meetingId);

   List<Optional<Calendar>> findByCalendarIdEqualsAndMeetingDateEquals(Integer calendarId, Date meetingDate);

}
