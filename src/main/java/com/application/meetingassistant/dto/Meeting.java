package com.application.meetingassistant.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Integer meetingId;

   @Column(nullable = false)
   Long startTime;

   @Column(nullable = false)
   Long endTime;

   public Meeting(Long startTime, Long endTime) {
      this.startTime = startTime;
      this.endTime = endTime;
   }

}
