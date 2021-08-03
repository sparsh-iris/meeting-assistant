package com.application.meetingassistant.dao.user.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.application.meetingassistant.dao.user.repository.UserRepository;
import com.application.meetingassistant.dto.User;
import com.application.meetingassistant.exception.MeetingCalendarCustomException;

import lombok.extern.log4j.Log4j2;

@Repository
@Transactional
@Log4j2
public class UserDao implements IUserDao {

   @Autowired
   private UserRepository userRepository;

   public User addUser(User user) {
      return userRepository.save(user);
   }

   public Integer findCalendarId(String email) {
      Optional<User> user = userRepository.findByEmail(email);
      if (!user.isPresent()) {
         throw new MeetingCalendarCustomException("No User Details found for the given email");
      }
      log.info("User found : {} for email : {}", user.get(), email);
      return user.map(User::getCalendarId).orElse(null);
   }

   public String getEmployeeName(Integer calendarId) {
      Optional<User> optionalEmployee = userRepository.findByCalendarId(calendarId);
      return optionalEmployee.map(User::getEmployeeName).orElse(null);
   }

}
