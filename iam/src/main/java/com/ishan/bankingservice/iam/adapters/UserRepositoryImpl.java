package com.ishan.bankingservice.iam.adapters;

import com.ishan.bankingservice.iam.domain.User;
import com.ishan.bankingservice.iam.domain.UserId;
import com.ishan.bankingservice.iam.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @Autowired
  private UserEventStore userEventStore;

  @Override
  public User get(UserId id) {
    User user = new User();
    userEventStore.getAllEvents(id)
        .forEach(userEvent -> userEvent.applyOn(user));
    return user;
  }

  @Override
  public void save(User user) {
    userEventStore.saveAllEvents(user.getUserId(), user.getChanges());
  }

}
