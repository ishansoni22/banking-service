package com.ishan.bankingservice.userhistory.adapters.http;

import com.ishan.bankingservice.userhistory.domain.User;
import com.ishan.bankingservice.userhistory.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user-history")
public class UserHistoryController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping(value = "/{userId}")
  public ResponseEntity<User> getUserHistory(@PathVariable("userId") String userId) {
    return userRepository.findById(userId)
        .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

}
