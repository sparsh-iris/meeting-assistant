package com.application.meetingassistant.dao.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.meetingassistant.dto.User;

public interface UserRepository extends JpaRepository<User, Integer> {

   Optional<User> findByEmail(String email);

   Optional<User> findByCalendarId(Integer calendarId);

}
