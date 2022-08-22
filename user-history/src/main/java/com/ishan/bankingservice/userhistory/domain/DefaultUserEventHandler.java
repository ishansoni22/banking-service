package com.ishan.bankingservice.userhistory.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultUserEventHandler implements UserEventHandler {

  private UserEventHandler next;

  @Override
  public void next(UserEventHandler next) {
    this.next = next;
  }

  @Override
  public void handle(String eventType, int eventVersion,
      String event, ObjectMapper mapper, User user) throws Exception {
    if ("UserCreated".equals(eventType)) {
      UserCreated userCreated = mapper.readValue(event, UserCreated.class);
      user.setUserId(userCreated.getAggregateId().getId());
      user.setFirstName(userCreated.getFirstName());
      user.setLastName(userCreated.getLastName());
      user.setEmail(userCreated.getEmail());
      user.setPan(userCreated.getPan());
    } else if ("UserNameUpdated".equals(eventType)) {
      UserNameUpdated userNameUpdated = mapper.readValue(event, UserNameUpdated.class);

      UserHistory history = new UserHistory();
      history.setKey("Full Name");
      history.setFrom(user.getFirstName() + " " + user.getLastName());
      history.setTo(userNameUpdated.getNewFirstName() + " " + userNameUpdated.getNewLastName());

      user.setFirstName(userNameUpdated.getNewFirstName());
      user.setLastName(userNameUpdated.getNewLastName());
      user.addHistory(history);
    } else if ("UserEmailUpdated".equals(eventType)) {
      UserEmailUpdated userEmailUpdated = mapper.readValue(event, UserEmailUpdated.class);

      UserHistory history = new UserHistory();
      history.setKey("Email");
      history.setFrom(user.getEmail());
      history.setTo(userEmailUpdated.getNewEmail());

      user.setEmail(userEmailUpdated.getNewEmail());
      user.addHistory(history);
    } else if ("UserPanUpdated".equals(eventType)) {
      UserPanUpdated userPanUpdated = mapper.readValue(event, UserPanUpdated.class);

      UserHistory history = new UserHistory();
      history.setKey("Pan");
      history.setFrom(user.getPan());
      history.setTo(userPanUpdated.getNewPan());

      user.setPan(userPanUpdated.getNewPan());
      user.addHistory(history);
    } else if (this.next != null) {
     this.next.handle(eventType, eventVersion, event, mapper, user);
    }
  }

}
