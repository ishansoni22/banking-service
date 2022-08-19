package com.ishan.bankingservice.userhistory.adapters.config.migrations;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.ishan.bankingservice.userhistory.domain.User;
import com.ishan.bankingservice.userhistory.domain.UserRepository;

@ChangeLog(order = "001")
public class Init {

  @ChangeSet(order = "001", id = "init-test-user", author = "isoni")
  public void initTestUser(UserRepository userRepository) {
    User user = new User();
    user.setUserId("test-user-1");
    user.setEmail("test@bankingservice.com");
    user.setFullName("Test User1");
    user.setPan("TESTPAN123");

    userRepository.insert(user);
  }

}
