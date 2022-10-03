package com.ishan.bankingservice.userhistory.domain;

import lombok.Data;

@Data
public class UserNameUpdatedV1 {

  private UserId aggregateId;
  private int revision;
  private int version;
  private String newFullName;

}
