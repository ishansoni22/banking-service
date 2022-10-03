package com.ishan.bankingservice.iam.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.iam.domain.UserCreatedV1;
import com.ishan.bankingservice.iam.domain.UserEvent;
import com.ishan.bankingservice.iam.domain.UserNameUpdatedV1;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserEventParserV1 implements UserEventParser {

  private UserEventParser next;

  @Override
  public void setNext(UserEventParser userEventParser) {
    this.next = userEventParser;
  }

  @Override
  public UserEvent parse(Fact fact, ObjectMapper mapper) {
    String type = fact.getFactType();
    if (fact.getVersion() == 1 && ("UserCreated".equals(type)
        || "UserNameUpdated".equals(type))) {
      UserEvent userEvent = null;
      try {
        if ("UserCreated".equals(type)){
          userEvent = mapper.readValue(fact.getFact(), UserCreatedV1.class);
        } else {
          userEvent = mapper.readValue(fact.getFact(), UserNameUpdatedV1.class);
        }
      } catch (Exception e) {
        log.error("Exception: Cannot parse fact -> event", e);
      }
      return userEvent;
    }
    return next.parse(fact, mapper);
  }

}
