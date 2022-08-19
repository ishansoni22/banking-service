package com.ishan.bankingservice.userhistory.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserHistory {

  private String key;
  private String from;
  private String to;
  private LocalDateTime at;

}
