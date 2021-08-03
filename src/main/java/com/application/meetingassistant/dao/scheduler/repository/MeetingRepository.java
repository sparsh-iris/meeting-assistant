package com.application.meetingassistant.dao.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.meetingassistant.dto.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

}
