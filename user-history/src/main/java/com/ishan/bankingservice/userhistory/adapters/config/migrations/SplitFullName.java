package com.ishan.bankingservice.userhistory.adapters.config.migrations;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.ishan.bankingservice.userhistory.domain.UserRepository;

@ChangeLog(order = "002")
public class SplitFullName {

  @ChangeSet(order = "001", id = "split-full-name", author = "isoni")
  public void splitFullName(UserRepository userRepository) {
    userRepository.findAll()
        .parallelStream()
        .forEach(user -> {
          String fullName = user.getFullName();

          String[] name = fullName.split(" ");

          user.setFirstName(name[0]);
          user.setLastName(name.length > 1 ? name[1] : null);

          userRepository.save(user);
        });
  }

}