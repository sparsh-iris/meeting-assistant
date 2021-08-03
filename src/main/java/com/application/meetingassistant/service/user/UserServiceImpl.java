package com.application.meetingassistant.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.meetingassistant.dao.user.dao.IUserDao;
import com.application.meetingassistant.dto.User;
import com.application.meetingassistant.exception.MeetingCalendarCustomException;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements IUserService {

   @Autowired
   private IUserDao iUserDao;

   @Override
   public User addUser(User user) {
      try {
         return iUserDao.addUser(user);
      } catch (Exception ex) {
         throw new MeetingCalendarCustomException("Exception occurred while adding user to DB", ex.getMessage());
      }
   }

   @Override
   public Integer findUsersCalendarId(String email) {
      try {
         return iUserDao.findCalendarId(email);
      } catch (MeetingCalendarCustomException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new MeetingCalendarCustomException("Exception occurred while quering user from DB via email", ex.getMessage());
      }
   }
}
