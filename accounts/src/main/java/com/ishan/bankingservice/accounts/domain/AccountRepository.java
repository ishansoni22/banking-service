package com.ishan.bankingservice.accounts.domain;

import java.util.Optional;

public interface AccountRepository {

  Optional<Account> get(AccountId accountId);

  Account save(Account account);

}
