package com.ishan.bankingservice.userhistory.domain;

import lombok.Data;

@Data
public class UserCreatedV1 {

  private UserId aggregateId;
  private int revision;
  private int version;
  private String fullName;
  private String email;
  private String pan;

}
