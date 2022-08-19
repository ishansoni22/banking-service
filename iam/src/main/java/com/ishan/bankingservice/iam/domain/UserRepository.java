package com.ishan.bankingservice.iam.domain;

public interface UserRepository {

  User get(UserId id);

  void save(User user);

}
