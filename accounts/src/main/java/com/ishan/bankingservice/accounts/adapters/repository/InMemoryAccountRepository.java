package com.ishan.bankingservice.accounts.adapters.repository;

import com.ishan.bankingservice.accounts.domain.Account;
import com.ishan.bankingservice.accounts.domain.AccountEvent;
import com.ishan.bankingservice.accounts.domain.AccountId;
import com.ishan.bankingservice.accounts.domain.AccountRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

  private Map<AccountId, List<AccountEvent>> db = new HashMap<>();

  @Override
  public Optional<Account> get(AccountId accountId) {
    if (this.db.containsKey(accountId)) {
      Account account = new Account();
      this.db.get(accountId).forEach(event -> event.applyOn(account));

      return Optional.of(account);
    }
    return Optional.empty();
  }

  @Override
  public Account save(Account account) {
    if (this.db.containsKey(account.getAccountId())) {
      this.db.get(account.getAccountId()).addAll(account.getChanges());
    } else {
      this.db.put(account.getAccountId(), account.getChanges());
    }
    return account;
  }

}
