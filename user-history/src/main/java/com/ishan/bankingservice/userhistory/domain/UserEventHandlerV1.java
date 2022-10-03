package com.ishan.bankingservice.userhistory.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserEventHandlerV1 implements UserEventHandler {

  private UserEventHandler next;

  @Override
  public void next(UserEventHandler next) {
    this.next = next;
  }

  @Override
  public void handle(String eventType, int eventVersion, String event, ObjectMapper mapper,
      User user) throws Exception {
    if (eventVersion == 1
        && ("UserCreated".equals(event) || "UserNameUpdated".equals(event))) {
      if ("UserCreated".equals(eventType)) {
        UserCreatedV1 userCreated = mapper.readValue(event, UserCreatedV1.class);
        user.setUserId(userCreated.getAggregateId().getId());

        String fullName = userCreated.getFullName();
        String[] name = fullName.split(" ");
        String firstName = name[0];
        String lastName = name.length > 1 ? name[1] : null;

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(userCreated.getEmail());
        user.setPan(userCreated.getPan());
      } else {
        UserNameUpdatedV1 userNameUpdated = mapper.readValue(event, UserNameUpdatedV1.class);

        UserHistory history = new UserHistory();
        history.setKey("Full Name");
        history.setFrom(user.getFirstName() + user.getLastName());

        String fullName = userNameUpdated.getNewFullName();
        String[] name = fullName.split(" ");
        String firstName = name[0];
        String lastName = name.length > 1 ? name[1] : null;

        history.setTo(firstName + lastName);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.addHistory(history);
      }
    } else if (this.next != null) {
      this.next.handle(eventType, eventVersion, event, mapper, user);
    }
  }

}
