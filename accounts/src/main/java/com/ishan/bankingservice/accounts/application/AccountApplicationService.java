package com.ishan.bankingservice.accounts.application;

import com.ishan.bankingservice.accounts.domain.Account;
import com.ishan.bankingservice.accounts.domain.AccountId;
import com.ishan.bankingservice.accounts.domain.AccountRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountApplicationService {

  @Autowired
  @Qualifier("persistentAccountRepository")
  private AccountRepository accountRepository;

  @Transactional
  public AccountResource createAccount(CreateAccountCommand createAccountCommand) {
    Account account = new Account();
    account.create(createAccountCommand.getOwnerId());

    this.accountRepository.save(account);
    return get(account.getAccountId().getId());
  }

  @Transactional
  public AccountResource deposit(MoneyDepositCommand moneyDepositCommand) {
    return this.accountRepository.get(new AccountId(moneyDepositCommand.getAccountId()))
        .map(account -> {
          account.deposit(moneyDepositCommand.getAmount());
          return account;
        })
        .map(account -> this.accountRepository.save(account))
        .map(account -> this.get(account.getAccountId().getId()))
        .orElseThrow();
  }

  public AccountResource get(String accountId) {
    Optional<Account> account = this.accountRepository.get(new AccountId(accountId));

    return account.map(a -> {
      AccountResource resource = new AccountResource();
      resource.setAccountId(a.getAccountId().getId());
      resource.setOwnerId(a.getOwnerId());
      resource.setBalance(a.getBalance());
      return resource;
    }).orElseThrow();
  }

}