package com.application.meetingassistant.api;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.meetingassistant.dto.User;
import com.application.meetingassistant.service.user.IUserService;

import lombok.extern.log4j.Log4j2;

/**
 * The type Employee controller.
 */
@RestController
@RequestMapping("/employee")
@Log4j2
public class EmployeeController {

   @Autowired
   private IUserService iUserService;

   /**
    * Store User.
    *
    * @param user the user
    * @return the response entity
    */
   @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
   public ResponseEntity<User> storeUser(@RequestBody @Valid User user) {
      log.info("Request received to store user:: {}", user.toString());
      User addedUser = iUserService.addUser(user);
      log.info("Response sent back :: {}", addedUser.toString());
      return ResponseEntity.status(HttpStatus.OK).body(addedUser);
   }

   /**
    * Get user id.
    *
    * @param email the email
    * @return the employee id
    */
   @GetMapping(value = "/getCalendarId", produces = "application/json")
   public ResponseEntity<Integer> getUserId(@RequestParam @NotEmpty(message = "Please provide a email") String email) {
      log.info("Request received to get user id from email :: {}", email);
      Integer id = iUserService.findUsersCalendarId(email);
      log.info("Response sent back :: {}", id);
      return ResponseEntity.status(HttpStatus.OK).body(id);
   }
}
