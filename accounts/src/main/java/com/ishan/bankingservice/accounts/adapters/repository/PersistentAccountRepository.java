package com.ishan.bankingservice.accounts.adapters.repository;

import com.ishan.bankingservice.accounts.domain.Account;
import com.ishan.bankingservice.accounts.domain.AccountEvent;
import com.ishan.bankingservice.accounts.domain.AccountId;
import com.ishan.bankingservice.accounts.domain.AccountRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class PersistentAccountRepository implements AccountRepository {

  @Autowired
  private AccountEventStore eventStore;

  @Override
  public Optional<Account> get(AccountId accountId) {
    List<AccountEvent> events = this.eventStore
        .getAllEvents(accountId);
    if (CollectionUtils.isEmpty(events)) {
      return Optional.empty();
    }
    Account account = new Account();
    events.forEach(e -> e.applyOn(account));
    return Optional.of(account);
  }

  @Override
  public Account save(Account account) {
    this.eventStore.saveAllEvents(account.getAccountId(), account.getChanges());
    return account;
  }

}
