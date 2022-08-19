package com.ishan.bankingservice.userhistory.domain;

import lombok.Data;

@Data
public class UserCreated {

  private UserId aggregateId;
  private int revision;
  private int version;
  private String fullName;
  private String email;
  private String pan;

}
