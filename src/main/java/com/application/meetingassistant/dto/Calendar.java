package com.application.meetingassistant.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Calendar", indexes = { @Index(name = "idx_calendar_calendarid", columnList = "calendarId"),
      @Index(name = "idx_calendar_meetingid", columnList = "meetingId") })
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Integer id;

   @Column(nullable = false)
   Integer calendarId;

   @Column(nullable = false)
   Integer meetingId;

   @Column(nullable = false)
   Date meetingDate;

   public Calendar(Integer calendarId, Integer meetingId, Date meetingDate) {
      this.calendarId = calendarId;
      this.meetingId = meetingId;
      this.meetingDate = meetingDate;
   }

}
