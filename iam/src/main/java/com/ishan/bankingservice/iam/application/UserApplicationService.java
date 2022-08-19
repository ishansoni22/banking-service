package com.ishan.bankingservice.iam.application;

import com.ishan.bankingservice.iam.domain.User;
import com.ishan.bankingservice.iam.domain.UserId;
import com.ishan.bankingservice.iam.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserApplicationService {

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public UserId createUser(CreateUserCommand createUserCommand) {
    User user = new User();
    UserId userId = user.create(
        createUserCommand.getFullName(),
        createUserCommand.getEmail(),
        createUserCommand.getPan()
    );

    userRepository.save(user);

    return userId;
  }

  public UserResource getUser(String userId) {
    User user = userRepository.get(new UserId(userId));

    UserResource userResource = new UserResource();
    userResource.setId(user.getUserId().getId());
    userResource.setFullName(user.getFullName());
    userResource.setEmail(user.getEmail());
    userResource.setPan(user.getPan());

    return userResource;
  }

  @Transactional
  public void updateName(UpdateNameCommand updateNameCommand) {
    User user = userRepository.get(new UserId(updateNameCommand.getUserId()));
    user.updateFullName(updateNameCommand.getNewName());
    userRepository.save(user);
  }

  @Transactional
  public void updateEmail(UpdateEmailCommand updateEmailCommand) {
    User user = userRepository.get(new UserId(updateEmailCommand.getUserId()));
    user.updateEmail(updateEmailCommand.getNewEmail());
    userRepository.save(user);
  }

  @Transactional
  public void updatePan(UpdatePanCommand updatePanCommand) {
    User user = userRepository.get(new UserId(updatePanCommand.getUserId()));
    user.updatePan(updatePanCommand.getNewPan());
    userRepository.save(user);
  }

}