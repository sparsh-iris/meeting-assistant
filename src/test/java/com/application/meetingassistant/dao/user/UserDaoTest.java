package com.application.meetingassistant.dao.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.application.meetingassistant.CalendarMeetingAssistantApplication;
import com.application.meetingassistant.dao.user.dao.IUserDao;
import com.application.meetingassistant.dao.user.repository.UserRepository;
import com.application.meetingassistant.dto.User;

@SpringBootTest(classes = CalendarMeetingAssistantApplication.class)
class UserDaoTest {

   @Autowired
   private IUserDao iUserDao;

   @Autowired
   private UserRepository userRepository;

   @Test
   @Transactional
   @Rollback
   void addUser() {
      User user = new User(123, "randomEmail@provider.com", "DummyFirstName", "DummyLastName");
      iUserDao.addUser(user);
      Optional<User> fetchedUser = userRepository.findByEmail("randomEmail@provider.com");
      Assertions.assertNotNull(fetchedUser.orElse(null));
   }

   @Test
   @Transactional
   @Rollback
   void findCalendarId() {
      Integer calendarId = iUserDao.findCalendarId("dummyMail1@gmail.com");
      Assertions.assertEquals(calendarId, 1);
   }

   @Test
   @Transactional
   @Rollback
   void getEmployeeName() {
      String employeeName = iUserDao.getEmployeeName(1);
      Assertions.assertEquals(employeeName, "DummyFirstName1 DummyLastName1");
   }
}