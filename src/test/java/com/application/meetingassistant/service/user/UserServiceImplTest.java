package com.application.meetingassistant.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.application.meetingassistant.CalendarMeetingAssistantApplication;
import com.application.meetingassistant.dto.User;

@SpringBootTest(classes = CalendarMeetingAssistantApplication.class)
class UserServiceImplTest {

   @Autowired
   private IUserService iUserService;

   @Test
   void addUser() {
      User user = new User(123, "randomEmail@provider.com", "DummyFirstName", "DummyLastName");
      User savedUser = iUserService.addUser(user);
      Assertions.assertNotNull(savedUser);
   }

   @Test
   void findUserCalendarId() {
      Integer calendarId = iUserService.findUsersCalendarId("dummyMail1@gmail.com");
      Assertions.assertEquals(1, calendarId);
   }
}