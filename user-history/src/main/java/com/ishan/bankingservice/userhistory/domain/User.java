package com.ishan.bankingservice.userhistory.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {

  @Id
  private String userId;

  private String fullName;

  private String email;

  private String pan;

  private List<UserHistory> history = new ArrayList<>();

  public void addHistory(UserHistory history) {
    this.history.add(history);
  }

}
