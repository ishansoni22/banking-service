package com.ishan.bankingservice.userhistory.domain;

import lombok.Data;

@Data
public class UserNameUpdated {

  private UserId aggregateId;
  private int revision;
  private int version;
  private String newFirstName;
  private String newLastName;

}
