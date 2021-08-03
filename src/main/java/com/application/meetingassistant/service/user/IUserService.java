package com.application.meetingassistant.service.user;

import com.application.meetingassistant.dto.User;

/**
 * The interface User service.
 */
public interface IUserService {

   /**
    * Adds user to the database.
    *
    * @param user the user
    * @return the user
    */
   User addUser(User user);

   /**
    * Find user calendar id from email.
    *
    * @param email the email
    * @return the integer
    */
   Integer findUsersCalendarId(String email);
}
