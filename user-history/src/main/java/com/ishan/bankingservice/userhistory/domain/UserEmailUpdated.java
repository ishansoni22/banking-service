package com.ishan.bankingservice.userhistory.domain;

import lombok.Data;

@Data
public class UserEmailUpdated {

  private UserId aggregateId;
  private int revision;
  private int version;
  private String newEmail;

}
