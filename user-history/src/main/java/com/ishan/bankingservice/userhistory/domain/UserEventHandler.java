package com.ishan.bankingservice.userhistory.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface UserEventHandler {

  void next(UserEventHandler next);

  void handle(String eventType, int eventVersion, String event, ObjectMapper mapper, User user)
      throws Exception;

}
