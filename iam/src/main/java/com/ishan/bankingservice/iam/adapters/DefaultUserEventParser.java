package com.ishan.bankingservice.iam.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.bankingservice.iam.domain.UserCreated;
import com.ishan.bankingservice.iam.domain.UserEmailUpdated;
import com.ishan.bankingservice.iam.domain.UserEvent;
import com.ishan.bankingservice.iam.domain.UserNameUpdated;
import com.ishan.bankingservice.iam.domain.UserPanUpdated;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultUserEventParser implements UserEventParser {

  private UserEventParser next;

  @Override
  public void setNext(UserEventParser userEventParser) {
  }

  @Override
  public UserEvent parse(Fact fact, ObjectMapper mapper) {
      UserEvent userEvent = null;
      try {
        String type = fact.getFactType();
        if ("UserCreated".equals(type)){
          userEvent = mapper.readValue(fact.getFact(), UserCreated.class);
        } else if ("UserNameUpdated".equals(type)) {
          userEvent = mapper.readValue(fact.getFact(), UserNameUpdated.class);
        } else if ("UserEmailUpdated".equals(type)) {
          userEvent = mapper.readValue(fact.getFact(), UserEmailUpdated.class);
        } else if ("UserPanUpdated".equals(type)) {
          userEvent = mapper.readValue(fact.getFact(), UserPanUpdated.class);
        }
      } catch (Exception e) {
        log.error("Exception: Cannot parse fact -> event", e);
      }
      return userEvent;
  }

}
