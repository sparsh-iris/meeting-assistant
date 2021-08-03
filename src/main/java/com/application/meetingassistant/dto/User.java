package com.application.meetingassistant.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Employee", indexes = { @Index(name = "idx_employee_email", columnList = "email"),
      @Index(name = "idx_employee_calendarid", columnList = "calendarId") })
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

   @Id
   @Column(nullable = false)
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer calendarId;

   @Column(nullable = false, unique = true)
   @NotEmpty(message = "Please provide a email")
   private String email;

   @Column(nullable = false)
   @NotEmpty(message = "Please provide a first name")
   private String firstName;

   @Column(nullable = false)
   @NotEmpty(message = "Please provide a last name")
   private String lastName;

   public User(String email, String firstName, String lastName) {
      this.email = email;
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public String getEmployeeName() {
      return this.firstName + " " + this.lastName;
   }
}
