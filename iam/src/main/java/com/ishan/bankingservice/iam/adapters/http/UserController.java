package com.ishan.bankingservice.iam.adapters.http;

import com.ishan.bankingservice.iam.application.CreateUserCommand;
import com.ishan.bankingservice.iam.application.UpdateEmailCommand;
import com.ishan.bankingservice.iam.application.UpdateNameCommand;
import com.ishan.bankingservice.iam.application.UpdatePanCommand;
import com.ishan.bankingservice.iam.application.UserApplicationService;
import com.ishan.bankingservice.iam.application.UserResource;
import com.ishan.bankingservice.iam.domain.UserId;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

  @Autowired
  private UserApplicationService userApplicationService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
    UserId userId = userApplicationService.createUser(
        new CreateUserCommand(
            createUserRequest.getFirstName(),
            createUserRequest.getLastName(),
            createUserRequest.getEmail(),
            createUserRequest.getPan()
        )
    );
    return ResponseEntity.ok(userId.getId());
  }

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserResource> getUser(@PathVariable("userId") String userId) {
    UserResource user = userApplicationService.getUser(userId);
    if (Objects.isNull(user)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(user);
  }

  @PostMapping(value = "/{userId}/name", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateUserName(
      @PathVariable("userId") String userId,
      @RequestBody UpdateNameRequest updateNameRequest) {
    userApplicationService.updateName(
        new UpdateNameCommand(userId, updateNameRequest.getNewFirstName(), updateNameRequest.getNewLastName())
    );
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/{userId}/email", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateUserEmail(
      @PathVariable("userId") String userId,
      @RequestBody UpdateEmailRequest updateEmailRequest) {
    userApplicationService.updateEmail(
        new UpdateEmailCommand(userId, updateEmailRequest.getNewEmail())
    );
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/{userId}/pan", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateUserPan(
      @PathVariable("userId") String userId,
      @RequestBody UpdatePanRequest updatePanRequest) {
    userApplicationService.updatePan(
        new UpdatePanCommand(userId, updatePanRequest.getNewPan())
    );
    return ResponseEntity.ok().build();
  }

}
