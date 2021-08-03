package com.application.meetingassistant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.application.meetingassistant.dao.scheduler.repository.CalendarRepository;
import com.application.meetingassistant.dao.scheduler.repository.MeetingRepository;
import com.application.meetingassistant.dao.user.repository.UserRepository;
import com.application.meetingassistant.dto.User;
import com.application.meetingassistant.pojo.MeetingRequest;
import com.application.meetingassistant.service.scheduler.IMeetingSchedulingService;
import com.application.meetingassistant.service.user.IUserService;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class CalendarMeetingAssistantApplication implements CommandLineRunner {

   @Autowired
   private UserRepository employeeRepository;

   @Autowired
   private MeetingRepository meetingRepository;

   @Autowired
   private CalendarRepository calendarRepository;

   @Autowired
   private IMeetingSchedulingService iMeetingSchedulingService;

   @Autowired
   private IUserService iUserService;

   @Override
   public void run(String... args) throws Exception {
      log.info("Populating Database with dummy values");
      employeeRepository.save(new User(1, "dummyMail1@gmail.com", "DummyFirstName1", "DummyLastName1"));
      employeeRepository.save(new User(2, "dummyMail2@gmail.com", "DummyFirstName2", "DummyLastName2"));
      employeeRepository.save(new User(3, "dummyMail3@gmail.com", "DummyFirstName3", "DummyLastName3"));
      log.info("Scheduling dummy meetings");
      iMeetingSchedulingService.scheduleMeeting(new MeetingRequest(convert(new int[] { 1, 2 }), LocalDateTime.of(2021, 8, 3, 14, 0), 60));
      iMeetingSchedulingService.scheduleMeeting(new MeetingRequest(convert(new int[] { 1, 3 }), LocalDateTime.of(2021, 8, 3, 20, 0), 120));
      iMeetingSchedulingService.scheduleMeeting(new MeetingRequest(convert(new int[] { 2, 3 }), LocalDateTime.of(2021, 8, 3, 12, 0), 60));
      iMeetingSchedulingService.scheduleMeeting(new MeetingRequest(convert(new int[] { 1 }), LocalDateTime.of(2021, 8, 3, 18, 0), 60));
      iMeetingSchedulingService.scheduleMeeting(new MeetingRequest(convert(new int[] { 3 }), LocalDateTime.of(2021, 8, 3, 11, 0), 60));
      iMeetingSchedulingService.scheduleMeeting(new MeetingRequest(convert(new int[] { 1, 2, 3 }), LocalDateTime.of(2021, 8, 3, 15, 0), 180));
      iMeetingSchedulingService.scheduleMeeting(new MeetingRequest(convert(new int[] { 1, 3 }), LocalDateTime.of(2021, 8, 3, 15, 0), 120));
   }

   public static List<Integer> convert(int[] arr) {
      List<Integer> list = new ArrayList<Integer>();
      for (int i : arr) {
         list.add(i);
      }
      return list;
   }

   public static void main(String[] args) {
      SpringApplication.run(CalendarMeetingAssistantApplication.class, args);
   }

}
