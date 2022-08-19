package com.ishan.bankingservice.userhistory.domain;

import lombok.Data;

@Data
public class UserPanUpdated {

  private UserId aggregateId;
  private int revision;
  private int version;
  private String newPan;

}