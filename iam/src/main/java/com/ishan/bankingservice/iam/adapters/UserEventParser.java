package com.ishan.bankingservice.iam.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.iam.domain.UserEvent;

public interface UserEventParser {

  void setNext(UserEventParser userEventParser);

  UserEvent parse(Fact fact, ObjectMapper mapper);

}
