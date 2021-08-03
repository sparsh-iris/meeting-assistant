package com.application.meetingassistant.dao.user.dao;

import com.application.meetingassistant.dto.User;

public interface IUserDao {

   User addUser(User user);

   Integer findCalendarId(String email);

   String getEmployeeName(Integer calendarId);
}
